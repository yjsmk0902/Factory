package com.example.nextboard.web.post;

import com.example.nextboard.domain.post.dto.request.CreatePostRequestDto;
import com.example.nextboard.domain.member.entity.Member;
import com.example.nextboard.domain.post.dto.request.ModifyPostRequestDto;
import com.example.nextboard.domain.post.dto.response.CompactPostResponseDto;
import com.example.nextboard.domain.post.dto.response.PostResponseDto;
import com.example.nextboard.domain.post.entity.Category;
import com.example.nextboard.domain.post.entity.Post;
import com.example.nextboard.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    //게시글 생성 로직 - Test Complete
    @Transactional
    public void createPost(@RequestBody CreatePostRequestDto createPostRequestDto, Member member) {
        postRepository.save(createPostRequestDto.toEntity(member));
    }

    //게시글 삭제 로직 - Test Complete
    @Transactional
    public void deletePost(Long postId) {
        postRepository.delete(findPost(postId));
    }

    //게시글 수정 로직 - Test Complete
    @Transactional
    public void modifyPost(@RequestBody ModifyPostRequestDto modifyPostRequestDto, Long postId) {
        findPost(postId).modifyPost(
                modifyPostRequestDto.getTitle(),
                modifyPostRequestDto.getContent(),
                modifyPostRequestDto.getCategory()
        );
    }

    //게시글 검색 로직 (페이징) (제목으로 검색) - Test Complete
    public Page<CompactPostResponseDto> getSearchTitlePage(String keyword, Pageable pageable) {
        return postRepository.findByTitleContaining(keyword, pageable)
                .map(CompactPostResponseDto::toDto);
    }

    //게시글 검색 로직 (페이징) (작성자로 검색) - Test Complete
    public Page<CompactPostResponseDto> getSearchMemberPage(String keyword, Pageable pageable) {
        return postRepository.findByMember_NameContaining(keyword, pageable)
                .map(CompactPostResponseDto::toDto);
    }

    //내가 쓴 게시글 뷰 로직 (페이징) - Test Complete
    public Page<CompactPostResponseDto> getMyPostPage(Member member, Pageable pageable) {
        return postRepository.findByMemberOrderByIdDesc(member, pageable)
                .map(CompactPostResponseDto::toDto);
    }

    //카테고리별 게시글 뷰 로직 (페이징) - Test Complete
    public Page<CompactPostResponseDto> getCategoryPostPage(Category category, Pageable pageable) {
        return postRepository.findByCategoryOrderByIdDesc(category, pageable)
                .map(CompactPostResponseDto::toDto);
    }

    //게시글 상세 뷰 로직 - Test Complete
    public PostResponseDto viewPost(Long postId) {
        return PostResponseDto.toDto(findPost(postId));
    }

    //Id로 게시글 찾기(기본 기능) - Test Complete
    public Post findPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(RuntimeException::new);     //예외 처리 필요
    }

}
