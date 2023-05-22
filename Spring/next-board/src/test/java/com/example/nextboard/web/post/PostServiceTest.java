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

    private final static String TEST_USER1 = "테스트 사용자1";
    private final static String TEST_USER2 = "테스트 사용자2";
    private final static String TEST_USER3 = "테스트 사용자3";

    @Test
    @DisplayName("ID로 게시글 검색 테스트(기본 기능)")
    void findPostTest() {
        //given
        List<Post> postAll = postRepository.findAll();
        Post post = postAll.get(0);
        Long postId = post.getId();

        //when
        Post resultPost = postService.findPost(postId);

        //then
        assertThat(resultPost).isEqualTo(post);
    }

    @Test
    @DisplayName("게시글 생성 로직 테스트")
    public void createPostTest() {
        //given
        CreatePostRequestDto request = new CreatePostRequestDto("생성된 게시판 제목", "생성된 게시판 내용", Category.NOTICE);
        Member member = memberRepository.findByName(TEST_USER3).orElseThrow(RuntimeException::new);

        //when
        System.out.println("쿼리 시작");
        postService.createPost(request, member);

        //then
        List<Post> posts = postRepository.findByTitleContaining("생성된");

        assertThat(posts).hasSize(1);
        assertThat(posts.get(0).getTitle()).isEqualTo("생성된 게시판 제목");
        assertThat(posts.get(0).getContent()).isEqualTo("생성된 게시판 내용");
        assertThat(posts.get(0).getCategory()).isEqualTo(Category.NOTICE);
        assertThat(posts.get(0).getMember().getName()).isEqualTo(TEST_USER3);
    }

    @Test
    @DisplayName("게시글 삭제 로직 테스트")
    void deletePostTest() {
        //given
        Member member = memberRepository.findByName(TEST_USER3).orElseThrow(RuntimeException::new);
        Post post = Post.builder()
                .member(member)
                .title("삭제 로직 테스트")
                .content("삭제 로직 테스트 내용")
                .build();
        postRepository.save(post);

        //when
        postService.deletePost(post.getId());

        //then
        List<Post> results = postRepository.findByTitleContaining("삭제");
        assertThat(results).isEmpty();
    }

    @Test
    @DisplayName("게시글 수정 로직 테스트")
    void modifyPostTest() {
        //given
        ModifyPostRequestDto modifyPostRequestDto = ModifyPostRequestDto.builder()
                .title("수정된 게시글 제목")
                .content("수정된 게시글 본문")
                .category(Category.QUESTION)
                .build();
        Member member = memberRepository.findByName(TEST_USER1).orElseThrow(RuntimeException::new);
        Post memberPost = member.getPosts().get(0);
        Long postId = memberPost.getId();

        //when
        System.out.println("쿼리 시작");
        postService.modifyPost(modifyPostRequestDto, postId);

        //then
        Post result = postRepository.findById(postId).orElseThrow(RuntimeException::new);
        assertThat(result.getTitle()).isEqualTo("수정된 게시글 제목");
        assertThat(result.getContent()).isEqualTo("수정된 게시글 본문");
        assertThat(result.getCategory()).isEqualTo(Category.QUESTION);
    }

    @Test
    @DisplayName("게시글 검색 로직 테스트 (페이징) (제목으로 검색)")
    void getSearchTitleTest() {
        //given
        PageRequest pageable = PageRequest.of(0, 5);

        //when
        Page<CompactPostResponseDto> results = postService.getSearchTitlePage("게시글", pageable);

        //then
        assertThat(results.getSize()).isEqualTo(5);
        assertThat(results.hasNext()).isTrue();
    }

    @Test
    @DisplayName("게시글 검색 로직 테스트 (페이징) (작성자로 검색)")
    void getSearchMemberTest(){
        //given
        PageRequest pageable = PageRequest.of(0, 5);

        //when
        Page<CompactPostResponseDto> results = postService.getSearchMemberPage("사용자1", pageable);

        //then
        assertThat(results.getSize()).isEqualTo(5);
        assertThat(results.hasNext()).isTrue();
    }

    @Test
    @DisplayName("내가 쓴 게시글 뷰 로직 (페이징)")
    void getMyPostTest(){
        //given
        PageRequest pageable = PageRequest.of(0, 5);
        Member member = memberRepository.findByName(TEST_USER1).orElseThrow(RuntimeException::new);

        //when
        Page<CompactPostResponseDto> results = postService.getMyPostPage(member, pageable);

        //then
        assertThat(results.getSize()).isEqualTo(5);
        assertThat(results.hasNext()).isTrue();
    }

    @Test
    @DisplayName("카테고리별 게시글 뷰 로직 (페이징)")
    void getCategoryPostTest(){
        //given
        PageRequest pageable = PageRequest.of(0, 5);

        //when
        Page<CompactPostResponseDto> results = postService.getCategoryPostPage(Category.NOTICE, pageable);

        //then
        assertThat(results.getSize()).isEqualTo(5);
        assertThat(results.hasNext()).isTrue();
    }

    @Test
    @DisplayName("게시글 상세 뷰 로직 테스트")
    void viewPostTest() {
        //given
        Member member = memberRepository.findByName(TEST_USER1).orElseThrow(RuntimeException::new);
        Post memberPost = member.getPosts().get(0);
        Long postId = memberPost.getId();

        //when
        System.out.println("쿼리 시작");
        PostResponseDto result = postService.viewPost(postId);
        System.out.println("쿼리 종료");

        //then
        CompactPostResponseDto resultCompact = result.getCompactPostResponseDto();
        assertThat(resultCompact.getTitle()).isEqualTo("게시글 제목1");
        assertThat(result.getContent()).isEqualTo("게시글 내용1");
        assertThat(resultCompact.getViewCount()).isEqualTo(41);
        assertThat(resultCompact.getRecommend()).isEqualTo(6);
        assertThat(resultCompact.getCategory()).isEqualTo("공지 게시판");
        assertThat(resultCompact.getDate()).isEqualTo("2023-05-20 (토) 오후 20:07");
    }
}
