package com.hardik.pomfrey.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class ResourceCreationRequest {

	@Schema(description = "Resource Type Id", example = "1", required = true)
	@NotBlank
	private final Integer resourceTypeId;

	@Schema(description = "Title/Heading Of Request", required = true)
	@Max(value = 100)
	@NotBlank
	private final String title;

	@Schema(description = "Description Of Request", required = true)
	@Max(value = 1000)
	@NotBlank
	private final String description;

	@Schema(description = "Resource-Count", example = "69", required = true)
	@NotBlank
	private final Integer count;

	@Schema(description = "Latitude Value", example = "27.2046", required = true)
	@NotBlank
	private final Double latitude;

	@Schema(description = "Longitude Value", example = "27.2046", required = true)
	@NotBlank
	private final Double longitude;

}
