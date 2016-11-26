package org.gpschat.web.api;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.gpschat.core.service.UserService;
import org.gpschat.web.config.CustomUserDetails;
import org.gpschat.web.data.User;
import org.gpschat.web.data.UserIdList;
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
public class UsersApiController implements UsersApi
{
	@Autowired
	UserService userService;

	@Override
	public ResponseEntity<Void> usersBlockIdPost(@PathVariable("id") String id,
			@AuthenticationPrincipal CustomUserDetails activeUser)
	{
		userService.blockUser(id, activeUser.getEntity());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<User>> usersFindGet(
			@RequestParam(value = "queryString", required = true) String queryString)
	{
		return new ResponseEntity<>(userService.queryUsers(queryString), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<User> usersIdGet(@PathVariable("id") String id)
	{
		return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<User> usersMeGet(@AuthenticationPrincipal CustomUserDetails activeUser)
	{
		return new ResponseEntity<>(userService.getUser(activeUser.getEntity().getId()),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> usersMePut(@RequestBody User user,
			@AuthenticationPrincipal CustomUserDetails activeUser)
	{
		userService.updateUser(user, activeUser.getEntity());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> logoutPost(HttpServletRequest httpServletRequest)
	{
		try
		{
			httpServletRequest.logout();
		}
		catch (ServletException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> fcmTokenPost(@RequestBody UserIdList userIds,
			@AuthenticationPrincipal CustomUserDetails activeUser)
	{
		userService.setFcmToken(userIds.getIds().get(0), activeUser.getEntity());
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
