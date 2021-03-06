package com.hardik.pomfrey.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hardik.pomfrey.dto.RequestDto;
import com.hardik.pomfrey.request.RequestCreationRequest;
import com.hardik.pomfrey.request.RequestDetailUpdationRequest;
import com.hardik.pomfrey.request.RequestStateUpdationRequest;
import com.hardik.pomfrey.service.RequestService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/request")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.DELETE,
		RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.OPTIONS, RequestMethod.PATCH, RequestMethod.POST,
		RequestMethod.PUT, RequestMethod.TRACE })
public class RequestController {

	private final RequestService requestService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Returns List Of Requests Nearest To The Logged In User")
	public ResponseEntity<List<RequestDto>> nearestRequestsReteivalHandler() {
		return requestService.reteive(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	@GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Returns List Of Requests That User Has Submitted")
	public ResponseEntity<List<RequestDto>> requestsForUserRetreivalHandler() {
		return requestService.retreiveForUser(SecurityContextHolder.getContext().getAuthentication().getName());
	}

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
