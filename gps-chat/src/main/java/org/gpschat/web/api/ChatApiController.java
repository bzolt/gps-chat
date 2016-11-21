package org.gpschat.web.api;

import java.time.OffsetDateTime;
import java.util.List;

import org.gpschat.core.service.ChatService;
import org.gpschat.core.service.UserService;
import org.gpschat.web.config.CustomUserDetails;
import org.gpschat.web.data.ChatRoom;
import org.gpschat.web.data.Message;
import org.gpschat.web.data.UserIdList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-17T14:00:30.041Z")

@Controller
public class ChatApiController implements ChatApi
{
	@Autowired
	UserService	userService;
	@Autowired
	ChatService	chatService;

	@Override
	public ResponseEntity<List<Message>> chatCommonAsUserIdGet(String userId, OffsetDateTime before)
	{
		// TODO Auto-generated method stub
		return ChatApi.super.chatCommonAsUserIdGet(userId, before);
	}

	@Override
	public ResponseEntity<Integer> chatCommonDistanceGet(
			@AuthenticationPrincipal CustomUserDetails activeUser)
	{
		return new ResponseEntity<>(userService.getViewDistance(activeUser.getEntity()),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> chatCommonDistancePost(Integer distance,
			@AuthenticationPrincipal CustomUserDetails activeUser)
	{
		userService.setViewDistance(distance, activeUser.getEntity());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<ChatRoom>> chatGet(
			@AuthenticationPrincipal CustomUserDetails activeUser)
	{
		return new ResponseEntity<>(chatService.getChats(activeUser.getEntity()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> chatGroupChatIdInvitePost(@PathVariable("chatId") String chatId,
			@RequestBody UserIdList userIds, @AuthenticationPrincipal CustomUserDetails activeUser)
	{
		chatService.inviteToChat(chatId, userIds.getIds(), activeUser.getEntity());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> chatGroupChatIdLeavePost(@PathVariable("chatId") String chatId,
			@AuthenticationPrincipal CustomUserDetails activeUser)
	{
		chatService.leaveChat(chatId, activeUser.getEntity());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> chatGroupNewPost(@RequestBody UserIdList userIds,
			@AuthenticationPrincipal CustomUserDetails activeUser)
	{
		return new ResponseEntity<>(
				chatService.newGroupChat(userIds.getIds(), activeUser.getEntity()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Message>> chatMessagesAfterGet(OffsetDateTime dateTime,
			String chatId)
	{
		// TODO Auto-generated method stub
		return ChatApi.super.chatMessagesAfterGet(dateTime, chatId);
	}

	@Override
	public ResponseEntity<List<Message>> chatMessagesBeforeGet(String chatId,
			OffsetDateTime dateTime)
	{
		// TODO Auto-generated method stub
		return ChatApi.super.chatMessagesBeforeGet(chatId, dateTime);
	}

	@Override
	public ResponseEntity<String> chatPrivateNewPost(@RequestBody UserIdList userId,
			@AuthenticationPrincipal CustomUserDetails activeUser)
	{
		return new ResponseEntity<>(
				chatService.newPrivateChat(userId.getIds(), activeUser.getEntity()), HttpStatus.OK);
	}

}
