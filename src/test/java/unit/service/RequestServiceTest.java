package unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import com.hardik.pomfrey.entity.Request;
import com.hardik.pomfrey.entity.User;
import com.hardik.pomfrey.repository.ReportMappingRepository;
import com.hardik.pomfrey.repository.RequestRepository;
import com.hardik.pomfrey.repository.UserRepository;
import com.hardik.pomfrey.request.RequestCreationRequest;
import com.hardik.pomfrey.request.RequestDetailUpdationRequest;
import com.hardik.pomfrey.request.RequestStateUpdationRequest;
import com.hardik.pomfrey.service.RequestService;
import com.hardik.pomfrey.utility.ResponseEntityUtils;

class RequestServiceTest {

	private static final String EMAIL_ID = "hardik.behl7444@gmail.com";
	private static final double LATITUDE = 17.9897;
	private static final double LONGITUDE = 34.990;
	private static final String REQUEST_TITLE = "Help me!";
	private static final String REQUEST_DESCRIPTION = "Oxygen Bed Needed!";
	private RequestService requestService;
	private RequestRepository requestRepository;
	private UserRepository userRepository;
	private ResponseEntityUtils responseEntityUtils;
	private ReportMappingRepository reportMappingRepository;

	@BeforeEach
	void setUp() {
		requestRepository = mock(RequestRepository.class);
		userRepository = mock(UserRepository.class);
		responseEntityUtils = spy(ResponseEntityUtils.class);
		reportMappingRepository = mock(ReportMappingRepository.class);
		requestService = new RequestService(requestRepository, userRepository, responseEntityUtils,
				reportMappingRepository);
	}

	@Test
	void create_whenValidInputsAreProvided_requestSubmittedInDatabaseSuccessfully() {
		final var requestCreationRequest = mock(RequestCreationRequest.class);
		final var user = mock(User.class);

		when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));
		when(requestCreationRequest.getDescription()).thenReturn(REQUEST_DESCRIPTION);
		when(requestCreationRequest.getTitle()).thenReturn(REQUEST_TITLE);
		when(requestCreationRequest.getResourceTypeId()).thenReturn(1);
		when(requestCreationRequest.getLongitude()).thenReturn(LONGITUDE);
		when(requestCreationRequest.getLatitude()).thenReturn(LATITUDE);
		when(requestRepository.save(Mockito.any(Request.class))).thenReturn(null);

		final var response = requestService.create(requestCreationRequest, EMAIL_ID);

		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		verify(requestCreationRequest).getDescription();
		verify(requestCreationRequest).getTitle();
		verify(requestCreationRequest).getResourceTypeId();
		verify(requestCreationRequest).getLatitude();
		verify(requestCreationRequest).getLongitude();
		verify(requestRepository).save(Mockito.any(Request.class));
	}

	@Test
	void create_whenWrongEmailIdIsProvided_throwException() {
		final var requestCreationRequest = mock(RequestCreationRequest.class);
		when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> requestService.create(requestCreationRequest, EMAIL_ID));

		verify(requestRepository, times(0)).save(Mockito.any(Request.class));
	}

	@Test
	void update__details_whenCorrectInputsAreProvided_requestDetailsAreUpdatedInDatabase() {
		final var requestDetailUpdationRequest = mock(RequestDetailUpdationRequest.class);
		final var requestId = UUID.randomUUID();
		final var userId = UUID.randomUUID();
		final var user = mock(User.class);
		final var request = mock(Request.class);

		when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));
		when(requestRepository.findById(requestId)).thenReturn(Optional.of(request));
		when(requestDetailUpdationRequest.getDescription()).thenReturn(REQUEST_DESCRIPTION);
		when(requestDetailUpdationRequest.getLatitude()).thenReturn(LATITUDE);
		when(requestDetailUpdationRequest.getLongitude()).thenReturn(LONGITUDE);
		when(requestDetailUpdationRequest.getId()).thenReturn(requestId);
		when(user.getId()).thenReturn(userId);
		when(request.getRequestedByUserId()).thenReturn(userId);
		when(requestRepository.save(request)).thenReturn(request);

		final var response = requestService.update(requestDetailUpdationRequest, EMAIL_ID);

		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		verify(requestDetailUpdationRequest).getDescription();
		verify(requestDetailUpdationRequest).getLongitude();
		verify(requestDetailUpdationRequest).getLatitude();
		verify(requestDetailUpdationRequest).getId();
		verify(requestRepository).save(request);
	}

	@Test
	void update_details_whenUserOtherThanRequestSubmitterTriesToUpdate_throwError() {
		final var requestDetailUpdationRequest = mock(RequestDetailUpdationRequest.class);
		final var requestId = UUID.randomUUID();
		final var userId = UUID.randomUUID();
		final var user = mock(User.class);
		final var request = mock(Request.class);

		when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));
		when(requestRepository.findById(requestId)).thenReturn(Optional.of(request));
		when(requestDetailUpdationRequest.getDescription()).thenReturn(REQUEST_DESCRIPTION);
		when(requestDetailUpdationRequest.getLatitude()).thenReturn(LATITUDE);
		when(requestDetailUpdationRequest.getLongitude()).thenReturn(LONGITUDE);
		when(requestDetailUpdationRequest.getId()).thenReturn(requestId);
		when(user.getId()).thenReturn(userId);
		when(request.getRequestedByUserId()).thenReturn(UUID.randomUUID());
		when(requestRepository.save(request)).thenReturn(request);

		final var response = requestService.update(requestDetailUpdationRequest, EMAIL_ID);

		assertNotNull(response.getBody());
		assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCodeValue());
		verify(requestRepository, times(0)).save(request);
	}

	@Test
	void update_details_whenWrongEmailidIsProvided_throwsError() {
		when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.empty());
		final var requestDetailUpdationRequest = mock(RequestDetailUpdationRequest.class);

		assertThrows(NoSuchElementException.class, () -> requestService.update(requestDetailUpdationRequest, EMAIL_ID));

		verify(requestRepository, times(0)).save(Mockito.any(Request.class));
	}

	@Test
	void update_state_whenCorrectRequestIdIsProvidedAndRequestIsNotFulfilledByPortal_updateStateToBeInactive() {
		final var requestStateUpdationRequest = mock(RequestStateUpdationRequest.class);
		final var requestId = UUID.randomUUID();
		final var userId = UUID.randomUUID();
		final var user = mock(User.class);
		final var request = mock(Request.class);

		when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));
		when(requestRepository.findById(requestId)).thenReturn(Optional.of(request));
		when(requestStateUpdationRequest.getFulfilled()).thenReturn(false);
		when(requestStateUpdationRequest.getId()).thenReturn(requestId);
		when(user.getId()).thenReturn(userId);
		when(request.getRequestedByUserId()).thenReturn(userId);
		when(requestRepository.save(request)).thenReturn(request);

		final var response = requestService.update(requestStateUpdationRequest, EMAIL_ID);

		verify(request).setIsActive(false);
		verify(requestRepository).save(request);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());

	}

	@Test
	void update_state_whenCorrectRequestIdIsProvidedAndRequestIsFulfilledByPortal_updateStateToBeInactive() {
		final var requestStateUpdationRequest = mock(RequestStateUpdationRequest.class);
		final var requestId = UUID.randomUUID();
		final var userId = UUID.randomUUID();
		final var helperUserId = UUID.randomUUID();
		final var user = mock(User.class);
		final var request = mock(Request.class);
		final var helperUser = mock(User.class);

		when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));
		when(requestRepository.findById(requestId)).thenReturn(Optional.of(request));
		when(requestStateUpdationRequest.getFulfilled()).thenReturn(true);
		when(requestStateUpdationRequest.getId()).thenReturn(requestId);
		when(user.getId()).thenReturn(userId);
		when(request.getRequestedByUserId()).thenReturn(userId);
		when(requestRepository.save(request)).thenReturn(request);
		when(requestStateUpdationRequest.getFulfilledByEmailId()).thenReturn("helper@gmail.com");
		when(helperUser.getId()).thenReturn(helperUserId);
		when(userRepository.findByEmailId("helper@gmail.com")).thenReturn(Optional.of(helperUser));

		final var response = requestService.update(requestStateUpdationRequest, EMAIL_ID);

		verify(request).setIsActive(false);
		verify(request).setFulfilledByUserId(helperUserId);
		verify(requestRepository).save(request);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	}

	@Test
	void update_whenUserOtherThanRequestSubmitterTriesToChangeState_throwException() {
		final var requestStateUpdationRequest = mock(RequestStateUpdationRequest.class);
		final var requestId = UUID.randomUUID();
		final var user = mock(User.class);
		final var request = mock(Request.class);

		when(requestStateUpdationRequest.getId()).thenReturn(requestId);
		when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));
		when(requestRepository.findById(requestId)).thenReturn(Optional.of(request));
		when(user.getId()).thenReturn(UUID.randomUUID());
		when(request.getRequestedByUserId()).thenReturn(UUID.randomUUID());

		final var response = requestService.update(requestStateUpdationRequest, EMAIL_ID);

		verify(request, times(0)).setIsActive(false);
		verify(requestRepository, times(0)).save(request);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCodeValue());

	}

}
