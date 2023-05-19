package com.example.nextboard.domain.post.repository;

import com.example.nextboard.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
