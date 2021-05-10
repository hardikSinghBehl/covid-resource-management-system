package com.hardik.pomfrey.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class UserUpdationRequest {

	@Schema(description = "First Name (Given)", example = "Hardik", required = true)
	@Size(min = 3, max = 50)
	@NotBlank
	private final String firstName;

	@Schema(description = "Last Name (Family)", example = "Behl", required = true)
	@Size(min = 3, max = 50)
	@NotBlank
	private final String lastName;

	@Schema(description = "Contact Number", example = "9999155786", required = true)
	@NotBlank
	private final String contactNumber;

	@Schema(description = "Residing State Id", example = "1", required = true)
	@NotBlank
	private final Integer stateId;

	@Schema(description = "Latitude Value", example = "27.2046", required = true)
	@NotBlank
	private final Double latitude;

	@Schema(description = "Longitude Value", example = "27.2046", required = true)
	@NotBlank
	private final Double longitude;

}
