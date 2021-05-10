package com.hardik.pomfrey.service;

import org.springframework.stereotype.Service;

import com.hardik.pomfrey.repository.MasterResourceTypeRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MasterResourceTypeService {

	private final MasterResourceTypeRepository masterResourceTypeRepository;

}
