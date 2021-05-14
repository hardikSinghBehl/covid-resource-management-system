package com.hardik.pomfrey.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hardik.pomfrey.constants.ItemType;
import com.hardik.pomfrey.dto.ResourceDto;
import com.hardik.pomfrey.entity.Resource;
import com.hardik.pomfrey.repository.ReportMappingRepository;
import com.hardik.pomfrey.repository.ResourceRepository;
import com.hardik.pomfrey.repository.UserRepository;
import com.hardik.pomfrey.request.ResourceCreationRequest;
import com.hardik.pomfrey.request.ResourceDetailUpdationRequest;
import com.hardik.pomfrey.request.ResourceStateUpdationRequest;
import com.hardik.pomfrey.utility.ResponseEntityUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ResourceService {

	private final ResourceRepository resourceRepository;

	private final UserRepository userRepository;

	private final ResponseEntityUtils responseEntityUtils;

	private final ReportMappingRepository reportMappingRepository;

	public ResponseEntity<?> create(final ResourceCreationRequest resourceCreationRequest, final String emailId) {
		final var user = userRepository.findByEmailId(emailId).get();
		final var resource = new Resource();

		resource.setCount(resourceCreationRequest.getCount());
		resource.setDescription(resourceCreationRequest.getDescription());
		resource.setIsActive(true);
		resource.setResourceTypeId(resourceCreationRequest.getResourceTypeId());
		resource.setTitle(resourceCreationRequest.getTitle());
		resource.setUserId(user.getId());
		resource.setLatitude(resourceCreationRequest.getLatitude());
		resource.setLongitude(resourceCreationRequest.getLongitude());

		resourceRepository.save(resource);

		return responseEntityUtils.generateResourceCreationResponse();
	}

	public ResponseEntity<?> update(final ResourceDetailUpdationRequest resourceDetailUpdationRequest,
			final String emailId) {
		final var user = userRepository.findByEmailId(emailId).get();
		final var resource = resourceRepository.findById(resourceDetailUpdationRequest.getId()).get();

		if (!user.getId().equals(resource.getUserId()))
			return responseEntityUtils.generateUnauthorizedResponse();

		resource.setCount(resourceDetailUpdationRequest.getCount());
		resource.setDescription(resourceDetailUpdationRequest.getDescription());
		resource.setLatitude(resourceDetailUpdationRequest.getLatitude());
		resource.setLongitude(resourceDetailUpdationRequest.getLongitude());

		resourceRepository.save(resource);

		return responseEntityUtils.generateResourceUpdationResponse();
	}

	public ResponseEntity<?> update(final ResourceStateUpdationRequest resourceStateUpdationRequest,
			final String emailId) {
		final var user = userRepository.findByEmailId(emailId).get();
		final var resource = resourceRepository.findById(resourceStateUpdationRequest.getId()).get();

		if (!user.getId().equals(resource.getUserId()))
			return responseEntityUtils.generateUnauthorizedResponse();

		resource.setIsActive(false);
		resourceRepository.save(resource);

		return responseEntityUtils.generateResourceUpdationResponse();
	}

	public ResponseEntity<List<ResourceDto>> retreiveForUser(String emailId) {
		final var user = userRepository.findByEmailId(emailId).get();
		return ResponseEntity.ok(user.getResources().parallelStream()
				.map(resource -> ResourceDto.builder().count(resource.getCount()).description(resource.getDescription())
						.emailId(user.getEmailId()).fullName(user.getFirstName() + " " + user.getLastName())
						.id(resource.getId()).isActive(resource.getIsActive()).title(resource.getTitle())
						.longitude(resource.getLongitude()).latitude(resource.getLatitude())
						.resourceType(resource.getResourceType().getName()).build())
				.collect(Collectors.toList()));
	}

	public ResponseEntity<List<ResourceDto>> reteive(String emailId) {
		final var user = userRepository.findByEmailId(emailId).get();
		return ResponseEntity.ok(resourceRepository.findNearestResources(user.getLatitude(), user.getLongitude())
				.parallelStream().map(resource -> {
					final var resourceOwner = resource.getUser();
					return ResourceDto.builder().count(resource.getCount()).description(resource.getDescription())
							.emailId(resourceOwner.getEmailId())
							.fullName(resourceOwner.getFirstName() + " " + resourceOwner.getLastName())
							.id(resource.getId()).isActive(resource.getIsActive()).latitude(resource.getLatitude())
							.longitude(resource.getLongitude()).resourceType(resource.getResourceType().getName())
							.title(resource.getTitle()).build();
				}).limit(50).collect(Collectors.toList()));
	}

	public void handleReports() {
		resourceRepository.findAll().parallelStream().filter(resource -> resource.getIsActive()).forEach(resource -> {
			if (reportMappingRepository.findByItemTypeAndItemId(ItemType.RESOURCE, resource.getId()).size() >= 10) {
				resource.setIsActive(false);
				resourceRepository.save(resource);
			}
		});
	}

}
