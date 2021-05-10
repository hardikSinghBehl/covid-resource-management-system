package com.hardik.pomfrey.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hardik.pomfrey.repository.FollowMappingRepository;
import com.hardik.pomfrey.request.FollowToggleRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FollowMappingService {

	private final FollowMappingRepository followMappingRepository;

	public ResponseEntity<?> toggle(final FollowToggleRequest followToggleRequest, final String emailId) {
		return null;
	}

}
