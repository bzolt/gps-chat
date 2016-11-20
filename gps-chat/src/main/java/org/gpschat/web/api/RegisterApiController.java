package org.gpschat.web.api;

import org.gpschat.core.service.RegisterService;
import org.gpschat.web.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-17T14:00:30.041Z")

@RestController
public class RegisterApiController implements RegisterApi
{
	@Autowired
	RegisterService registerService;

	@Override
	public ResponseEntity<Void> registerPost(@RequestBody User user)
	{
		registerService.registerUser(user);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> loginGet()
	{
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
