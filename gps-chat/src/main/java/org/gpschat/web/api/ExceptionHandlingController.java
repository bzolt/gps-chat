package org.gpschat.web.api;

import org.gpschat.core.exceptions.ChatNotFoundException;
import org.gpschat.core.exceptions.DuplicateEmailAddressException;
import org.gpschat.core.exceptions.InvalidUserException;
import org.gpschat.core.exceptions.InvalidValueException;
import org.gpschat.core.exceptions.UserIsBlockedException;
import org.gpschat.core.exceptions.UserNotFoundException;
import org.gpschat.core.exceptions.UserNotPartOfChatException;
import org.gpschat.web.data.ErrorData;
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
	public ResponseEntity<ErrorData> duplicateEmail()
	{
		ErrorData error = new ErrorData();
		error.error(ErrorData.ErrorEnum.DUPLICATE_EMAIL)
				.message("This email is already in the database");
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseBody
	public ResponseEntity<ErrorData> userNotFound()
	{
		ErrorData error = new ErrorData();
		error.error(ErrorData.ErrorEnum.USER_NOT_FOUND).message("User not found.");
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidUserException.class)
	@ResponseBody
	public ResponseEntity<ErrorData> invalidUser()
	{
		ErrorData error = new ErrorData();
		error.error(ErrorData.ErrorEnum.INVALID_USER).message("You can't block yourself.");
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidValueException.class)
	@ResponseBody
	public ResponseEntity<ErrorData> invalidValue()
	{
		ErrorData error = new ErrorData();
		error.error(ErrorData.ErrorEnum.INVALID_VALUE).message("Bad format.");
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserIsBlockedException.class)
	@ResponseBody
	public ResponseEntity<ErrorData> userIsBlocked()
	{
		ErrorData error = new ErrorData();
		error.error(ErrorData.ErrorEnum.USER_IS_BLOCKED)
				.message("One or more user has blocked you.");
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ChatNotFoundException.class)
	@ResponseBody
	public ResponseEntity<ErrorData> chatNotFound()
	{
		ErrorData error = new ErrorData();
		error.error(ErrorData.ErrorEnum.CHAT_NOT_FOUND).message("Chat not found.");
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserNotPartOfChatException.class)
	@ResponseBody
	public ResponseEntity<ErrorData> userNotPartOfChat()
	{
		ErrorData error = new ErrorData();
		error.error(ErrorData.ErrorEnum.USER_NOT_PART_OF_CHAT)
				.message("You are not part of this chat.");
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
