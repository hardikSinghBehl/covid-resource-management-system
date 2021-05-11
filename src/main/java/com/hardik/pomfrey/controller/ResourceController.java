package com.hardik.pomfrey.controller;

import java.util.List;

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

import com.hardik.pomfrey.dto.ResourceDto;
import com.hardik.pomfrey.entity.MasterResourceType;
import com.hardik.pomfrey.request.ResourceCreationRequest;
import com.hardik.pomfrey.request.ResourceDetailUpdationRequest;
import com.hardik.pomfrey.request.ResourceStateUpdationRequest;
import com.hardik.pomfrey.service.MasterResourceTypeService;
import com.hardik.pomfrey.service.ResourceService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/resource")
public class ResourceController {

	private final ResourceService resourceService;

	private final MasterResourceTypeService masterResourceTypeService;

	@GetMapping(value = "/type", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Returns List Of Master Resource Types Available")
	public ResponseEntity<List<MasterResourceType>> resourceTypeRetreivalHandler() {
		return masterResourceTypeService.retreive();
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Returns List Of Resources Nearest To The Logged In User")
	public ResponseEntity<List<ResourceDto>> nearestResourcesReteivalHandler() {
		return resourceService.reteive(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	@GetMapping("/user")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Returns List Of Resources That User Has Submitted")
	public ResponseEntity<List<ResourceDto>> resourcesForUserRetreivalHandler() {
		return resourceService.retreiveForUser(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Creates A New Resource In The Database")
	public ResponseEntity<?> resourceCreationHandler(
			@Valid @RequestBody(required = true) final ResourceCreationRequest resourceCreationRequest) {
		return resourceService.create(resourceCreationRequest,
				SecurityContextHolder.getContext().getAuthentication().getName());
	}

	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Updates Resource Details In The Database")
	public ResponseEntity<?> resourceDetailUpdationRequest(
			@Valid @RequestBody(required = true) final ResourceDetailUpdationRequest resourceDetailUpdationRequest) {
		return resourceService.update(resourceDetailUpdationRequest,
				SecurityContextHolder.getContext().getAuthentication().getName());
	}

	@PutMapping(value = "/state", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Updates Resource State (In-Active) In The Database")
	public ResponseEntity<?> resourceStateUpdationHandler(
			@Valid @RequestBody(required = true) final ResourceStateUpdationRequest resourceStateUpdationRequest) {
		return resourceService.update(resourceStateUpdationRequest,
				SecurityContextHolder.getContext().getAuthentication().getName());
	}

}
