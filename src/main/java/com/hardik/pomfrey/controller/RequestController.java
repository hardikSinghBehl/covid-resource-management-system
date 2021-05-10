package com.hardik.pomfrey.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hardik.pomfrey.request.RequestCreationRequest;
import com.hardik.pomfrey.request.RequestDetailUpdationRequest;
import com.hardik.pomfrey.request.RequestStateUpdationRequest;
import com.hardik.pomfrey.service.RequestService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/request")
public class RequestController {

	private final RequestService requestService;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Creates A New Request For The Mentioned Reosurce And Location")
	public ResponseEntity<?> requestCreationHandler(
			@Valid @RequestBody(required = true) final RequestCreationRequest requestCreationRequest) {
		return requestService.create(requestCreationRequest,
				SecurityContextHolder.getContext().getAuthentication().getName());
	}

	@PutMapping(value = "/detail", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Updates Request Details For The Mentioned Reosurce And Location")
	public ResponseEntity<?> requestDetailUpdationHandler(
			@Valid @RequestBody(required = true) final RequestDetailUpdationRequest requestDetailUpdationRequest) {
		return requestService.update(requestDetailUpdationRequest,
				SecurityContextHolder.getContext().getAuthentication().getName());
	}

	@PutMapping(value = "/state", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Updates Request To Be Inactive")
	public ResponseEntity<?> requestStateUpdationHandler(
			@Valid @RequestBody(required = true) final RequestStateUpdationRequest requestStateUpdationRequest) {
		return requestService.update(requestStateUpdationRequest,
				SecurityContextHolder.getContext().getAuthentication().getName());
	}

}
