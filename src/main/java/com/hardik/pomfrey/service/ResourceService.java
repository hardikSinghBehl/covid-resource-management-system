package com.hardik.pomfrey.service;

import org.springframework.stereotype.Service;

import com.hardik.pomfrey.repository.ResourceRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ResourceService {

	private final ResourceRepository resourceRepository;

}
