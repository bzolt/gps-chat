package org.gpschat.web.api;

import java.time.OffsetDateTime;
import java.util.List;

import org.gpschat.core.service.ChatService;
import org.gpschat.core.service.UserService;
import org.gpschat.web.config.CustomUserDetails;
import org.gpschat.web.data.ChatRoom;
import org.gpschat.web.data.ChatMessage;
import org.gpschat.web.data.UserIdList;
import org.gpschat.web.data.ViewDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-17T14:00:30.041Z")

@Controller
public class ChatApiController implements ChatApi
{
	@Autowired
	UserService	userService;
	@Autowired
	ChatService	chatService;

	@Override
	public ResponseEntity<List<ChatMessage>> chatCommonAsUserIdGet(
			@PathVariable("userId") String userId,
			@RequestParam(value = "before", required = false) OffsetDateTime before)
	{
		return new ResponseEntity<>(chatService.messagesBeforeInCommonAsOtherUser(userId, before),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ViewDistance> chatCommonDistanceGet(
			@AuthenticationPrincipal CustomUserDetails activeUser)
	{
		return new ResponseEntity<>(userService.getViewDistance(activeUser.getEntity()),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> chatCommonDistancePost(@RequestBody ViewDistance distance,
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
	public ResponseEntity<List<ChatMessage>> chatMessagesAfterGet(
			@RequestParam(value = "chatId", required = true) String chatId,
			@RequestParam(value = "dateTime", required = true) OffsetDateTime dateTime,
			@AuthenticationPrincipal CustomUserDetails activeUser)
	{
		return new ResponseEntity<>(
				chatService.messagesAfter(chatId, dateTime, activeUser.getEntity()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<ChatMessage>> chatMessagesBeforeGet(
			@RequestParam(value = "chatId", required = true) String chatId,
			@RequestParam(value = "dateTime", required = true) OffsetDateTime dateTime,
			@AuthenticationPrincipal CustomUserDetails activeUser)
	{
		return new ResponseEntity<>(
				chatService.messagesBefore(chatId, dateTime, activeUser.getEntity()),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> chatPrivateNewPost(@RequestBody UserIdList userId,
			@AuthenticationPrincipal CustomUserDetails activeUser)
	{
		return new ResponseEntity<>(
				chatService.newPrivateChat(userId.getIds(), activeUser.getEntity()), HttpStatus.OK);
	}

}
