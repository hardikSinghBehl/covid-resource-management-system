package com.hardik.pomfrey.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hardik.pomfrey.request.FollowToggleRequest;
import com.hardik.pomfrey.service.FollowMappingService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/follow")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.DELETE,
		RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.OPTIONS, RequestMethod.PATCH, RequestMethod.POST,
		RequestMethod.PUT, RequestMethod.TRACE })
public class FollowController {

	private final FollowMappingService followMappingService;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Toggle's Follow For The User(s)")
	public ResponseEntity<?> followToggleHandler(
			@Valid @RequestBody(required = true) final FollowToggleRequest followToggleRequest) {
		return followMappingService.toggle(followToggleRequest,
				SecurityContextHolder.getContext().getAuthentication().getName());
	}

}
