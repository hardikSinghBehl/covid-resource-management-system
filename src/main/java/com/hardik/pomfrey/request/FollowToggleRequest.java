package com.hardik.pomfrey.request;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class FollowToggleRequest {

	@Schema(description = "Id of user to be followed", example = "4fd779b9-d9c6-4dda-9485-a2d54fdbca47", required = true)
	@NotBlank
	private final UUID userId;

}
