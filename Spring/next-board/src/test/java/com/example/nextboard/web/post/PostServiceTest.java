package com.example.nextboard.web.post;

import com.example.nextboard.domain.member.entity.Member;
import com.example.nextboard.domain.member.repository.MemberRepository;
import com.example.nextboard.domain.post.dto.request.CreatePostRequestDto;
import com.example.nextboard.domain.post.dto.request.ModifyPostRequestDto;
import com.example.nextboard.domain.post.dto.response.CompactPostResponseDto;
import com.example.nextboard.domain.post.dto.response.PostResponseDto;
import com.example.nextboard.domain.post.entity.Category;
import com.example.nextboard.domain.post.entity.Post;
import com.example.nextboard.domain.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @DisplayName("게시글 생성 로직 테스트")
    public void createPostTest() {
        //given
        CreatePostRequestDto request = new CreatePostRequestDto("게시판 제목", "게시판 내용", Category.NOTICE);
        Member member = memberRepository.findByName("테스트 사용자2").orElseThrow(RuntimeException::new);

        //when
        postService.createPost(request, member);

        //then
        List<Post> posts = postRepository.findByTitleContaining("게시판");

        assertThat(posts).hasSize(1);
        assertThat(posts.get(0).getTitle()).isEqualTo("게시판 제목");
        assertThat(posts.get(0).getContent()).isEqualTo("게시판 내용");
        assertThat(posts.get(0).getCategory()).isEqualTo(Category.NOTICE);
        assertThat(posts.get(0).getMember().getName()).isEqualTo("테스트 사용자2");
    }

    @Test
    @DisplayName("게시글 삭제 로직 테스트")
    void deletePostTest() {
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
    @DisplayName("게시글 수정 로직 테스트")
    void modifyPostTest(){
        //given
        ModifyPostRequestDto modifyPostRequestDto = ModifyPostRequestDto.builder()
                .title("수정된 게시글 제목")
                .content("수정된 게시글 본문")
                .category(Category.QUESTION)
                .build();
        Member member = memberRepository.findByName("테스트 사용자1").orElseThrow(RuntimeException::new);
        Post memberPost = member.getPosts().get(0);
        Long postId = memberPost.getId();

        //when
        postService.modifyPost(modifyPostRequestDto, postId);

        //then
        Post result = postRepository.findById(postId).orElseThrow(RuntimeException::new);
        assertThat(result.getTitle()).isEqualTo("수정된 게시글 제목");
        assertThat(result.getContent()).isEqualTo("수정된 게시글 본문");
        assertThat(result.getCategory()).isEqualTo(Category.QUESTION);
    }
    
    @Test
    @DisplayName("게시글 검색 로직 테스트 (페이징) (제목으로 검색)")
    void getSearchTitleTest(){
        //given
        PageRequest pageable = PageRequest.of(0, 5);
        
        //when
        Page<CompactPostResponseDto> results = postService.getSearchTitlePage("게시글", pageable);

        //then
        assertThat(results.getSize()).isEqualTo(5);
        assertThat(results.hasNext()).isTrue();
    }

    @Test
    @DisplayName("게시글 상세 뷰 로직 테스트")
    void viewPostTest() {
        //given
        Member member = memberRepository.findByName("테스트 사용자1").orElseThrow(RuntimeException::new);
        Post memberPost = member.getPosts().get(0);
        Long postId = memberPost.getId();

        //when
        PostResponseDto result = postService.viewPost(postId);

        //then
        CompactPostResponseDto resultCompact = result.getCompactPostResponseDto();
        assertThat(resultCompact.getTitle()).isEqualTo("게시글 제목0");
        assertThat(result.getContent()).isEqualTo("게시글 내용0");
        assertThat(resultCompact.getViewCount()).isEqualTo(40);
        assertThat(resultCompact.getRecommend()).isEqualTo(5);
        assertThat(resultCompact.getCategory()).isEqualTo("공지 게시판");
        assertThat(resultCompact.getDate()).isEqualTo("2023-05-20 (토) 오후 20:06");
    }

}
