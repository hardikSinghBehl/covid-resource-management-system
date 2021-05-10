package com.hardik.pomfrey.service;

import org.springframework.stereotype.Service;

import com.hardik.pomfrey.repository.FollowMappingRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FollowMappingService {

	private final FollowMappingRepository followMappingRepository;

}
