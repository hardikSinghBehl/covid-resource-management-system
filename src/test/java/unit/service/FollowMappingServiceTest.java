package unit.service;

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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hardik.pomfrey.entity.FollowMapping;
import com.hardik.pomfrey.entity.User;
import com.hardik.pomfrey.repository.FollowMappingRepository;
import com.hardik.pomfrey.repository.UserRepository;
import com.hardik.pomfrey.request.FollowToggleRequest;
import com.hardik.pomfrey.service.FollowMappingService;
import com.hardik.pomfrey.utility.ResponseEntityUtils;

@DisplayName("Follow Service Is Called")
public class FollowMappingServiceTest {

	private static final String EMAIL_ID = "hardik.behl7444@gmail.com";

	private FollowMappingService followMappingService;

	private FollowMappingRepository followMappingRepository;

	private UserRepository userRepository;

	private ResponseEntityUtils responseEntityUtils;

	@BeforeEach
	void setUp() {
		followMappingRepository = mock(FollowMappingRepository.class);
		userRepository = mock(UserRepository.class);
		responseEntityUtils = spy(ResponseEntityUtils.class);
		followMappingService = new FollowMappingService(followMappingRepository, userRepository, responseEntityUtils);
	}

	@Test
	@DisplayName("Giving Wrong User Email-id")
	void toggle_whenWrongEmailIsProvided_throwsException() {
		when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> followMappingService.toggle(null, EMAIL_ID));
	}

	@Test
	@DisplayName("When Logged-in User Follows Other User Initially")
	void toggle_whenUserIsFollowingAnotherInitially_followMappingCreatedSuccessfullyInDatabase() {
		final var followToggleRequest = mock(FollowToggleRequest.class);
		final var followMapping = mock(FollowMapping.class);
		final var user = mock(User.class);
		final var loggedInUserId = UUID.randomUUID();
		final var userId = UUID.randomUUID();
		when(user.getId()).thenReturn(loggedInUserId);
		when(followToggleRequest.getUserId()).thenReturn(userId);
		when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));
		when(followMappingRepository.findByFollowerUserIdAndFollowedUserId(loggedInUserId, userId))
				.thenReturn(Optional.empty());
		when(followMappingRepository.save(Mockito.any())).thenReturn(followMapping);

		followMappingService.toggle(followToggleRequest, EMAIL_ID);

		verify(user, times(2)).getId();
		verify(followToggleRequest, times(2)).getUserId();
		verify(followMappingRepository).save(Mockito.any());
	}

	@Test
	@DisplayName("When Logged-in User Unfollows A Followed User")
	void toggle_whenLoggedInUserUnfollowsUser_updateFollowMappingInDatabase() {
		final var followToggleRequest = mock(FollowToggleRequest.class);
		final var followMapping = mock(FollowMapping.class);
		final var user = mock(User.class);
		final var loggedInUserId = UUID.randomUUID();
		final var userId = UUID.randomUUID();
		when(user.getId()).thenReturn(loggedInUserId);
		when(followToggleRequest.getUserId()).thenReturn(userId);
		when(followMapping.getIsActive()).thenReturn(true);
		when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));
		when(followMappingRepository.findByFollowerUserIdAndFollowedUserId(loggedInUserId, userId))
				.thenReturn(Optional.of(followMapping));
		when(followMappingRepository.save(Mockito.any())).thenReturn(followMapping);

		followMappingService.toggle(followToggleRequest, EMAIL_ID);

		verify(followMapping).setIsActive(false);
		verify(followMappingRepository).save(followMapping);
	}

	@Test
	@DisplayName("When Logged-in User Re-follows A Past Followed User")
	void toggle_whenLoggedInUserReFollowsUser_updateFollowMappingInDatabase() {
		final var followToggleRequest = mock(FollowToggleRequest.class);
		final var followMapping = mock(FollowMapping.class);
		final var user = mock(User.class);
		final var loggedInUserId = UUID.randomUUID();
		final var userId = UUID.randomUUID();
		when(user.getId()).thenReturn(loggedInUserId);
		when(followToggleRequest.getUserId()).thenReturn(userId);
		when(followMapping.getIsActive()).thenReturn(false);
		when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));
		when(followMappingRepository.findByFollowerUserIdAndFollowedUserId(loggedInUserId, userId))
				.thenReturn(Optional.of(followMapping));
		when(followMappingRepository.save(Mockito.any())).thenReturn(followMapping);

		followMappingService.toggle(followToggleRequest, EMAIL_ID);

		verify(followMapping).setIsActive(true);
		verify(followMappingRepository).save(followMapping);
	}

}
