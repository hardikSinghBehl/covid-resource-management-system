package com.hardik.pomfrey.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.pomfrey.entity.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, UUID> {

}
