package com.hardik.pomfrey.service;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hardik.pomfrey.repository.CommentRepository;
import com.hardik.pomfrey.request.CommentCreationRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;

	public ResponseEntity<?> create(final CommentCreationRequest commentCreationRequest, final String emailId) {
		return null;
	}

	public ResponseEntity<?> delete(final UUID commentId, final String string) {
		return null;
	}

}
