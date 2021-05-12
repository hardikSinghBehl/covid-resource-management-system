package com.hardik.pomfrey.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class CommentDto {

	private final UUID id;
	private final String emailId;
	private final String fullName;
	private final String text;
	private final Boolean isActive;
	private final LocalDateTime createdAt;

}
