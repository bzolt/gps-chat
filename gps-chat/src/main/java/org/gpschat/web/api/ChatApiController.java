package org.gpschat.web.api;

import java.util.List;

import org.gpschat.core.service.ChatService;
import org.gpschat.core.service.UserService;
import org.gpschat.web.config.CustomUserDetails;
import org.gpschat.web.data.ChatMessage;
import org.gpschat.web.data.ChatRoom;
import org.gpschat.web.data.StringList;
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
			@RequestParam(value = "before", required = false) Long before)
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
			@RequestBody StringList userIds, @AuthenticationPrincipal CustomUserDetails activeUser)
	{
		chatService.inviteToChat(chatId, userIds.getItems(), activeUser.getEntity());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> chatGroupChatIdLeavePost(@PathVariable("chatId") String chatId,
			@AuthenticationPrincipal CustomUserDetails activeUser)
	{
		chatService.leaveChat(chatId, activeUser.getEntity());
		try
		{
			Thread.sleep(5000);
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> chatGroupNewPost(@RequestBody StringList userIds,
			@AuthenticationPrincipal CustomUserDetails activeUser)
	{
		return new ResponseEntity<>(
				chatService.newGroupChat(userIds.getItems(), activeUser.getEntity()),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<ChatMessage>> chatMessagesAfterGet(
			@RequestParam(value = "chatId", required = true) String chatId,
			@RequestParam(value = "dateTime", required = true) Long dateTime,
			@AuthenticationPrincipal CustomUserDetails activeUser)
	{
		return new ResponseEntity<>(
				chatService.messagesAfter(chatId, dateTime, activeUser.getEntity()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<ChatMessage>> chatMessagesBeforeGet(
			@RequestParam(value = "chatId", required = true) String chatId,
			@RequestParam(value = "dateTime", required = true) Long dateTime,
			@AuthenticationPrincipal CustomUserDetails activeUser)
	{
		return new ResponseEntity<>(
				chatService.messagesBefore(chatId, dateTime, activeUser.getEntity()),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> chatPrivateNewPost(@RequestBody StringList userId,
			@AuthenticationPrincipal CustomUserDetails activeUser)
	{
		return new ResponseEntity<>(
				chatService.newPrivateChat(userId.getItems(), activeUser.getEntity()),
				HttpStatus.OK);
	}

}
