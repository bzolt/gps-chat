package org.gpschat.web.api;

import org.gpschat.core.exceptions.ChatNotFoundException;
import org.gpschat.core.exceptions.DuplicateEmailAddressException;
import org.gpschat.core.exceptions.InvalidUserException;
import org.gpschat.core.exceptions.InvalidValueException;
import org.gpschat.core.exceptions.UserIsBlockedException;
import org.gpschat.core.exceptions.UserNotFoundException;
import org.gpschat.core.exceptions.UserNotPartOfChatException;
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

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseBody
	public ResponseEntity<User> userNotFound()
	{
		User user = new User();
		user.setEmail("qwe");
		user.setFullName("qweasd");
		return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidUserException.class)
	@ResponseBody
	public ResponseEntity<User> invalidUser()
	{
		User user = new User();
		user.setEmail("qwe");
		user.setFullName("qweasd");
		return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidValueException.class)
	@ResponseBody
	public ResponseEntity<User> invalidValue()
	{
		User user = new User();
		user.setEmail("qwe");
		user.setFullName("qweasd");
		return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserIsBlockedException.class)
	@ResponseBody
	public ResponseEntity<User> userIsBlocked()
	{
		User user = new User();
		user.setEmail("qwe");
		user.setFullName("qweasd");
		return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ChatNotFoundException.class)
	@ResponseBody
	public ResponseEntity<User> chatNotFound()
	{
		User user = new User();
		user.setEmail("qwe");
		user.setFullName("qweasd");
		return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserNotPartOfChatException.class)
	@ResponseBody
	public ResponseEntity<User> userNotPartOfChat()
	{
		User user = new User();
		user.setEmail("qwe");
		user.setFullName("qweasd");
		return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
	}
}
