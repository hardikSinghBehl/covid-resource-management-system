package com.hardik.pomfrey.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hardik.pomfrey.request.UserCreationRequest;
import com.hardik.pomfrey.request.UserPasswordUpdationRequest;
import com.hardik.pomfrey.request.UserUpdationRequest;
import com.hardik.pomfrey.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Returns User Details Of The Logged In User")
	public ResponseEntity<?> retreive() {
		return userService.retrieve(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	@PostMapping(value = "/pre-register/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Creates A New User In The Database")
	public ResponseEntity<?> userCreationHandler(
			@Valid @RequestBody(required = true) final UserCreationRequest userCreationRequest) {
		return userService.create(userCreationRequest);
	}

	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Updates The Details Of The User In The Database")
	public ResponseEntity<?> userDetailUpdationHandler(
			@Valid @RequestBody(required = true) final UserUpdationRequest userUpdationRequest) {
		return userService.update(userUpdationRequest);
	}

	@PutMapping(value = "/password", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Changes User's Password If Old Password Was Correctly Provided")
	public ResponseEntity<?> userPasswordUpdationHandler(
			@Valid @RequestBody(required = true) final UserPasswordUpdationRequest userPasswordUpdationRequest) {
		return userService.update(userPasswordUpdationRequest);
	}

}
