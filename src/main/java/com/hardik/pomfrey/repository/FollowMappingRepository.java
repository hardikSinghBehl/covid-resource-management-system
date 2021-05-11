package com.hardik.pomfrey.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.pomfrey.entity.FollowMapping;

@Repository
public interface FollowMappingRepository extends JpaRepository<FollowMapping, Integer> {

	Optional<FollowMapping> findByFollowerUserIdAndFollowedUserId(UUID followerUserId, UUID followedUserId);

}
