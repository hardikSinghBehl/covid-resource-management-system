package com.hardik.pomfrey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.pomfrey.entity.MasterResourceType;

@Repository
public interface MasterResourceTypeRepository extends JpaRepository<MasterResourceType, Integer> {

}
