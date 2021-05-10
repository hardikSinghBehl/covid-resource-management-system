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
public class UserPasswordUpdationRequest {

	@Schema(description = "Old Password", example = "User@123", required = true)
	@Max(value = 15)
	@NotBlank
	private final String oldPassword;

	@Schema(description = "New Password", example = "User@1234", required = true)
	@Max(value = 15)
	@NotBlank
	private final String newPassword;

}
