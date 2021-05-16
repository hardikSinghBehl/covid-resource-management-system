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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import com.hardik.pomfrey.constants.ItemType;
import com.hardik.pomfrey.entity.Comment;
import com.hardik.pomfrey.entity.User;
import com.hardik.pomfrey.repository.CommentRepository;
import com.hardik.pomfrey.repository.ReportMappingRepository;
import com.hardik.pomfrey.repository.UserRepository;
import com.hardik.pomfrey.request.CommentCreationRequest;
import com.hardik.pomfrey.service.CommentService;
import com.hardik.pomfrey.utility.ResponseEntityUtils;

class CommentServiceTest {

	private static final String COMMENT_TEXT = "Reach++";
	private static final String EMAIL_ID = "hardik.behl7444@gmail.com";
	private CommentService commentService;
	private CommentRepository commentRepository;
	private UserRepository userRepository;
	private ResponseEntityUtils responseEntityUtils;
	private ReportMappingRepository reportMappingRepository;

	@BeforeEach
	void setUp() {
		commentRepository = mock(CommentRepository.class);
		userRepository = mock(UserRepository.class);
		responseEntityUtils = spy(ResponseEntityUtils.class);
		reportMappingRepository = mock(ReportMappingRepository.class);
		commentService = new CommentService(commentRepository, userRepository, responseEntityUtils,
				reportMappingRepository);
	}

	@Nested
	@DisplayName("Comment Creation Service Is Called")
	class CommentCreationTest {

		@Test
		@DisplayName("Giving Correct Email-Id And Request Body")
		void create_whenCorrectEmailIdIsProvided_commentCreatedInDatabase() {
			final var commentCreationRequest = mock(CommentCreationRequest.class);
			final var user = mock(User.class);
			final var userId = UUID.randomUUID();
			final var requestId = UUID.randomUUID();

			when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));
			when(user.getId()).thenReturn(userId);
			when(commentCreationRequest.getText()).thenReturn(COMMENT_TEXT);
			when(commentCreationRequest.getItemId()).thenReturn(requestId);
			when(commentCreationRequest.getItemType()).thenReturn(ItemType.REQUEST);
			when(commentRepository.save(Mockito.any())).thenReturn(new Comment());

			final var response = commentService.create(commentCreationRequest, EMAIL_ID);

			assertNotNull(response.getBody());
			assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
			verify(commentCreationRequest).getText();
			verify(commentCreationRequest).getItemId();
			verify(commentCreationRequest).getItemType();
			verify(commentRepository).save(Mockito.any(Comment.class));
		}

		@Test
		@DisplayName("Giving Incorrect Email-id")
		void create_whenWrongEmailIdIsProvided_throwsError() {
			when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.empty());

			assertThrows(NoSuchElementException.class,
					() -> commentService.create(mock(CommentCreationRequest.class), EMAIL_ID));
		}

	}

	@Nested
	@DisplayName("Comment Deletion Service Is Called")
	class CommentDeletionTest {

		@Test
		@DisplayName("Giving Invalid Comment-id")
		void delete_whenWrongCommentIdIsProvided_throwsError() {
			final var wrongCommentId = UUID.randomUUID();
			when(commentRepository.findById(wrongCommentId)).thenReturn(Optional.empty());

			assertThrows(NoSuchElementException.class, () -> commentService.delete(wrongCommentId, EMAIL_ID));
			verify(commentRepository, times(1)).findById(wrongCommentId);
			verify(userRepository, times(0)).findByEmailId(Mockito.anyString());
		}

		@Test
		@DisplayName("Giving Invalid Email-id")
		void delete_whenWrongEmailIdIsProvided_throwsError() {
			final var commentId = UUID.randomUUID();
			when(commentRepository.findById(commentId)).thenReturn(Optional.of(new Comment()));
			when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.empty());

			assertThrows(NoSuchElementException.class, () -> commentService.delete(commentId, EMAIL_ID));
			verify(commentRepository, times(1)).findById(commentId);
			verify(userRepository, times(1)).findByEmailId(Mockito.anyString());
		}

		@Test
		@DisplayName("Giving Correct Input")
		void delete_whenCorrectInputsAreProvided_setCommentAsInactiveInDatabase() {
			final var commentId = UUID.randomUUID();
			final var userId = UUID.randomUUID();
			final var user = mock(User.class);
			final var comment = mock(Comment.class);

			when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
			when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));
			when(user.getId()).thenReturn(userId);
			when(comment.getUserId()).thenReturn(userId);
			when(commentRepository.save(comment)).thenReturn(comment);

			commentService.delete(commentId, EMAIL_ID);

			verify(comment).setActive(false);
		}

		@Test
		@DisplayName("When Other User's Credentials Are Given")
		void delete_whenUserOtherThanCommentWriterTriesToDelete_errorMessageShow() {
			final var commentId = UUID.randomUUID();
			final var userId = UUID.randomUUID();
			final var user = mock(User.class);
			final var comment = mock(Comment.class);
			when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
			when(userRepository.findByEmailId(EMAIL_ID)).thenReturn(Optional.of(user));
			when(user.getId()).thenReturn(userId);
			when(comment.getUserId()).thenReturn(UUID.randomUUID());

			final var response = commentService.delete(commentId, EMAIL_ID);

			assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCodeValue());
			verify(comment, times(0)).setActive(false);
		}
	}

}
