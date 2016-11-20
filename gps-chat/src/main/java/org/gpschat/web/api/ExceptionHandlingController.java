package org.gpschat.web.api;

import org.gpschat.core.exceptions.DuplicateEmailAddressException;
import org.gpschat.web.data.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandlingController
{
	@ExceptionHandler(DuplicateEmailAddressException.class)
	@ResponseBody
	public ResponseEntity<User> duplicateEmail()
	{
		User user = new User();
		user.setEmail("qwe");
		user.setFullName("qweasd");
		return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
	}
}
