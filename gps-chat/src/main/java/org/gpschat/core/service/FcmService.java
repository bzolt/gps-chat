package org.gpschat.core.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.gpschat.core.constants.ChatConstants;
import org.gpschat.fcm.data.AckResponse;
import org.gpschat.fcm.data.AckUpstream;
import org.gpschat.fcm.data.ControlResponse;
import org.gpschat.fcm.data.FcmDownstreamMessage;
import org.gpschat.fcm.data.FcmMessage;
import org.gpschat.fcm.data.FcmUpstreamMessage;
import org.gpschat.fcm.data.NackResponse;
import org.gpschat.fcm.data.Notification;
import org.gpschat.fcm.data.PendingMessage;
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
import org.gpschat.web.data.TypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.xmpp.XmppHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.TaskScheduler;
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

	@Autowired
	TaskScheduler taskScheduler;

	private final Map<String, PendingMessage> pendingMessages = new ConcurrentHashMap<>();

	private final Queue<FcmDownstreamMessage> waitingMessages = new ConcurrentLinkedQueue<>();

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

		if (user != null)
		{
			GeoJsonPoint location = new GeoJsonPoint(message.getLongitude(), message.getLatitude());
			updateLocation(user, location);

			if (message.getText() != null)
			{
				processUpstreamMessage(user, message, location);
			}
		}
	}

	private void receiveAck(AckResponse ack)
	{
		synchronized (pendingMessages)
		{
			pendingMessages.remove(ack.getId());
		}
		FcmDownstreamMessage message = waitingMessages.poll();
		if (message != null)
		{
			prepareMessage(message);
		}
	}

	private void receiveNack(NackResponse nack)
	{
		switch (nack.getError())
		{
			case BAD_REGISTRATION:
			case DEVICE_UNREGISTERED:
				pendingMessages.remove(nack.getId());
				UserEntity user = userEntityRepository.findByFcmToken(nack.getFrom());
				user.setFcmToken(null);
				userEntityRepository.save(user);
				break;
			case SERVICE_UNAVAILABLE:
			case INTERNAL_SERVER_ERROR:
			case DEVICE_MESSAGE_RATE_EXCEEDED:
				scheduleRetry(nack.getId());
				break;
			case CONNECTION_DRAINING:
				handleConnectionDraining();
				break;
			default:
				pendingMessages.remove(nack.getId());
				break;
		}
	}

	private void receiveControl(ControlResponse control)
	{
		if ("CONNECTION_DRAINING".equals(control.getControlType()))
		{
			handleConnectionDraining();
		}
	}

	private void sendAck(String messageId, String to)
	{
		Message<AckUpstream> ack = MessageBuilder.withPayload(new AckUpstream(messageId, to))
				.setHeader(XmppHeaders.TO, "gcm.googleapis.com").build();
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

	private void processUpstreamMessage(UserEntity user, UpChatMessage message,
			GeoJsonPoint location)
	{
		boolean isCommon = message.getChatId().equalsIgnoreCase(ChatConstants.COMMON_CHAT_ID);
		Chat chat = isCommon ? null : chatRepository.findOne(message.getChatId());
		if (isCommon || (chat != null && chat.getMembers().contains(user)))
		{
			Date date = saveChatMessage(user, chat, message.getText(), location);
			List<UserEntity> recipients = getRecipients(chat, location);
			recipients.remove(user);
			if (!recipients.isEmpty())
			{
				ChatMessage downMessage = new ChatMessage().chatId(message.getChatId())
						.senderId(user.getId()).senderUserName(user.getUserName())
						.text(message.getText()).dateTime(date);
				if (!isCommon)
				{
					downMessage.setChatType(chat.isGroup() ? TypeEnum.GROUP : TypeEnum.PRIVATE);
				}
				for (UserEntity recipient : recipients)
				{
					newMessage(recipient, downMessage);
				}
			}
		}
	}

	private List<UserEntity> usersNear(GeoJsonPoint point)
	{
		Aggregation aggregation = newAggregation(new GeoJsonNearOperation(point, "distance"),
				new WithinOperation(), match(where("within").is(true)), project("_id"));
		AggregationResults<UserEntity> users = mongoTemplate.aggregate(aggregation, "userEntity",
				UserEntity.class);
		return users.getMappedResults();
		// AggregationResults<Object> users =
		// mongoTemplate.aggregate(aggregation, "userEntity",
		// Object.class);
		// for (Object object : users)
		// {
		// System.out.println(object);
		// }
		// return new ArrayList<>();
	}

	private List<UserEntity> getRecipients(Chat chat, GeoJsonPoint location)
	{
		List<UserEntity> recipients;
		// It is the common chat
		if (chat == null)
		{
			List<UserEntity> asd = usersNear(location);
			recipients = new ArrayList<>();
			for (UserEntity userEntity : asd)
			{
				recipients.add(userEntityRepository.findOne(userEntity.getId()));
			}
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

	private void newMessage(UserEntity recipient, ChatMessage message)
	{
		FcmDownstreamMessage downMessage = new FcmDownstreamMessage();
		downMessage.setId(messageId());
		downMessage.setTo(recipient.getFcmToken());
		downMessage.setData(message);
		System.out.println(recipient.getEmail() + ", " + recipient.getFcmToken());
		prepareMessage(downMessage);

		FcmDownstreamMessage downMessage2 = new FcmDownstreamMessage();
		downMessage2.setId(messageId());
		downMessage2.setTo(recipient.getFcmToken());
		downMessage2.setNotification(createNotification(message));
		// prepareMessage(downMessage2);
	}

	private void prepareMessage(FcmDownstreamMessage message)
	{
		boolean canSend = false;
		synchronized (pendingMessages)
		{
			if (pendingMessages.size() < 100)
			{
				canSend = true;
				pendingMessages.put(message.getId(), new PendingMessage(0, message));
			}
			else
			{
				waitingMessages.add(message);
			}
		}
		if (canSend)
		{
			sendMessage(message);
		}
	}

	private void sendMessage(FcmDownstreamMessage message)
	{
		Message<FcmDownstreamMessage> fcmMessage = MessageBuilder.withPayload(message)
				.setHeader(XmppHeaders.TO, "gcm.googleapis.com").build();
		messageChannel.send(fcmMessage);
	}

	public void retryMessage(String messageId)
	{
		sendMessage(pendingMessages.get(messageId).getMessage());
	}

	private Notification createNotification(ChatMessage message)
	{
		String chatName;
		if (message.getChatId().equalsIgnoreCase(ChatConstants.COMMON_CHAT_ID))
		{
			chatName = "Közös chat";
		}
		else
		{
			chatName = message.getChatType() == TypeEnum.GROUP ? "Csoportos chat"
					: message.getSenderUserName();
		}
		Notification notification = new Notification();
		notification.setTitle(chatName);
		notification.setBody(message.getSenderUserName() + ": " + message.getText());
		notification.setColor("#f23c90");
		notification.setIcon("ic_menu_info_details");
		notification.setTag(message.getChatId());
		notification.setSound("default");
		return notification;
	}

	private String messageId()
	{
		return UUID.randomUUID().toString();
	}

	private void handleConnectionDraining()
	{
		// TODO
	}

	private void scheduleRetry(String messageId)
	{
		PendingMessage pending = pendingMessages.get(messageId);
		long waitTime = Math.round(Math.pow((double) pending.getRetryCount(), 2.0));
		pending.incrementRetryCount();
		Date runTime = new Date(new Date().getTime() + waitTime);
		taskScheduler.schedule(() -> retryMessage(messageId), runTime);
	}
}
