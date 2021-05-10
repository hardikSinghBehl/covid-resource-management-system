package com.hardik.pomfrey.service;

import javax.validation.Valid;

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

	public ResponseEntity<?> create(@Valid UserCreationRequest userCreationRequest) {
		return null;
	}

	public ResponseEntity<?> update(@Valid UserUpdationRequest userUpdationRequest) {
		return null;
	}

	public ResponseEntity<?> update(@Valid UserPasswordUpdationRequest userPasswordUpdationRequest) {
		return null;
	}

	public ResponseEntity<?> retrieve(String emailId) {
		return null;
	}

}
