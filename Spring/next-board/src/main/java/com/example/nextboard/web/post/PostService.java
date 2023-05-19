package com.example.nextboard.web.post;

import com.example.nextboard.domain.post.dto.request.CreatePostRequestDto;
import com.example.nextboard.domain.member.entity.Member;
import com.example.nextboard.domain.post.dto.response.PostResponseDto;
import com.example.nextboard.domain.post.entity.Post;
import com.example.nextboard.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    //게시판 생성 로직
    @Transactional
    public void createPost(@RequestBody CreatePostRequestDto createPostRequestDto, Member member) {
        postRepository.save(createPostRequestDto.toEntity(member));
    }

    //게시판 삭제 로직
    @Transactional
    public void deletePost(Long postId) {
        postRepository.delete(findPost(postId));
    }

    //게시판 뷰 로직
    public PostResponseDto viewPost(Long postId) {
        return PostResponseDto.toDto(findPost(postId));
    }

    public Post findPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(RuntimeException::new);     //예외 처리 필요
    }
}
