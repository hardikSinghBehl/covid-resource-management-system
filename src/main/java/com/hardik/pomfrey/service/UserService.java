package com.hardik.pomfrey.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hardik.pomfrey.constants.Response;
import com.hardik.pomfrey.dto.UserDetailDto;
import com.hardik.pomfrey.entity.User;
import com.hardik.pomfrey.repository.RequestRepository;
import com.hardik.pomfrey.repository.UserRepository;
import com.hardik.pomfrey.request.UserCreationRequest;
import com.hardik.pomfrey.request.UserLoginRequest;
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

	private final RequestRepository requestRepository;

	public ResponseEntity<?> create(final UserCreationRequest userCreationRequest) {
		final var user = new User();
		user.setFirstName(userCreationRequest.getFirstName());
		user.setLastName(userCreationRequest.getLastName());
		user.setContactNumber(userCreationRequest.getContactNumber());
		user.setEmailId(userCreationRequest.getEmailId());
		user.setStateId(userCreationRequest.getStateId());
		user.setPassword(passwordEncoder.encode(userCreationRequest.getPassword()));
		user.setLongitude(userCreationRequest.getLongitude());
		user.setLatitude(userCreationRequest.getLatitude());

		userRepository.save(user);

		return responseEntityUtils.generateUserAccountCreationResponse();
	}

	public ResponseEntity<?> update(final UserUpdationRequest userUpdationRequest, final String emailId) {
		final var user = userRepository.findByEmailId(emailId).get();
		user.setFirstName(userUpdationRequest.getFirstName());
		user.setLastName(userUpdationRequest.getLastName());
		user.setContactNumber(userUpdationRequest.getContactNumber());
		user.setStateId(userUpdationRequest.getStateId());
		user.setLongitude(userUpdationRequest.getLongitude());
		user.setLatitude(userUpdationRequest.getLatitude());

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
		return ResponseEntity.ok(UserDetailDto.builder().contactNumber(user.getContactNumber())
				.emailId(user.getEmailId()).firstName(user.getFirstName()).lastName(user.getLastName())
				.longitude(user.getLongitude()).latitude(user.getLatitude()).build());
	}

	public ResponseEntity<?> retreiveCredibility(String emailId) {
		final var response = new JSONObject();

		final var user = userRepository.findByEmailId(emailId).get();
		final var credibility = requestRepository.findByFulfilledByUserId(user.getId()).size();

		try {
			response.put(Response.KEY.STATUS, HttpStatus.OK.value());
			response.put(Response.KEY.CREDIBILITY, credibility);
			response.put(Response.KEY.TIMESTAMP, LocalDateTime.now().toString());

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(response.toString());
	}

	public ResponseEntity<List<UserDetailDto>> rereiveFollowers(String emailId) {
		final var user = userRepository.findByEmailId(emailId).get();
		return ResponseEntity.ok(user.getFollowers().parallelStream().map(follower -> {
			final var followerUser = follower.getFollowerUser();
			return UserDetailDto.builder().contactNumber(followerUser.getContactNumber())
					.emailId(followerUser.getEmailId()).firstName(followerUser.getFirstName())
					.lastName(followerUser.getLastName()).build();
		}).collect(Collectors.toList()));
	}

	public ResponseEntity<List<UserDetailDto>> rereiveFollowing(String emailId) {
		final var user = userRepository.findByEmailId(emailId).get();
		return ResponseEntity.ok(user.getFollowers().parallelStream().map(follower -> {
			final var followerUser = follower.getFollowedUser();
			return UserDetailDto.builder().contactNumber(followerUser.getContactNumber())
					.emailId(followerUser.getEmailId()).firstName(followerUser.getFirstName())
					.lastName(followerUser.getLastName()).build();
		}).collect(Collectors.toList()));
	}

	public ResponseEntity<?> login(final UserLoginRequest userLoginRequest) {
		final var user = userRepository.findByEmailId(userLoginRequest.getEmailId()).get();
		if (passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
			return responseEntityUtils.generateSuccessLoginResponse(user);
		}
		throw new UsernameNotFoundException("Bad credentials");
	}

}
