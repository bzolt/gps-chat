package org.gpschat.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gpschat.core.constants.ChatConstants;
import org.gpschat.core.exceptions.ChatNotFoundException;
import org.gpschat.core.exceptions.InvalidValueException;
import org.gpschat.core.exceptions.UserIsBlockedException;
import org.gpschat.core.exceptions.UserNotFoundException;
import org.gpschat.core.exceptions.UserNotPartOfChatException;
import org.gpschat.persistance.domain.Chat;
import org.gpschat.persistance.domain.MessageEntity;
import org.gpschat.persistance.domain.UserEntity;
import org.gpschat.persistance.repositories.ChatRepository;
import org.gpschat.persistance.repositories.MessageEntityRepository;
import org.gpschat.persistance.repositories.UserEntityRepository;
import org.gpschat.web.data.ChatMessage;
import org.gpschat.web.data.ChatRoom;
import org.gpschat.web.data.TypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class ChatService
{
	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	ChatRepository			chatRepository;
	@Autowired
	MessageEntityRepository	messageEntityRepository;
	@Autowired
	UserEntityRepository	userEntityRepository;

	@Autowired
	DateService dateService;

	public List<ChatRoom> getChats(UserEntity user)
	{
		List<Chat> entities = chatRepository.findByMembersContaining(user);
		List<ChatRoom> chatRooms = new ArrayList<>();
		for (Chat chat : entities)
		{
			chatRooms.add(convertToChatRoom(chat));
		}
		return chatRooms;
	}

	public String newPrivateChat(List<String> userIds, UserEntity user)
	{
		if (userIds.size() != 1)
		{
			throw new InvalidValueException();
		}
		List<UserEntity> members = createMembersWithUser(userIds, user);
		Chat chat = getChatWithMembers(members);
		if (chat == null)
		{
			chat = new Chat();
			chat.setGroup(false);
			chat.setMembers(members);
			chatRepository.save(chat);
		}

		return chat.getId();
	}

	public String newGroupChat(List<String> userIds, UserEntity user)
	{
		if (userIds.isEmpty())
		{
			throw new InvalidValueException();
		}
		List<UserEntity> members = createMembersWithUser(userIds, user);

		Chat chat = new Chat();
		chat.setGroup(true);
		chat.setMembers(members);
		chatRepository.save(chat);
		return chat.getId();
	}

	public void leaveChat(String chatId, UserEntity user)
	{
		Chat chat = chatRepository.findOne(chatId);
		if (chat == null)
		{
			throw new ChatNotFoundException();
		}

		if (chat.getMembers().size() == 1)
		{
			chatRepository.delete(chat);
		}
		else
		{
			chat.getMembers().remove(user);
			chatRepository.save(chat);
		}
	}

	public void inviteToChat(String chatId, List<String> userIds, UserEntity user)
	{
		if (userIds.isEmpty())
		{
			throw new InvalidValueException();
		}

		Chat chat = chatRepository.findOne(chatId);
		if (chat == null)
		{
			throw new ChatNotFoundException();
		}

		if (!chat.getMembers().contains(user))
		{
			throw new UserNotPartOfChatException();
		}

		List<UserEntity> members = createMembers(userIds, user);
		for (UserEntity member : members)
		{
			if (!chat.getMembers().contains(member))
			{
				chat.getMembers().add(member);
			}
		}
		chatRepository.save(chat);
	}

	public List<ChatMessage> messagesAfter(String chatId, Long dateTime, UserEntity user)
	{
		if (dateTime == null)
		{
			throw new InvalidValueException();
		}
		Date date = new Date(dateTime);
		if (chatId.equals(ChatConstants.COMMON_CHAT_ID))
		{
			return messagesAfterInCommon(date, user);
		}
		else
		{
			Chat chat = chatRepository.findOne(chatId);
			if (chat == null)
			{
				throw new ChatNotFoundException();
			}
			if (!chat.getMembers().contains(user))
			{
				throw new UserNotPartOfChatException();
			}
			List<MessageEntity> entities = messageEntityRepository
					.findFirst10ByChatAndDateTimeAfter(chat, date);

			List<ChatMessage> messages = new ArrayList<>();
			for (MessageEntity messageEntity : entities)
			{
				messages.add(convertToMessage(messageEntity));
			}
			return messages;
		}
	}

	public List<ChatMessage> messagesBefore(String chatId, Long dateTime, UserEntity user)
	{
		if (dateTime == null)
		{
			throw new InvalidValueException();
		}
		Date date = new Date(dateTime);
		if (chatId.equals(ChatConstants.COMMON_CHAT_ID))
		{
			return messagesBeforeInCommon(date, user);
		}
		else
		{
			Chat chat = chatRepository.findOne(chatId);
			if (chat == null)
			{
				throw new ChatNotFoundException();
			}
			if (!chat.getMembers().contains(user))
			{
				throw new UserNotPartOfChatException();
			}

			List<MessageEntity> entities = messageEntityRepository
					.findFirst10ByChatAndDateTimeBefore(chat, date);

			List<ChatMessage> messages = new ArrayList<>();
			for (MessageEntity messageEntity : entities)
			{
				messages.add(convertToMessage(messageEntity));
			}
			return messages;
		}
	}

	public List<ChatMessage> messagesBeforeInCommonAsOtherUser(String userId, Long dateTime)
	{
		if (dateTime == null)
		{
			throw new InvalidValueException();
		}
		UserEntity user = userEntityRepository.findOne(userId);
		if (user == null)
		{
			throw new UserNotFoundException();
		}

		Date date = new Date(dateTime);
		return messagesBeforeInCommon(date, user);
	}

	private List<ChatMessage> messagesAfterInCommon(Date dateTime, UserEntity user)
	{
		Pageable pageable = new PageRequest(0, 10, Sort.Direction.ASC, "dateTime");
		List<MessageEntity> entities = messageEntityRepository.findCommonAfter(dateTime,
				user.getLocation().getX(), user.getLocation().getY(),
				distanceInRadians(user.getViewDistance()), pageable);
		List<ChatMessage> messages = new ArrayList<>();
		for (MessageEntity messageEntity : entities)
		{
			messages.add(convertToMessage(messageEntity));
		}
		return messages;
	}

	private List<ChatMessage> messagesBeforeInCommon(Date dateTime, UserEntity user)
	{
		Pageable pageable = new PageRequest(0, 10, Sort.Direction.DESC, "dateTime");
		List<MessageEntity> entities = messageEntityRepository.findCommonBefore(dateTime,
				user.getLocation().getX(), user.getLocation().getY(),
				distanceInRadians(user.getViewDistance()), pageable);
		List<ChatMessage> messages = new ArrayList<>();
		for (MessageEntity messageEntity : Lists.reverse(entities))
		{
			messages.add(convertToMessage(messageEntity));
		}
		return messages;
	}

	private double distanceInRadians(double distanceInKms)
	{
		return distanceInKms / 6378.1;
	}

	private List<UserEntity> createMembers(List<String> userIds, UserEntity user)
	{
		List<UserEntity> members = new ArrayList<>();
		for (String userId : userIds)
		{
			UserEntity otherUser = userEntityRepository.findOne(userId);
			if (otherUser == null)
			{
				throw new UserNotFoundException();
			}
			if (otherUser.getBlockedUsers().contains(user))
			{
				throw new UserIsBlockedException();
			}
			members.add(otherUser);
		}
		return members;
	}

	private List<UserEntity> createMembersWithUser(List<String> userIds, UserEntity user)
	{
		List<UserEntity> members = createMembers(userIds, user);
		members.add(user);
		return members;
	}

	private Chat getChatWithMembers(List<UserEntity> members)
	{
		Chat probe = new Chat();
		probe.setGroup(false);
		probe.setMembers(members);
		Example<Chat> example = Example.of(probe);

		Chat chat = chatRepository.findOne(example);
		if (chat == null)
		{
			probe.setMembers(Lists.reverse(members));
			example = Example.of(probe);
			chat = chatRepository.findOne(example);
		}
		return chat;
	}

	private ChatRoom convertToChatRoom(Chat entity)
	{
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.id(entity.getId()).type(entity.isGroup() ? TypeEnum.GROUP : TypeEnum.PRIVATE);
		return chatRoom;
	}

	private ChatMessage convertToMessage(MessageEntity entity)
	{
		ChatMessage message = new ChatMessage();
		message.senderId(entity.getSender().getId())
				.senderUserName(entity.getSender().getUserName()).text(entity.getText())
				.dateTime(entity.getDateTime());
		return message;
	}
}
