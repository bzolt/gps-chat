package org.gpschat.core.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.gpschat.core.constants.ChatConstants;
import org.gpschat.fcm.data.AckResponse;
import org.gpschat.fcm.data.AckUpstream;
import org.gpschat.fcm.data.ControlResponse;
import org.gpschat.fcm.data.FcmDownstreamMessage;
import org.gpschat.fcm.data.FcmMessage;
import org.gpschat.fcm.data.FcmUpstreamMessage;
import org.gpschat.fcm.data.NackResponse;
import org.gpschat.fcm.data.Notification;
import org.gpschat.fcm.data.UpChatMessage;
import org.gpschat.persistance.domain.Chat;
import org.gpschat.persistance.domain.MessageEntity;
import org.gpschat.persistance.domain.UserEntity;
import org.gpschat.persistance.repositories.ChatRepository;
import org.gpschat.persistance.repositories.MessageEntityRepository;
import org.gpschat.persistance.repositories.UserEntityRepository;
import org.gpschat.web.config.GeoJsonNearOperation;
import org.gpschat.web.config.WithinOperation;
import org.gpschat.web.data.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
public class FcmService
{
	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	@Qualifier("fcmChannel")
	private MessageChannel messageChannel;

	@Autowired
	ChatRepository			chatRepository;
	@Autowired
	MessageEntityRepository	messageRepository;
	@Autowired
	UserEntityRepository	userEntityRepository;

	@Autowired
	DateService dateService;

	Map<String, org.gpschat.web.data.ChatMessage> messages = new ConcurrentHashMap<>();

	@ServiceActivator(inputChannel = "fcmInChannel")
	public void receive(FcmMessage message)
	{
		if (message.getType() == null)
		{
			receiveUpstream(message.asUpstreamMessage());
		}
		else
		{
			if ("ack".equalsIgnoreCase(message.getType()))
			{
				receiveAck(message.asAck());
			}
			else if ("nack".equalsIgnoreCase(message.getType()))
			{
				receiveNack(message.asNack());
			}
			else if ("control".equalsIgnoreCase(message.getType()))
			{
				receiveControl(message.asControl());
			}
		}
	}

	private void receiveUpstream(FcmUpstreamMessage fcmMessage)
	{
		sendAck(fcmMessage.getId(), fcmMessage.getFrom());
		UpChatMessage message = fcmMessage.getData();
		UserEntity user = userEntityRepository.findByFcmToken(fcmMessage.getFrom());
		boolean isCommon = message.getChatId().equalsIgnoreCase(ChatConstants.COMMON_CHAT_ID);

		Chat chat = isCommon ? null : chatRepository.findOne(message.getChatId());
		if (user != null && (isCommon || chat != null))
		{
			GeoJsonPoint location = new GeoJsonPoint(message.getLongitude(), message.getLatitude());
			updateLocation(user, location);

			if (message.getText() != null)
			{
				Date date = saveChatMessage(user, chat, message.getText(), location);
				List<UserEntity> recipients = getRecipients(chat, location);
				// recipients.remove(user);
				if (!recipients.isEmpty())
				{
					ChatMessage downMessage = new ChatMessage().chatId(message.getChatId())
							.senderId(user.getId()).senderUserName(user.getUserName())
							.text(message.getText()).dateTime(dateService.toOffsetDateTime(date));
					for (UserEntity recipient : recipients)
					{
						sendMessage(recipient, downMessage);
					}
				}
			}
		}
	}

	private void receiveAck(AckResponse ack)
	{
		messages.remove(ack.getId());
	}

	private void receiveNack(NackResponse nack)
	{
		switch (nack.getError())
		{
			case BAD_REGISTRATION:
			case DEVICE_UNREGISTERED:
				messages.remove(nack.getId());
				// TODO remove registration
				System.out.println("FCM error: " + nack.getError() + "("
						+ nack.getErrorDescription() + "), id: " + nack.getId());
				break;
			case BAD_ACK:
				// TODO retry ACK
				System.out.println("FCM error: " + nack.getError() + "("
						+ nack.getErrorDescription() + "), id: " + nack.getId());
				break;
			case SERVICE_UNAVAILABLE:
			case INTERNAL_SERVER_ERROR:
				// TODO retry
				break;
			case DEVICE_MESSAGE_RATE_EXCEEDED:
				// TODO wait and retry later
				break;
			case CONNECTION_DRAINING:
				// TODO open new connection and retry
				break;
			default:
				messages.remove(nack.getId());
				System.out.println("FCM error: " + nack.getError() + "("
						+ nack.getErrorDescription() + "), id: " + nack.getId());
				break;
		}
	}

	private void receiveControl(ControlResponse control)
	{
		if ("CONNECTION_DRAINING".equals(control.getControlType()))
		{
			// TODO open new connection
		}
	}

	private void sendAck(String messageId, String to)
	{
		Message<AckUpstream> ack = MessageBuilder.withPayload(new AckUpstream(messageId, to))
				.build();
		messageChannel.send(ack);
	}

	private Date saveChatMessage(UserEntity sender, Chat chat, String text, GeoJsonPoint location)
	{
		Date date = new Date();
		MessageEntity message = new MessageEntity();
		message.setChat(chat);
		message.setSender(sender);
		message.setText(text);
		message.setDateTime(date);
		message.setLocation(location);
		messageRepository.save(message);
		return date;
	}

	private void updateLocation(UserEntity user, GeoJsonPoint location)
	{
		user.setLocation(location);
		userEntityRepository.save(user);
	}

	private List<UserEntity> usersNear(GeoJsonPoint point)
	{
		Aggregation aggregation = newAggregation(
				new GeoJsonNearOperation(new GeoJsonPoint(23, 42), "distance"),
				// project(fields("distance", "viewDistance").and("within", "{
				// '$lt': [ '$distance', '$viewDistance' ] }")),
				new WithinOperation(), match(where("within").is(true)), project("_id"));
		AggregationResults<UserEntity> users = mongoTemplate.aggregate(aggregation, "userEntity",
				UserEntity.class);
		return users.getMappedResults();
	}

	private List<UserEntity> getRecipients(Chat chat, GeoJsonPoint location)
	{
		List<UserEntity> recipients;
		// It is the common chat
		if (chat == null)
		{
			recipients = usersNear(location);
		}
		else
		{
			recipients = chat.getMembers();
		}
		for (Iterator<UserEntity> iterator = recipients.iterator(); iterator.hasNext();)
		{
			UserEntity userEntity = iterator.next();
			if (userEntity.getFcmToken() == null)
			{
				iterator.remove();
			}
		}
		return recipients;
	}

	private void sendMessage(UserEntity recipient, ChatMessage message)
	{
		messages.put(message.getText(), message);
		FcmDownstreamMessage downMessage = new FcmDownstreamMessage();
		downMessage.setId(message.getText());
		downMessage.setData(message);
		downMessage.setTo(recipient.getFcmToken());
		Notification notification = new Notification();
		notification.setTitle("Üzenet!!!!!!");
		notification.setBody("Mondom jött üzenet, remélem szép a színe!");
		notification.setColor("#1E90C4");
		downMessage.setNotification(notification);
		Message<FcmDownstreamMessage> fcmMessage = MessageBuilder.withPayload(downMessage).build();
		messageChannel.send(fcmMessage);
	}
}
