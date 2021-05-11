package com.hardik.pomfrey.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hardik.pomfrey.entity.MasterResourceType;
import com.hardik.pomfrey.repository.MasterResourceTypeRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MasterResourceTypeService {

	private final MasterResourceTypeRepository masterResourceTypeRepository;

	public ResponseEntity<List<MasterResourceType>> retreive() {
		return ResponseEntity.ok(masterResourceTypeRepository.findAll());
	}

}
