package com.hardik.pomfrey.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hardik.pomfrey.repository.RequestRepository;
import com.hardik.pomfrey.request.RequestCreationRequest;
import com.hardik.pomfrey.request.RequestDetailUpdationRequest;
import com.hardik.pomfrey.request.RequestStateUpdationRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RequestService {

	private final RequestRepository requestRepository;

	public ResponseEntity<?> create(final RequestCreationRequest requestCreationRequest, final String emailId) {
		return null;
	}

	public ResponseEntity<?> update(final RequestDetailUpdationRequest requestDetailUpdationRequest,
			final String emailId) {
		return null;
	}

	public ResponseEntity<?> update(final RequestStateUpdationRequest requestStateUpdationRequest,
			final String emailId) {
		return null;
	}

}
