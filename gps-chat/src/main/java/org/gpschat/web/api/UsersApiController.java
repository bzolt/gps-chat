package org.gpschat.web.api;

import java.util.List;

import org.gpschat.core.service.UserService;
import org.gpschat.web.config.CustomUserDetails;
import org.gpschat.web.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

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
	public ResponseEntity<List<User>> usersFindGet(String queryString)
	{
		return new ResponseEntity<>(userService.queryUsers(queryString), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<User> usersIdGet(String id)
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
	public ResponseEntity<Void> usersMePut(User user,
			@AuthenticationPrincipal CustomUserDetails activeUser)
	{
		userService.updateUser(user, activeUser.getEntity());
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
