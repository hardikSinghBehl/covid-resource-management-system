package com.hardik.pomfrey.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.pomfrey.entity.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, UUID> {

}
