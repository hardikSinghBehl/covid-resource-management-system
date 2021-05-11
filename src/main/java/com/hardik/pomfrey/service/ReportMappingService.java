package com.hardik.pomfrey.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hardik.pomfrey.entity.ReportMapping;
import com.hardik.pomfrey.repository.ReportMappingRepository;
import com.hardik.pomfrey.repository.UserRepository;
import com.hardik.pomfrey.request.ReportCreationRequest;
import com.hardik.pomfrey.utility.ResponseEntityUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReportMappingService {

	private final ReportMappingRepository reportMappingRepository;

	private final UserRepository userRepository;

	private final ResponseEntityUtils responseEntityUtils;

	public ResponseEntity<?> create(final ReportCreationRequest reportCreationRequest, final String emailId) {
		final var user = userRepository.findByEmailId(emailId).get();
		final var report = new ReportMapping();
		report.setItemType(reportCreationRequest.getItemType());
		report.setItemId(reportCreationRequest.getItemId());
		report.setUserId(user.getId());

		reportMappingRepository.save(report);

		return responseEntityUtils.generateReportCreationResponse();
	}

}
