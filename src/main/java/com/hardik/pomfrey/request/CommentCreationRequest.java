package com.hardik.pomfrey.request;

import java.util.UUID;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class CommentCreationRequest {

	@Schema(description = "Comment Text", example = "Eat Shit and Die", required = true)
	@Max(value = 200)
	@NotBlank
	private String text;

	@Schema(description = "Item/Context Type", example = "resource", required = true)
	@NotBlank
	private String itemType;

	@Schema(description = "Item/Context Id", example = "9bf77410-abd2-44de-9f74-bb91a1edc534", required = true)
	@NotBlank
	private UUID itemId;

}
