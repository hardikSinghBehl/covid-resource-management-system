package com.hardik.pomfrey.service;

import org.springframework.stereotype.Service;

import com.hardik.pomfrey.repository.RequestRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RequestService {

	private final RequestRepository requestRepository;

}
