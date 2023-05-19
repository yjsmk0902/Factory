package com.example.nextboard.domain.post.repository;

import com.example.nextboard.domain.member.entity.Member;
import com.example.nextboard.domain.post.entity.Category;
import com.example.nextboard.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByMemberOrderByIdDesc(Member member, Pageable pageable);

    Page<Post> findByCategoryOrderByIdDesc(Category category, Pageable pageable);

    Page<Post> findByTitleContaining(String keyword, Pageable pageable);

    Page<Post> findByMember_NameContaining(String keyword, Pageable pageable);

}
