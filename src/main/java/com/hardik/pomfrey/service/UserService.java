package com.hardik.pomfrey.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hardik.pomfrey.repository.UserRepository;
import com.hardik.pomfrey.request.UserCreationRequest;
import com.hardik.pomfrey.request.UserPasswordUpdationRequest;
import com.hardik.pomfrey.request.UserUpdationRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public ResponseEntity<?> create(final UserCreationRequest userCreationRequest) {
		return null;
	}

	public ResponseEntity<?> update(final UserUpdationRequest userUpdationRequest, final String emailId) {
		return null;
	}

	public ResponseEntity<?> update(final UserPasswordUpdationRequest userPasswordUpdationRequest,
			final String emailId) {
		return null;
	}

	public ResponseEntity<?> retrieve(final String emailId) {
		return null;
	}

}
