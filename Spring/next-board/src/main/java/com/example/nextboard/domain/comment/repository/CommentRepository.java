package com.example.nextboard.domain.comment.repository;

import com.example.nextboard.domain.comment.entity.Comment;
import com.example.nextboard.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = "post")
    Page<Comment> findByPostOrderByIdDesc(Post post, Pageable pageable);
}
