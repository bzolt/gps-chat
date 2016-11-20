package org.gpschat.web.api;

import java.time.OffsetDateTime;
import java.math.BigDecimal;
import java.util.List;

import io.swagger.annotations.*;

import org.gpschat.web.data.ChatRoom;
import org.gpschat.web.data.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-17T14:00:30.041Z")

@Api(value = "chat", description = "the chat API")
public interface ChatApi {

    @ApiOperation(value = "View chat as someone else.", notes = "View the common chat as another user.", response = Message.class, responseContainer = "List", tags={ "chat", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Messages before the given time seen as the specified user.", response = Message.class) })
    @RequestMapping(value = "/chat/common/as/{userId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<List<Message>> chatCommonAsUserIdGet(@ApiParam(value = "",required=true ) @PathVariable("userId") BigDecimal userId,
        @ApiParam(value = "") @RequestParam(value = "before", required = false) OffsetDateTime before) {
        // do some magic!
        return new ResponseEntity<List<Message>>(HttpStatus.OK);
    }


    @ApiOperation(value = "Get view distance.", notes = "Get the currently logged in user's view distance.", response = BigDecimal.class, tags={ "chat", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The view distance.", response = BigDecimal.class) })
    @RequestMapping(value = "/chat/common/distance",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<BigDecimal> chatCommonDistanceGet() {
        // do some magic!
        return new ResponseEntity<BigDecimal>(HttpStatus.OK);
    }


    @ApiOperation(value = "Set view distance.", notes = "Set the currently logged in user's view distance.", response = Void.class, tags={ "chat", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The view distance.", response = Void.class) })
    @RequestMapping(value = "/chat/common/distance",
        produces = { "application/json" }, 
        method = RequestMethod.POST)
    default ResponseEntity<Void> chatCommonDistancePost(@ApiParam(value = "" ,required=true ) @RequestBody BigDecimal distance) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    @ApiOperation(value = "Get private and group chats.", notes = "Get private and group chats of current user.", response = ChatRoom.class, tags={ "chat", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The chats of the user", response = ChatRoom.class) })
    @RequestMapping(value = "/chat",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<ChatRoom> chatGet() {
        // do some magic!
        return new ResponseEntity<ChatRoom>(HttpStatus.OK);
    }


    @ApiOperation(value = "Inviting users to a group chat.", notes = "Inviting users to a group chat with specified users.", response = BigDecimal.class, tags={ "chat", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The id of the new chat.", response = BigDecimal.class),
        @ApiResponse(code = 404, message = "Chat or user(s) not found.", response = BigDecimal.class),
        @ApiResponse(code = 499, message = "There are users, who has blocked you in the invite list.", response = BigDecimal.class) })
    @RequestMapping(value = "/chat/group/{chatId}/invite",
        produces = { "application/json" }, 
        method = RequestMethod.POST)
    default ResponseEntity<BigDecimal> chatGroupChatIdInvitePost(@ApiParam(value = "",required=true ) @PathVariable("chatId") BigDecimal chatId,
        @ApiParam(value = "" ,required=true ) @RequestBody List<BigDecimal> userIds) {
        // do some magic!
        return new ResponseEntity<BigDecimal>(HttpStatus.OK);
    }


    @ApiOperation(value = "Leave a group chat.", notes = "Leave a group chat.", response = Void.class, tags={ "chat", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Succesful leaving.", response = Void.class),
        @ApiResponse(code = 404, message = "Chat not found.", response = Void.class) })
    @RequestMapping(value = "/chat/group/{chatId}/leave",
        produces = { "application/json" }, 
        method = RequestMethod.POST)
    default ResponseEntity<Void> chatGroupChatIdLeavePost(@ApiParam(value = "",required=true ) @PathVariable("chatId") BigDecimal chatId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    @ApiOperation(value = "Starting a new group chat.", notes = "Starting a new group chat with specified users.", response = BigDecimal.class, tags={ "chat", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The id of the new chat.", response = BigDecimal.class),
        @ApiResponse(code = 404, message = "User(s) not found.", response = BigDecimal.class),
        @ApiResponse(code = 499, message = "There are users, who has blocked you in the invite list.", response = BigDecimal.class) })
    @RequestMapping(value = "/chat/group/new",
        produces = { "application/json" }, 
        method = RequestMethod.POST)
    default ResponseEntity<BigDecimal> chatGroupNewPost(@ApiParam(value = "" ,required=true ) @RequestBody List<BigDecimal> userIds) {
        // do some magic!
        return new ResponseEntity<BigDecimal>(HttpStatus.OK);
    }


    @ApiOperation(value = "Get messages.", notes = "Get messages in a chat after a given time.", response = Message.class, responseContainer = "List", tags={ "chat", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Messages after the given time.", response = Message.class),
        @ApiResponse(code = 400, message = "Bad format.", response = Message.class),
        @ApiResponse(code = 404, message = "Chat not found.", response = Message.class) })
    @RequestMapping(value = "/chat/messages/after",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<List<Message>> chatMessagesAfterGet(@ApiParam(value = "", required = true) @RequestParam(value = "dateTime", required = true) OffsetDateTime dateTime,
        @ApiParam(value = "", required = true) @RequestParam(value = "chatId", required = true) BigDecimal chatId) {
        // do some magic!
        return new ResponseEntity<List<Message>>(HttpStatus.OK);
    }


    @ApiOperation(value = "Get messages.", notes = "Get messages in a chat before a given time.", response = Message.class, responseContainer = "List", tags={ "chat", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Messages before the given time.", response = Message.class),
        @ApiResponse(code = 400, message = "Bad format.", response = Message.class),
        @ApiResponse(code = 404, message = "Chat not found.", response = Message.class) })
    @RequestMapping(value = "/chat/messages/before",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<List<Message>> chatMessagesBeforeGet(@ApiParam(value = "", required = true) @RequestParam(value = "chatId", required = true) BigDecimal chatId,
        @ApiParam(value = "") @RequestParam(value = "dateTime", required = false) OffsetDateTime dateTime) {
        // do some magic!
        return new ResponseEntity<List<Message>>(HttpStatus.OK);
    }


    @ApiOperation(value = "Starting a new private chat.", notes = "Starting a new private chat with a specified user.", response = BigDecimal.class, tags={ "chat", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The id of the new chat.", response = BigDecimal.class),
        @ApiResponse(code = 404, message = "User not found.", response = BigDecimal.class),
        @ApiResponse(code = 499, message = "The user has blocked you.", response = BigDecimal.class) })
    @RequestMapping(value = "/chat/private/new",
        produces = { "application/json" }, 
        method = RequestMethod.POST)
    default ResponseEntity<BigDecimal> chatPrivateNewPost(@ApiParam(value = "" ,required=true ) @RequestBody BigDecimal userId) {
        // do some magic!
        return new ResponseEntity<BigDecimal>(HttpStatus.OK);
    }

}
