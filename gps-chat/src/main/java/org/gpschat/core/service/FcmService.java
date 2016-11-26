package org.gpschat.core.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.gpschat.fcm.data.AckResponse;
import org.gpschat.fcm.data.AckUpstream;
import org.gpschat.fcm.data.ControlResponse;
import org.gpschat.fcm.data.FcmMessage;
import org.gpschat.fcm.data.FcmUpstreamMessage;
import org.gpschat.fcm.data.NackResponse;
import org.gpschat.persistance.domain.Chat;
import org.gpschat.persistance.domain.MessageEntity;
import org.gpschat.persistance.domain.UserEntity;
import org.gpschat.persistance.repositories.ChatRepository;
import org.gpschat.persistance.repositories.MessageEntityRepository;
import org.gpschat.persistance.repositories.UserEntityRepository;
import org.gpschat.web.config.GeoJsonNearOperation;
import org.gpschat.web.config.WithinOperation;
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

	Map<String, org.gpschat.web.data.Message> messages = new ConcurrentHashMap<>();

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

	private void receiveUpstream(FcmUpstreamMessage message)
	{
		sendAck(message.getId(), message.getFrom());

		// TODO getUSer
		// TODO save msg
		// TODO update location
		// TODO send to others (only others!)
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

	private void saveChatMessage(UserEntity sender, Chat chat, String text, GeoJsonPoint location)
	{
		MessageEntity message = new MessageEntity();
		message.setChat(chat);
		message.setSender(sender);
		message.setText(text);
		message.setDateTime(new Date());
		message.setLocation(location);
		messageRepository.save(message);
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
}
