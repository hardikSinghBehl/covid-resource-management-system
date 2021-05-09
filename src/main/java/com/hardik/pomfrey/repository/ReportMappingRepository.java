package com.hardik.pomfrey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.pomfrey.entity.ReportMapping;

@Repository
public interface ReportMappingRepository extends JpaRepository<ReportMapping, Integer> {

}
