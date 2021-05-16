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

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import com.hardik.pomfrey.entity.Resource;
import com.hardik.pomfrey.entity.User;
import com.hardik.pomfrey.repository.ReportMappingRepository;
import com.hardik.pomfrey.repository.ResourceRepository;
import com.hardik.pomfrey.repository.UserRepository;
import com.hardik.pomfrey.request.ResourceCreationRequest;
import com.hardik.pomfrey.request.ResourceDetailUpdationRequest;
import com.hardik.pomfrey.request.ResourceStateUpdationRequest;
import com.hardik.pomfrey.service.ResourceService;
import com.hardik.pomfrey.utility.ResponseEntityUtils;

class ResourceServiceTest {

	private static final String EMAIL_ID = "hardik.behl7444@gmail.com";
	private static final double LATITUDE = 28.56;
	private static final double LONGITUDE = 78.67;
	private static final String RESOURCE_TITLE = "Resource Title";
	private static final String RESOURCE_DESCRIPTION = "Resource Description";
	private ResourceService resourceService;
	private ResourceRepository resourceRepository;
	private UserRepository userRepository;
	private ResponseEntityUtils responseEntityUtils;
	private ReportMappingRepository reportMappingRepository;

	@BeforeEach
	void setUp() {
		resourceRepository = mock(ResourceRepository.class);
		userRepository = mock(UserRepository.class);
		responseEntityUtils = spy(ResponseEntityUtils.class);
		reportMappingRepository = mock(ReportMappingRepository.class);
		resourceService = new ResourceService(resourceRepository, userRepository, responseEntityUtils,
				reportMappingRepository);
	}

	@Nested
	@DisplayName("Resource Creation Service Is Called")
	class ResourceCreationServiceTest {

		@Test
		@DisplayName("Giving Correct Email-id and Request Inputs")
		void create_whenCorrectInputsAreProvided_createAvailableResourceInDatabase() {
			final var resourceCreationRequest = mock(ResourceCreationRequest.class);
			final var user = mock(User.class);

			when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));
			when(resourceCreationRequest.getCount()).thenReturn(1);
			when(resourceCreationRequest.getDescription()).thenReturn(RESOURCE_DESCRIPTION);
			when(resourceCreationRequest.getTitle()).thenReturn(RESOURCE_TITLE);
			when(resourceCreationRequest.getResourceTypeId()).thenReturn(1);
			when(resourceCreationRequest.getLatitude()).thenReturn(LATITUDE);
			when(resourceCreationRequest.getLongitude()).thenReturn(LONGITUDE);
			when(resourceRepository.save(Mockito.any(Resource.class))).thenReturn(new Resource());

			final var response = resourceService.create(resourceCreationRequest, EMAIL_ID);

			assertNotNull(response.getBody());
			assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
			verify(resourceCreationRequest).getDescription();
			verify(resourceCreationRequest).getTitle();
			verify(resourceCreationRequest).getLatitude();
			verify(resourceCreationRequest).getLongitude();
			verify(resourceCreationRequest).getCount();
			verify(resourceCreationRequest).getResourceTypeId();
			verify(resourceRepository).save(Mockito.any(Resource.class));
		}

		@Test
		@DisplayName("Giving Wrong Email-id")
		void create_whenWrongEmailIdIsProvided_throwException() {
			when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.empty());

			assertThrows(NoSuchElementException.class,
					() -> resourceService.create(mock(ResourceCreationRequest.class), RandomStringUtils.random(10)));
		}

	}

	@Nested
	@DisplayName("Resource Updation Service Is Called")
	class ResourceUpdationServiceTest {

		@Nested
		@DisplayName("Resource Details Updation")
		class ResourceDetailsUpdationServiceTest {

			@Test
			@DisplayName("Giving Valid Email-id And Request Input")
			void update_details_whenCorrectInputIsProvided_updateDetailsOfResourceInDatabase() {
				final var user = mock(User.class);
				final var resource = mock(Resource.class);
				final var userId = UUID.randomUUID();
				final var resourceId = UUID.randomUUID();
				final var resourceDetailUpdationRequest = mock(ResourceDetailUpdationRequest.class);

				when(resourceDetailUpdationRequest.getId()).thenReturn(resourceId);
				when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));
				when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(resource));
				when(resource.getUserId()).thenReturn(userId);
				when(user.getId()).thenReturn(userId);
				when(resourceDetailUpdationRequest.getCount()).thenReturn(2);
				when(resourceDetailUpdationRequest.getDescription()).thenReturn(RESOURCE_DESCRIPTION);
				when(resourceDetailUpdationRequest.getLatitude()).thenReturn(LATITUDE);
				when(resourceDetailUpdationRequest.getLongitude()).thenReturn(LONGITUDE);

				final var response = resourceService.update(resourceDetailUpdationRequest, EMAIL_ID);

				assertNotNull(response.getBody());
				assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
				verify(resourceDetailUpdationRequest).getDescription();
				verify(resourceDetailUpdationRequest).getCount();
				verify(resourceDetailUpdationRequest).getLatitude();
				verify(resourceDetailUpdationRequest).getLongitude();
				verify(resourceRepository).save(resource);
			}

			@Test
			@DisplayName("Giving Wrong Email-id")
			void update_details_whenWrongEmailIdIsProvided_throwException() {
				when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.empty());

				assertThrows(NoSuchElementException.class, () -> resourceService
						.update(mock(ResourceDetailUpdationRequest.class), RandomStringUtils.random(10)));
			}

			@Test
			@DisplayName("Giving User's Email-id Who Have Not Created The Resource")
			void update_details_whenUserOtherThanResourceSubmitterTriesToUpdateIt_throwsException() {
				final var user = mock(User.class);
				final var resource = mock(Resource.class);
				final var resourceId = UUID.randomUUID();
				final var resourceDetailsUpdationRequest = mock(ResourceDetailUpdationRequest.class);

				when(resourceDetailsUpdationRequest.getId()).thenReturn(resourceId);
				when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));
				when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(resource));
				when(user.getId()).thenReturn(UUID.randomUUID());
				when(resource.getUserId()).thenReturn(UUID.randomUUID());

				final var response = resourceService.update(resourceDetailsUpdationRequest, EMAIL_ID);

				assertNotNull(response.getBody());
				assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCodeValue());
				verify(resourceRepository, times(0)).save(resource);
			}

		}

		@Nested
		@DisplayName("Resource State Updation")
		class ResourceStateUpdationServiceTest {

			@Test
			@DisplayName("Giving Correct Email-id And Request Input")
			void update_state_whenCorrectInputIsProvided_updateDetailsOfResourceInDatabase() {
				final var user = mock(User.class);
				final var resource = mock(Resource.class);
				final var userId = UUID.randomUUID();
				final var resourceId = UUID.randomUUID();
				final var resourceStateUpdationRequest = mock(ResourceStateUpdationRequest.class);

				when(resourceStateUpdationRequest.getId()).thenReturn(resourceId);
				when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));
				when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(resource));
				when(resource.getUserId()).thenReturn(userId);
				when(user.getId()).thenReturn(userId);

				final var response = resourceService.update(resourceStateUpdationRequest, EMAIL_ID);

				assertNotNull(response.getBody());
				assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
				verify(resource).setIsActive(false);
				verify(resourceRepository).save(resource);
			}

			@Test
			@DisplayName("Giving Wrong Email-id")
			void update_state_whenWrongEmailIdIsProvided_throwException() {
				when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.empty());

				assertThrows(NoSuchElementException.class, () -> resourceService
						.update(mock(ResourceStateUpdationRequest.class), RandomStringUtils.random(10)));
			}

			@Test
			@DisplayName("Giving User's Email-id Who Have Not Created The Resource")
			void update_state_whenUserOtherThanResourceSubmitterTriesToUpdateIt_throwsException() {
				final var user = mock(User.class);
				final var resource = mock(Resource.class);
				final var resourceId = UUID.randomUUID();
				final var resourceStateUpdationRequest = mock(ResourceStateUpdationRequest.class);

				when(resourceStateUpdationRequest.getId()).thenReturn(resourceId);
				when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));
				when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(resource));
				when(user.getId()).thenReturn(UUID.randomUUID());
				when(resource.getUserId()).thenReturn(UUID.randomUUID());

				final var response = resourceService.update(resourceStateUpdationRequest, EMAIL_ID);

				assertNotNull(response.getBody());
				assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCodeValue());
				verify(resourceRepository, times(0)).save(resource);
			}

		}

	}
}
