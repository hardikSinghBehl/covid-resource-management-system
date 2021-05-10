package com.hardik.pomfrey.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hardik.pomfrey.repository.ResourceRepository;
import com.hardik.pomfrey.request.ResourceCreationRequest;
import com.hardik.pomfrey.request.ResourceDetailUpdationRequest;
import com.hardik.pomfrey.request.ResourceStateUpdationRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ResourceService {

	private final ResourceRepository resourceRepository;

	public ResponseEntity<?> create(final ResourceCreationRequest resourceCreationRequest, final String emailId) {
		return null;
	}

	public ResponseEntity<?> update(final ResourceDetailUpdationRequest resourceDetailUpdationRequest,
			final String emailId) {
		return null;
	}

	public ResponseEntity<?> update(final ResourceStateUpdationRequest resourceStateUpdationRequest,
			final String emailId) {
		return null;
	}

}
