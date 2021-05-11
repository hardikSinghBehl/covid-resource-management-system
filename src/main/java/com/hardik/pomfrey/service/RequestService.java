package com.hardik.pomfrey.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hardik.pomfrey.dto.RequestDto;
import com.hardik.pomfrey.entity.Request;
import com.hardik.pomfrey.repository.RequestRepository;
import com.hardik.pomfrey.repository.UserRepository;
import com.hardik.pomfrey.request.RequestCreationRequest;
import com.hardik.pomfrey.request.RequestDetailUpdationRequest;
import com.hardik.pomfrey.request.RequestStateUpdationRequest;
import com.hardik.pomfrey.utility.ResponseEntityUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RequestService {

	private final RequestRepository requestRepository;

	private final UserRepository userRepository;

	private final ResponseEntityUtils responseEntityUtils;

	public ResponseEntity<?> create(final RequestCreationRequest requestCreationRequest, final String emailId) {
		final var user = userRepository.findByEmailId(emailId).get();
		final var request = new Request();

		request.setResourceTypeId(requestCreationRequest.getResourceTypeId());
		request.setTitle(requestCreationRequest.getTitle());
		request.setDescription(requestCreationRequest.getDescription());
		request.setIsActive(true);
		request.setRequestedByUserId(user.getId());
		request.setLocation(null);

		requestRepository.save(request);

		return responseEntityUtils.generateRequestCreationResponse();
	}

	public ResponseEntity<?> update(final RequestDetailUpdationRequest requestDetailUpdationRequest,
			final String emailId) {
		final var user = userRepository.findByEmailId(emailId).get();
		final var request = requestRepository.findById(requestDetailUpdationRequest.getId()).get();

		if (!user.getId().equals(request.getRequestedByUserId()))
			return responseEntityUtils.generateUnauthorizedResponse();

		request.setDescription(requestDetailUpdationRequest.getDescription());
		request.setLocation(null);

		requestRepository.save(request);

		return responseEntityUtils.generateRequestUpdationResponse();
	}

	public ResponseEntity<?> update(final RequestStateUpdationRequest requestStateUpdationRequest,
			final String emailId) {
		final var user = userRepository.findByEmailId(emailId).get();
		final var request = requestRepository.findById(requestStateUpdationRequest.getId()).get();

		if (!user.getId().equals(request.getRequestedByUserId()))
			return responseEntityUtils.generateUnauthorizedResponse();

		request.setIsActive(false);
		if (requestStateUpdationRequest.getFulfilled().equals(true)
				&& requestStateUpdationRequest.getFulfilledByEmailId() != null) {
			request.setFulfilledByUserId(
					userRepository.findByEmailId(requestStateUpdationRequest.getFulfilledByEmailId()).get().getId());
		}

		requestRepository.save(request);

		return responseEntityUtils.generateRequestUpdationResponse();
	}

	public ResponseEntity<List<RequestDto>> retreiveForUser(String emailId) {
		final var user = userRepository.findByEmailId(emailId).get();
		return ResponseEntity.ok(user.getRequested().parallelStream().map(request -> {
			final var requestedByUser = request.getRequestedByUser();
			final var location = request.getLocation();
			return RequestDto.builder().description(request.getDescription()).emailId(requestedByUser.getEmailId())
					.fullName(requestedByUser.getFirstName() + " " + requestedByUser.getLastName()).id(request.getId())
					.isActive(request.getIsActive()).title(request.getTitle()).latitude(location.getY())
					.longitude(location.getX()).resourceType(request.getResourceType().getName()).build();
		}).collect(Collectors.toList()));
	}

}
