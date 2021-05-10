package com.hardik.pomfrey.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hardik.pomfrey.request.ReportCreationRequest;
import com.hardik.pomfrey.service.ReportMappingService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/report")
public class ReportController {

	private final ReportMappingService reportMappingService;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Registers A New Report For Mentioned Context")
	public ResponseEntity<?> reportCreationHandler(
			@Valid @RequestBody(required = true) final ReportCreationRequest reportCreationRequest) {
		return reportMappingService.create(reportCreationRequest,
				SecurityContextHolder.getContext().getAuthentication().getName());
	}

}
