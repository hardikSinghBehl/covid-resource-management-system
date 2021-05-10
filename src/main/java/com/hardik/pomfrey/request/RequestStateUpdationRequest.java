package com.hardik.pomfrey.request;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class RequestStateUpdationRequest {

	@Schema(description = "Request Fulfilled", example = "true/false", required = true)
	@NotBlank
	private final Boolean fulfilled;

	@Schema(description = "Fulfilled By Email-Id", example = "hardik.behl7444@gmail.com", required = false)
	private final String fulfilledByEmailId;

}
