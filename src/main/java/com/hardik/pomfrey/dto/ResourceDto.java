package com.hardik.pomfrey.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class ResourceDto {

	private final UUID id;
	private final String resourceType;
	private final String title;
	private final String description;
	private final Integer count;
	private final String emailId;
	private final String fullName;
	private final Boolean isActive;
	private final Double latitude;
	private final Double longitude;

}
