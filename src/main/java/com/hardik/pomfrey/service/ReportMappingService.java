package com.hardik.pomfrey.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hardik.pomfrey.repository.ReportMappingRepository;
import com.hardik.pomfrey.request.ReportCreationRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReportMappingService {

	private final ReportMappingRepository reportMappingRepository;

	public ResponseEntity<?> create(final ReportCreationRequest reportCreationRequest, final String emailId) {
		return null;
	}

}
