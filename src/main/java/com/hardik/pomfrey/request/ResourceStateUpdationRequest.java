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
public class ResourceStateUpdationRequest {

	@Schema(description = "Request-Id", example = "4285f896-3afa-4763-8373-ea6279a1aa2e", required = true)
	@NotBlank
	private final UUID id;

}
