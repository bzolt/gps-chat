package org.gpschat.web.api;

import org.gpschat.web.data.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-17T14:00:30.041Z")

@Controller
public class UsersApiController implements UsersApi
{
	@Override
	public ResponseEntity<User> usersMeGet()
	{
		User user = new User();
		user.setEmail("asd");
		user.setFullName("asd");
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> usersMePut(@RequestBody User user)
	{
		System.out.println("asd");
		System.out.println(user);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
