package unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import com.hardik.pomfrey.constants.ItemType;
import com.hardik.pomfrey.entity.User;
import com.hardik.pomfrey.repository.ReportMappingRepository;
import com.hardik.pomfrey.repository.UserRepository;
import com.hardik.pomfrey.request.ReportCreationRequest;
import com.hardik.pomfrey.service.ReportMappingService;
import com.hardik.pomfrey.utility.ResponseEntityUtils;

@DisplayName("When Service Is Called To Raise A New Report")
class ReportMappingServiceTest {

	private static final String EMAIL_ID = "hardik.behl7444@gmail.com";
	private ReportMappingService reportMappingService;
	private ReportMappingRepository reportMappingRepository;
	private UserRepository userRepository;
	private ResponseEntityUtils responseEntityUtils;

	@BeforeEach
	void setUp() {
		reportMappingRepository = mock(ReportMappingRepository.class);
		userRepository = mock(UserRepository.class);
		responseEntityUtils = spy(ResponseEntityUtils.class);
		reportMappingService = new ReportMappingService(reportMappingRepository, userRepository, responseEntityUtils);
	}

	@Test
	@DisplayName("Giving Correct Email-id And Request Input")
	void create_whenCorrectEmailIsProvided_reportIsRegisteredInDatabase() {
		final var reportCreationRequest = mock(ReportCreationRequest.class);
		final var user = mock(User.class);
		final var userId = UUID.randomUUID();
		final var requestId = UUID.randomUUID();
		when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));
		when(user.getId()).thenReturn(userId);
		when(reportCreationRequest.getItemType()).thenReturn(ItemType.REQUEST);
		when(reportCreationRequest.getItemId()).thenReturn(requestId);

		final var response = reportMappingService.create(reportCreationRequest, EMAIL_ID);

		assertNotNull(response);
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		verify(user).getId();
		verify(reportCreationRequest).getItemId();
		verify(reportCreationRequest).getItemType();
		verify(reportMappingRepository).save(Mockito.any());
	}

	@Test
	@DisplayName("Giving Wrong Email-id")
	void create_whenWrongEmailIdIsProvided_throwError() {
		when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> reportMappingService.create(null, EMAIL_ID));
	}

}
