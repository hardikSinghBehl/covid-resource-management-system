package com.hardik.pomfrey.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.pomfrey.entity.ReportMapping;

@Repository
public interface ReportMappingRepository extends JpaRepository<ReportMapping, Integer> {

	List<ReportMapping> findByItemTypeAndItemId(String itemType, UUID itemId);

}
