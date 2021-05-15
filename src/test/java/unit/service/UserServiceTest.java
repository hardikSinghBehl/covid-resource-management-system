package unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hardik.pomfrey.dto.UserDetailDto;
import com.hardik.pomfrey.entity.Request;
import com.hardik.pomfrey.entity.User;
import com.hardik.pomfrey.repository.RequestRepository;
import com.hardik.pomfrey.repository.UserRepository;
import com.hardik.pomfrey.request.UserCreationRequest;
import com.hardik.pomfrey.request.UserLoginRequest;
import com.hardik.pomfrey.request.UserPasswordUpdationRequest;
import com.hardik.pomfrey.request.UserUpdationRequest;
import com.hardik.pomfrey.service.UserService;
import com.hardik.pomfrey.utility.ResponseEntityUtils;

public class UserServiceTest {

	private static final double LATITUDE = 67.2398;

	private static final double LONGITUDE = 43.0232;

	private static final String PASSWORD = "somthing-strong";

	private static final String LAST_NAME = "Behl";

	private static final String FIRST_NAME = "Hardik";

	private static final String EMAIL_ID = "hardik.behl7444@gmail.com";

	private static final String CONTACT_NUMBER = "9999236756";

	private UserService userService;

	private UserRepository userRepository;

	private PasswordEncoder passwordEncoder;

	private ResponseEntityUtils responseEntityUtils;

	private RequestRepository requestRepository;

	@BeforeEach
	void setUp() {
		userRepository = mock(UserRepository.class);
		passwordEncoder = mock(PasswordEncoder.class);
		requestRepository = mock(RequestRepository.class);
		responseEntityUtils = spy(ResponseEntityUtils.class);
		userService = new UserService(userRepository, passwordEncoder, responseEntityUtils, requestRepository);
	}

	@Test
	void create_whenCorrectInputIsProvided_userCreatedInDb() {
		final var userCreationRequest = mock(UserCreationRequest.class);
		when(userCreationRequest.getContactNumber()).thenReturn(CONTACT_NUMBER);
		when(userCreationRequest.getEmailId()).thenReturn(EMAIL_ID);
		when(userCreationRequest.getFirstName()).thenReturn(FIRST_NAME);
		when(userCreationRequest.getLastName()).thenReturn(LAST_NAME);
		when(userCreationRequest.getStateId()).thenReturn(1);
		when(userCreationRequest.getPassword()).thenReturn(PASSWORD);
		when(userCreationRequest.getLongitude()).thenReturn(LONGITUDE);
		when(userCreationRequest.getLatitude()).thenReturn(LATITUDE);
		when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());
		when(responseEntityUtils.generateUserAccountCreationResponse()).thenReturn(ResponseEntity.ok(null));

		userService.create(userCreationRequest);

		verify(userRepository).save(Mockito.any(User.class));
		verify(userCreationRequest).getContactNumber();
		verify(userCreationRequest).getEmailId();
		verify(userCreationRequest).getFirstName();
		verify(userCreationRequest).getLastName();
		verify(userCreationRequest).getLatitude();
		verify(userCreationRequest).getLongitude();
		verify(userCreationRequest).getPassword();
		verify(userCreationRequest).getStateId();
		verify(passwordEncoder).encode(userCreationRequest.getPassword());
		verify(responseEntityUtils).generateUserAccountCreationResponse();
	}

	@Test
	void update_whenCorrectInputIsGiven_userDetailsAreUpdated() {
		final var userUpdationRequest = mock(UserUpdationRequest.class);
		final var user = mock(User.class);
		when(userUpdationRequest.getContactNumber()).thenReturn(CONTACT_NUMBER);
		when(userUpdationRequest.getFirstName()).thenReturn(FIRST_NAME);
		when(userUpdationRequest.getLastName()).thenReturn(LAST_NAME);
		when(userUpdationRequest.getStateId()).thenReturn(1);
		when(userUpdationRequest.getLongitude()).thenReturn(LONGITUDE);
		when(userUpdationRequest.getLatitude()).thenReturn(LATITUDE);
		when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));
		when(responseEntityUtils.generateUserAccountDetailUpdationResponse()).thenReturn(ResponseEntity.ok(null));

		userService.update(userUpdationRequest, EMAIL_ID);

		verify(userRepository).findByEmailId(EMAIL_ID);
		verify(userRepository).save(user);
		verify(user).setContactNumber(userUpdationRequest.getContactNumber());
		verify(user).setFirstName(userUpdationRequest.getFirstName());
		verify(user).setLastName(userUpdationRequest.getLastName());
		verify(user).setStateId(userUpdationRequest.getStateId());
		verify(user).setLatitude(userUpdationRequest.getLatitude());
		verify(user).setLongitude(userUpdationRequest.getLongitude());
		verify(responseEntityUtils).generateUserAccountDetailUpdationResponse();
	}

	@Test
	void update_whenCorrectOldPasswordIsGiven_passwordIsUpdatedWithNewPassword() {
		final var userPasswordUpdationRequest = mock(UserPasswordUpdationRequest.class);
		final var user = mock(User.class);
		when(user.getPassword()).thenReturn(PASSWORD);
		when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));
		when(userPasswordUpdationRequest.getOldPassword()).thenReturn(PASSWORD);
		when(userPasswordUpdationRequest.getNewPassword()).thenReturn(RandomStringUtils.random(5));
		when(passwordEncoder.matches(userPasswordUpdationRequest.getOldPassword(), user.getPassword()))
				.thenReturn(true);
		when(passwordEncoder.encode(userPasswordUpdationRequest.getNewPassword())).thenReturn("ENCODED");
		when(userRepository.save(user)).thenReturn(user);
		when(responseEntityUtils.generateUserPasswordUpdationResponse()).thenReturn(ResponseEntity.ok(null));

		userService.update(userPasswordUpdationRequest, EMAIL_ID);

		verify(responseEntityUtils, times(0)).generateWrongOldPasswordResponse();
		verify(responseEntityUtils).generateUserPasswordUpdationResponse();
		verify(user).setPassword("ENCODED");
		verify(userRepository).save(user);
	}

	@Test
	void update_whenWrongOldPasswordIsGiven_exceptionIsThrown() {
		final var userPasswordUpdationRequest = mock(UserPasswordUpdationRequest.class);
		final var user = mock(User.class);
		when(user.getPassword()).thenReturn(PASSWORD);
		when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));
		when(userPasswordUpdationRequest.getOldPassword()).thenReturn(RandomStringUtils.random(5));
		when(userPasswordUpdationRequest.getNewPassword()).thenReturn(RandomStringUtils.random(5));
		when(passwordEncoder.matches(userPasswordUpdationRequest.getOldPassword(), user.getPassword()))
				.thenReturn(false);
		when(passwordEncoder.encode(userPasswordUpdationRequest.getNewPassword())).thenReturn("ENCODED");
		when(userRepository.save(user)).thenReturn(user);

		final var response = userService.update(userPasswordUpdationRequest, EMAIL_ID);

		verify(responseEntityUtils).generateWrongOldPasswordResponse();
		verify(responseEntityUtils, times(0)).generateUserPasswordUpdationResponse();
		verify(user, times(0)).setPassword(Mockito.anyString());
		verify(userRepository, times(0)).save(user);
		assertEquals(HttpStatus.EXPECTATION_FAILED.value(), response.getStatusCodeValue());
	}

	@Test
	void retreive_whenValidEmailIdIsProvided_returnUserDetailDto() {
		final var user = mock(User.class);
		when(user.getContactNumber()).thenReturn(CONTACT_NUMBER);
		when(user.getEmailId()).thenReturn(EMAIL_ID);
		when(user.getFirstName()).thenReturn(FIRST_NAME);
		when(user.getLastName()).thenReturn(LAST_NAME);
		when(user.getLatitude()).thenReturn(LATITUDE);
		when(user.getLongitude()).thenReturn(LONGITUDE);
		when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));

		final var response = userService.retrieve(EMAIL_ID);
		final var userDetailDtoRecieved = response.getBody();

		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		assertThat(response.getBody()).isInstanceOf(UserDetailDto.class);
		assertEquals(EMAIL_ID, userDetailDtoRecieved.getEmailId());
		assertEquals(FIRST_NAME, userDetailDtoRecieved.getFirstName());
		assertEquals(LAST_NAME, userDetailDtoRecieved.getLastName());
		assertEquals(CONTACT_NUMBER, userDetailDtoRecieved.getContactNumber());
		assertEquals(LATITUDE, userDetailDtoRecieved.getLatitude());
		assertEquals(LONGITUDE, userDetailDtoRecieved.getLongitude());
	}

	@Test
	void retreive_whenWrongEmailIdIsProvided_throwError() {
		when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> userService.retrieve(EMAIL_ID));
	}

	@Test
	void login_whenCorrectCredentialsAreProvided_jwtIsReturned() {
		final var userLoginRequest = mock(UserLoginRequest.class);
		final var user = mock(User.class);
		when(user.getPassword()).thenReturn(PASSWORD);
		when(userLoginRequest.getEmailId()).thenReturn(EMAIL_ID);
		when(userLoginRequest.getPassword()).thenReturn(PASSWORD);
		when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));
		when(passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())).thenReturn(true);
		Mockito.doReturn(ResponseEntity.ok(null)).when(responseEntityUtils).generateSuccessLoginResponse(user);

		final var response = userService.login(userLoginRequest);

		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	}

	@Test
	void login_whenWrongEmailIdIsProvided_throwException() {
		final var userLoginRequest = mock(UserLoginRequest.class);
		when(userLoginRequest.getEmailId()).thenReturn(EMAIL_ID);
		when(userLoginRequest.getPassword()).thenReturn(PASSWORD);
		when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> userService.login(userLoginRequest));
	}

	@Test
	void login_whenWrongPasswordIsProvided_throwException() {
		final var userLoginRequest = mock(UserLoginRequest.class);
		final var user = mock(User.class);
		when(user.getPassword()).thenReturn(RandomStringUtils.random(10));
		when(userLoginRequest.getEmailId()).thenReturn(EMAIL_ID);
		when(userLoginRequest.getPassword()).thenReturn(PASSWORD);
		when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));
		when(passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())).thenReturn(false);

		assertThrows(UsernameNotFoundException.class, () -> userService.login(userLoginRequest));
	}

	@Test
	void retreiveCredibility_whenCorrectEmailIdIsProvided_returnsUsersCredibilityPointsRepresentingRequestsFulfilled() {
		final var user = mock(User.class);
		final var userId = UUID.randomUUID();
		when(user.getId()).thenReturn(userId);
		when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));
		when(requestRepository.findByFulfilledByUserId(userId)).thenReturn(List.of(new Request(), new Request()));

		final var response = userService.retreiveCredibility(EMAIL_ID);

		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		assertTrue(response.getBody().toString().contains("\"credibility\":2"));
	}

	@Test
	void rereiveFollowers_whenCorrectEmailIdProvided_returnsListOfUsersFollowingLoggedInUser() {
		final var user = mock(User.class);
		when(user.getFollowers()).thenReturn(Set.of());
		when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));

		final var response = userService.rereiveFollowers(EMAIL_ID);

		assertNotNull(response.getBody());
		assertEquals(0, response.getBody().size());
	}

	@Test
	void rereiveFollowing_whenCorrectEmailIdProvided_returnsListOfUsersLoggedInUserIsFollowing() {
		final var user = mock(User.class);
		when(user.getFollowing()).thenReturn(Set.of());
		when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));

		final var response = userService.rereiveFollowing(EMAIL_ID);

		assertNotNull(response.getBody());
		assertEquals(0, response.getBody().size());
	}

}
