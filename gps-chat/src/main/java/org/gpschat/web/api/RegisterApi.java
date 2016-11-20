package org.gpschat.web.api;

import org.gpschat.web.data.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-17T14:00:30.041Z")

@Api(value = "register", description = "the register API")
public interface RegisterApi
{

	@ApiOperation(value = "Register to the service.", notes = "You can register to the service on this route.", response = Void.class, tags = {
			"user", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Registration successful.", response = Void.class),
			@ApiResponse(code = 400, message = "Bad format.", response = Void.class) })
	@RequestMapping(value = "/register", produces = {
			"application/json" }, method = RequestMethod.POST)
	default ResponseEntity<Void> registerPost(
			@ApiParam(value = "", required = true) @RequestBody User user)
	{
		// do some magic!
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
