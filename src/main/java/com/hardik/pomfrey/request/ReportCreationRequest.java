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
public class ReportCreationRequest {

	@Schema(description = "Item Type", example = "request", required = true)
	@NotBlank
	private final String itemType;

	@Schema(description = "Item Id", example = "4fd779b9-d9c6-4dda-9485-a2d54fdbca47", required = true)
	@NotBlank
	private final UUID itemId;

}
