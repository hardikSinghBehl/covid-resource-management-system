package com.hardik.pomfrey.service;

import org.springframework.stereotype.Service;

import com.hardik.pomfrey.repository.ReportMappingRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReportMappingService {

	private final ReportMappingRepository reportMappingRepository;

}
