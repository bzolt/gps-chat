package org.gpschat.web.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.gpschat.web.config.CustomUserDetails;
import org.gpschat.web.data.StringList;
import org.gpschat.web.data.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-17T14:00:30.041Z")

@Api(value = "users", description = "the users API")
public interface UsersApi
{

	@ApiOperation(value = "Block user.", notes = "Block the user with the given id.", response = Void.class, authorizations = {
			@Authorization(value = "basicAuth") }, tags = { "user", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "User successfully blocked.", response = Void.class),
			@ApiResponse(code = 400, message = "User is same as current user.", response = Void.class),
			@ApiResponse(code = 404, message = "User not found.", response = Void.class) })
	@RequestMapping(value = "/users/block/{id}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<Void> usersBlockIdPost(
			@ApiParam(value = "", required = true) @PathVariable("id") String id,
			@AuthenticationPrincipal CustomUserDetails activeUser);

	@ApiOperation(value = "Find users by e-mail, name or username.", notes = "You can search users from the database by e-mail, fullname or username.", response = User.class, responseContainer = "List", authorizations = {
			@Authorization(value = "basicAuth") }, tags = { "user", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The list of users matching the value.", response = User.class) })
	@RequestMapping(value = "/users/find", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<List<User>> usersFindGet(
			@ApiParam(value = "", required = true) @RequestParam(value = "queryString", required = true) String queryString);

	@ApiOperation(value = "Get user by id.", notes = "Get details of user by id.", response = User.class, authorizations = {
			@Authorization(value = "basicAuth") }, tags = { "user", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "User who has the given id.", response = User.class),
			@ApiResponse(code = 404, message = "User not found.", response = User.class) })
	@RequestMapping(value = "/users/{id}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<User> usersIdGet(
			@ApiParam(value = "", required = true) @PathVariable("id") String id);

	@ApiOperation(value = "Get current user.", notes = "Get details of current user.", response = User.class, authorizations = {
			@Authorization(value = "basicAuth") }, tags = { "user", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The currently logged in user.", response = User.class) })
	@RequestMapping(value = "/users/me", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<User> usersMeGet(@AuthenticationPrincipal CustomUserDetails activeUser);

	@ApiOperation(value = "Modify user datas.", notes = "Modify currently logged in user datas.", response = Void.class, authorizations = {
			@Authorization(value = "basicAuth") }, tags = { "user", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Modify successful.", response = Void.class),
			@ApiResponse(code = 400, message = "Bad format.", response = Void.class) })
	@RequestMapping(value = "/users/me", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	ResponseEntity<Void> usersMePut(@ApiParam(value = "", required = true) @RequestBody User user,
			@AuthenticationPrincipal CustomUserDetails activeUser);

	@ApiOperation(value = "Send token for FCM communication.", notes = "Send the token used for FCM communication.", response = Void.class, authorizations = {
			@Authorization(value = "basicAuth") }, tags = { "user", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Token successfully sent.", response = Void.class),
			@ApiResponse(code = 400, message = "Bad format.", response = Void.class) })
	@RequestMapping(value = "/fcm/token", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<Void> fcmTokenPost(@RequestBody StringList token,
			@AuthenticationPrincipal CustomUserDetails activeUser);

	@ApiOperation(value = "Logout from the service.", notes = "You can logout from the service on this route.", response = Void.class, tags = {
			"user", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Logout successful.", response = Void.class) })
	@RequestMapping(value = "/users/logout", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<Void> logoutPost(HttpServletRequest httpServletRequest,
			@AuthenticationPrincipal CustomUserDetails activeUser);

}
