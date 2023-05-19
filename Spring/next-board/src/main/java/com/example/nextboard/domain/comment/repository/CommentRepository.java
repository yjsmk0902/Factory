package com.example.nextboard.domain.comment.repository;

import com.example.nextboard.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
