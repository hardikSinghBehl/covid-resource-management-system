package com.hardik.pomfrey.service;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hardik.pomfrey.dto.UserDetailDto;
import com.hardik.pomfrey.entity.User;
import com.hardik.pomfrey.repository.UserRepository;
import com.hardik.pomfrey.request.UserCreationRequest;
import com.hardik.pomfrey.request.UserPasswordUpdationRequest;
import com.hardik.pomfrey.request.UserUpdationRequest;
import com.hardik.pomfrey.utility.ResponseEntityUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final ResponseEntityUtils responseEntityUtils;

	public ResponseEntity<?> create(final UserCreationRequest userCreationRequest) {
		final var user = new User();
		user.setFirstName(userCreationRequest.getFirstName());
		user.setLastName(userCreationRequest.getLastName());
		user.setContactNumber(userCreationRequest.getContactNumber());
		user.setEmailId(userCreationRequest.getEmailId());
		user.setStateId(userCreationRequest.getStateId());
		user.setPassword(passwordEncoder.encode(userCreationRequest.getPassword()));
		user.setLocation(new Point((CoordinateSequence) new Coordinate(userCreationRequest.getLongitude(),
				userCreationRequest.getLatitude()), new GeometryFactory()));

		userRepository.save(user);

		return responseEntityUtils.generateUserAccountCreationResponse();
	}

	public ResponseEntity<?> update(final UserUpdationRequest userUpdationRequest, final String emailId) {
		final var user = userRepository.findByEmailId(emailId).get();
		user.setFirstName(userUpdationRequest.getFirstName());
		user.setLastName(userUpdationRequest.getLastName());
		user.setContactNumber(userUpdationRequest.getContactNumber());
		user.setStateId(userUpdationRequest.getStateId());
		user.setLocation(new Point((CoordinateSequence) new Coordinate(userUpdationRequest.getLongitude(),
				userUpdationRequest.getLatitude()), new GeometryFactory()));

		userRepository.save(user);

		return responseEntityUtils.generateUserAccountDetailUpdationResponse();
	}

	public ResponseEntity<?> update(final UserPasswordUpdationRequest userPasswordUpdationRequest,
			final String emailId) {
		final var user = userRepository.findByEmailId(emailId).get();

		if (passwordEncoder.matches(userPasswordUpdationRequest.getOldPassword(), user.getPassword())) {
			user.setPassword(passwordEncoder.encode(userPasswordUpdationRequest.getNewPassword()));
			userRepository.save(user);
		} else
			return responseEntityUtils.generateWrongOldPasswordResponse();
		return responseEntityUtils.generateUserPasswordUpdationResponse();

	}

	public ResponseEntity<UserDetailDto> retrieve(final String emailId) {
		final var user = userRepository.findByEmailId(emailId).get();
		Point location = user.getLocation();
		return ResponseEntity.ok(UserDetailDto.builder().contactNumber(user.getContactNumber())
				.emailId(user.getEmailId()).firstName(user.getFirstName()).lastName(user.getLastName())
				.longitude(location.getX()).latitude(location.getY()).build());
	}

}
