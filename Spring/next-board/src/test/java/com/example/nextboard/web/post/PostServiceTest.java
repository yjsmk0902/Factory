package com.example.nextboard.web.post;

import com.example.nextboard.domain.member.entity.Member;
import com.example.nextboard.domain.member.repository.MemberRepository;
import com.example.nextboard.domain.post.dto.request.CreatePostRequestDto;
import com.example.nextboard.domain.post.dto.response.CompactPostResponseDto;
import com.example.nextboard.domain.post.dto.response.PostResponseDto;
import com.example.nextboard.domain.post.entity.Category;
import com.example.nextboard.domain.post.entity.Post;
import com.example.nextboard.domain.post.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@DisplayName("게시판 서비스 테스트")
@Transactional
public class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("게시판 생성 로직 테스트")
    public void createPost() {
        //given
        CreatePostRequestDto request = new CreatePostRequestDto("게시판 제목", "게시판 내용", Category.NOTICE);
        Member member = memberRepository.findByName("테스트 사용자1").orElseThrow(RuntimeException::new);

        //when
        postService.createPost(request, member);

        //then
        List<Post> posts = postRepository.findAll();

        assertThat(posts).hasSize(2);
        assertThat(posts.get(1).getTitle()).isEqualTo("게시판 제목");
        assertThat(posts.get(1).getContent()).isEqualTo("게시판 내용");
        assertThat(posts.get(1).getCategory()).isEqualTo(Category.NOTICE);
        assertThat(posts.get(1).getMember().getName()).isEqualTo("테스트 사용자1");
    }

    @Test
    @DisplayName("게시판 삭제 로직 테스트")
    void deletePost() {
        //given
        Member member = memberRepository.findByName("테스트 사용자1").orElseThrow(RuntimeException::new);
        Post memberPost = member.getPosts().get(0);
        Long postId = memberPost.getId();

        //when
        postService.deletePost(postId);

        //then
        List<Post> results = postRepository.findAll();
    }

    @Test
    @DisplayName("게시판 뷰 로직 테스트")
    void viewPost() {
        //given
        Member member = memberRepository.findByName("테스트 사용자1").orElseThrow(RuntimeException::new);
        Post memberPost = member.getPosts().get(0);
        Long postId = memberPost.getId();

        //when
        PostResponseDto result = postService.viewPost(postId);

        //then
        CompactPostResponseDto resultCompact = result.getCompactPostResponseDto();
        assertThat(resultCompact.getTitle()).isEqualTo("게시판 제목1");
        assertThat(result.getContent()).isEqualTo("게시판 내용1");
        assertThat(resultCompact.getViewCount()).isEqualTo(40);
        assertThat(resultCompact.getRecommend()).isEqualTo(2);
        assertThat(resultCompact.getCategory()).isEqualTo("공지 게시판");
        assertThat(resultCompact.getDate()).isEqualTo("2023-05-19 (금) 오후 13:44");
    }
}
