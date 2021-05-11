package com.hardik.pomfrey.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.pomfrey.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

	List<Comment> findByItemId(UUID itemId);

}
