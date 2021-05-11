package com.hardik.pomfrey.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hardik.pomfrey.dto.CommentDto;
import com.hardik.pomfrey.entity.Comment;
import com.hardik.pomfrey.repository.CommentRepository;
import com.hardik.pomfrey.repository.UserRepository;
import com.hardik.pomfrey.request.CommentCreationRequest;
import com.hardik.pomfrey.utility.ResponseEntityUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;

	private final UserRepository userRepository;

	private final ResponseEntityUtils responseEntityUtils;

	public ResponseEntity<?> create(final CommentCreationRequest commentCreationRequest, final String emailId) {
		final var comment = new Comment();
		final var user = userRepository.findByEmailId(emailId).get();

		comment.setItemType(commentCreationRequest.getItemType());
		comment.setItemId(commentCreationRequest.getItemId());
		comment.setText(commentCreationRequest.getText());
		comment.setUserId(user.getId());

		commentRepository.save(comment);

		return responseEntityUtils.generateCommentCreationResponse();
	}

	public ResponseEntity<?> delete(final UUID commentId, final String emailId) {
		final var comment = commentRepository.findById(commentId).get();
		final var user = userRepository.findByEmailId(emailId).get();

		if (comment.getUserId() != user.getId())
			return responseEntityUtils.generateUnauthorizedResponse();

		commentRepository.deleteById(comment.getId());
		return responseEntityUtils.generateCommentDeletionResponse();
	}

	public ResponseEntity<List<CommentDto>> retreive(UUID contextId) {
		return ResponseEntity.ok(commentRepository.findByItemId(contextId).parallelStream().map(comment -> {
			final var user = comment.getUser();
			return CommentDto.builder().createdAt(comment.getCreatedAt()).emailId(user.getEmailId())
					.fullName(user.getFirstName() + " " + user.getLastName()).id(comment.getId())
					.text(comment.getText()).build();
		}).sorted((a, b) -> a.getCreatedAt().compareTo(b.getCreatedAt())).collect(Collectors.toList()));
	}

}
