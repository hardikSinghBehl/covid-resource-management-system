package com.hardik.pomfrey.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hardik.pomfrey.entity.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, UUID> {

	List<Request> findByFulfilledByUserId(UUID fulfilledByUserId);

	@Query(nativeQuery = true, value = "SELECT *, SQRT(\n" + "    POW(69.1 * (latitude - ?1), 2) +\n"
			+ "    POW(69.1 * (?2 - longitude) * COS(latitude / 57.3), 2)) AS distance\n"
			+ "FROM requests ORDER BY distance DESC;")
	List<Request> findNearestRequests(Double latitude, Double longitude, Pageable pageable);
}
