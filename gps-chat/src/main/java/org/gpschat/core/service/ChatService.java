package org.gpschat.core.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
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
import org.springframework.data.geo.Distance;
import org.springframework.stereotype.Service;

@Service
public class ChatService
{
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

		Chat probe = new Chat();
		probe.setGroup(false);
		probe.setMembers(members);
		Example<Chat> example = Example.of(probe);

		Chat chat = chatRepository.findOne(example);

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

	public List<ChatMessage> messagesAfter(String chatId, OffsetDateTime dateTime, UserEntity user)
	{
		if (chatId.equals(ChatConstants.COMMON_CHAT_ID))
		{
			messagesAfterInCommon(dateTime, user);
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
		List<MessageEntity> entities = messageEntityRepository.findFirstByChatAndDateTimeAfter(
				ChatConstants.MESSAGE_PAGE_SIZE, chat, dateService.toDate(dateTime));

		List<ChatMessage> messages = new ArrayList<>();
		for (MessageEntity messageEntity : entities)
		{
			messages.add(convertToMessage(messageEntity));
		}
		return messages;
	}

	public List<ChatMessage> messagesBefore(String chatId, OffsetDateTime dateTime, UserEntity user)
	{
		if (chatId.equals(ChatConstants.COMMON_CHAT_ID))
		{
			messagesBeforeInCommon(dateTime, user);
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

		List<MessageEntity> entities = messageEntityRepository.findFirstByChatAndDateTimeBefore(
				ChatConstants.MESSAGE_PAGE_SIZE, chat, dateService.toDate(dateTime));

		List<ChatMessage> messages = new ArrayList<>();
		for (MessageEntity messageEntity : entities)
		{
			messages.add(convertToMessage(messageEntity));
		}
		return messages;
	}

	public List<ChatMessage> messagesBeforeInCommonAsOtherUser(String userId,
			OffsetDateTime dateTime)
	{
		UserEntity user = userEntityRepository.findOne(userId);
		if (user == null)
		{
			throw new UserNotFoundException();
		}

		return messagesBeforeInCommon(dateTime, user);
	}

	private List<ChatMessage> messagesAfterInCommon(OffsetDateTime dateTime, UserEntity user)
	{
		List<MessageEntity> entities = messageEntityRepository
				.findFirstByChatIsNullAndDateTimeAfterAndLocationNear(
						ChatConstants.MESSAGE_PAGE_SIZE, dateService.toDate(dateTime),
						user.getLocation(),
						new Distance(user.getViewDistance(), ChatConstants.VIEW_DISTANCE_METRICS));

		List<ChatMessage> messages = new ArrayList<>();
		for (MessageEntity messageEntity : entities)
		{
			messages.add(convertToMessage(messageEntity));
		}
		return messages;
	}

	private List<ChatMessage> messagesBeforeInCommon(OffsetDateTime dateTime, UserEntity user)
	{
		List<MessageEntity> entities = messageEntityRepository
				.findFirstByChatIsNullAndDateTimeBeforeAndLocationNear(
						ChatConstants.MESSAGE_PAGE_SIZE, dateService.toDate(dateTime),
						user.getLocation(),
						new Distance(user.getViewDistance(), ChatConstants.VIEW_DISTANCE_METRICS));

		List<ChatMessage> messages = new ArrayList<>();
		for (MessageEntity messageEntity : entities)
		{
			messages.add(convertToMessage(messageEntity));
		}
		return messages;
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
