package com.hardik.pomfrey.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hardik.pomfrey.request.CommentCreationRequest;
import com.hardik.pomfrey.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/comment")
public class CommentController {

	private final CommentService commentService;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Creates A New Comment Under The Mentioned Context")
	public ResponseEntity<?> commentCreationHandler(
			@Valid @RequestBody(required = true) final CommentCreationRequest commentCreationRequest) {
		return commentService.create(commentCreationRequest,
				SecurityContextHolder.getContext().getAuthentication().getName());
	}

	@DeleteMapping(value = "/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Deletes The Comment")
	public ResponseEntity<?> commentDeletionHandler(
			@PathVariable(name = "commentId", required = true) final UUID commentId) {
		return commentService.delete(commentId, SecurityContextHolder.getContext().getAuthentication().getName());
	}
}
