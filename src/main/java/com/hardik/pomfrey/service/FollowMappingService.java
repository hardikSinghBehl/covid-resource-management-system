package com.hardik.pomfrey.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hardik.pomfrey.entity.FollowMapping;
import com.hardik.pomfrey.repository.FollowMappingRepository;
import com.hardik.pomfrey.repository.UserRepository;
import com.hardik.pomfrey.request.FollowToggleRequest;
import com.hardik.pomfrey.utility.ResponseEntityUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FollowMappingService {

	private final FollowMappingRepository followMappingRepository;

	private final UserRepository userRepository;

	private final ResponseEntityUtils responseEntityUtils;

	public ResponseEntity<?> toggle(final FollowToggleRequest followToggleRequest, final String emailId) {
		final var user = userRepository.findByEmailId(emailId).get();
		final var followMapping = followMappingRepository
				.findByFollowerUserIdAndFollowedUserId(user.getId(), followToggleRequest.getUserId())
				.orElse(new FollowMapping());
		followMapping.setFollowedUserId(followToggleRequest.getUserId());
		followMapping.setFollowerUserId(user.getId());
		if (followMapping.getIsActive() != null)
			followMapping.setIsActive(!followMapping.getIsActive());
		else
			followMapping.setIsActive(true);

		followMappingRepository.save(followMapping);

		return responseEntityUtils.generateFollowToggleResponse();
	}

}
