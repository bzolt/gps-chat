package org.gpschat.core.service;

import java.util.ArrayList;
import java.util.List;

import org.gpschat.core.exceptions.ChatNotFoundException;
import org.gpschat.core.exceptions.InvalidValueException;
import org.gpschat.core.exceptions.UserIsBlockedException;
import org.gpschat.core.exceptions.UserNotFoundException;
import org.gpschat.core.exceptions.UserNotPartOfChatException;
import org.gpschat.persistance.domain.Chat;
import org.gpschat.persistance.domain.UserEntity;
import org.gpschat.persistance.repositories.ChatRepository;
import org.gpschat.persistance.repositories.UserEntityRepository;
import org.gpschat.web.data.ChatRoom;
import org.gpschat.web.data.ChatRoom.TypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class ChatService
{
	@Autowired
	ChatRepository			chatRepository;
	@Autowired
	UserEntityRepository	userEntityRepository;

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
}
