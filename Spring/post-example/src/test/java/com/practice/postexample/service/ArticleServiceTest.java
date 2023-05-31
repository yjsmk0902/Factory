package com.practice.postexample.service;

import com.practice.postexample.domain.Article;
import com.practice.postexample.domain.ArticleComment;
import com.practice.postexample.domain.UserAccount;
import com.practice.postexample.domain.type.SearchType;
import com.practice.postexample.dto.ArticleCommentDto;
import com.practice.postexample.dto.ArticleDto;
import com.practice.postexample.dto.UserAccountDto;
import com.practice.postexample.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@DisplayName("[Service] - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks
    private ArticleService sut;

    @Mock
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("[Service] - 게시글 검색으로 조회 :Success" +
            "(Param: SearchType, String)" +
            "(Return: List<ArticleDto>)")
    void searchArticlesTest() {
        //given
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findAll(pageable)).willReturn(Page.empty());

        //when
        Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword");   //제목, 본문, ID, 닉네임, 해시태그

        //then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findAll(pageable);
    }

    @Test
    @DisplayName("[Service] - 검색어로 게시글 검색 :Success" +
            "(Param: Long)" +
            "(Return: ArticleDto)")
    void searchArticleWithTitleTest() {
        //given
        SearchType searchType = SearchType.TITLE;
        String searchKeyword = "title";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByTitle(searchKeyword, pageable)).willReturn(Page.empty());

        //when
        Page<ArticleDto> articles = sut.searchArticles(searchType, searchKeyword, pageable);

        //then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findByTitle(searchKeyword, pageable);

    }

    @Test
    @DisplayName("[Service] - 단일 게시글 ID로 조회 " +
            "(Param: Long)" +
            "(Return: ArticleResponseDto)")
    void searchArticleTest() {
        //given
        Long articleId = 1L;
        Article article = createArticle();
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

        //when
        ArticleDto result = sut.getArticle(articleId);

        //then
        assertThat(result)
                .hasFieldOrPropertyWithValue("title", article.getTitle())
                .hasFieldOrPropertyWithValue("content", article.getContent())
                .hasFieldOrPropertyWithValue("hashtag", article.getHashtag());
        then(articleRepository).should().findById(articleId);
    }

    @Test
    @DisplayName("[Service] - 게시글 생성 " +
            "(Param: ArticleCreateRequestDto)" +
            "(Return: void)")
    void createArticleTest() {
        //given
        ArticleDto request = createArticleDto();
        given(articleRepository.save(any(Article.class))).willReturn(createArticle());

        //when
        sut.saveArticle(request);

        //then
        then(articleRepository).should().save(any(Article.class));
    }

    @Test
    @DisplayName("[Service] - 게시글 수정 " +
            "(Param: Long, ArticleUpdateRequestDto)" +
            "(Return: void)")
    void updateArticleTest() {
        //given
        Article article = createArticle();
        ArticleDto request = createArticleDto("새 타이틀", "새 내용", "#새 해시태그");
        given(articleRepository.getReferenceById(request.id())).willReturn(article);

        //when
        sut.updateArticle(request);

        //then
        assertThat(article)
                .hasFieldOrPropertyWithValue("title", request.title())
                .hasFieldOrPropertyWithValue("content", request.content())
                .hasFieldOrPropertyWithValue("hashtag", request.hashtag());
        then(articleRepository).should().getReferenceById(request.id());
    }

    @Test
    @DisplayName("[Service] - 게시글 삭제 " +
            "(Param: Long)" +
            "(Return: void)")
    void deleteArticleTest() {
        //given
        Long articleId = 1L;
        willDoNothing().given(articleRepository).deleteById(articleId);

        //when
        sut.deleteArticle(articleId);

        //then
        then(articleRepository).should().deleteById(articleId);
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                1L,
                "userId",
                "userPassword",
                "email",
                "nickname",
                "memo",
                LocalDateTime.now(),
                "luke",
                LocalDateTime.now(),
                "luke"
        );
    }

    private ArticleDto createArticleDto() {
        return ArticleDto.of(
                1L,
                createUserAccountDto(),
                "title",
                "content",
                "hashtag",
                LocalDateTime.now(),
                "luke",
                LocalDateTime.now(),
                "luke"
        );
    }

    private ArticleDto createArticleDto(String title, String content, String hashtag) {
        return ArticleDto.of(
                1L,
                createUserAccountDto(),
                title,
                content,
                hashtag,
                LocalDateTime.now(),
                "luke",
                LocalDateTime.now(),
                "luke"
        );
    }

    private ArticleCommentDto createArticleCommentDto() {
        return ArticleCommentDto.of(
                1L,
                createArticleDto(),
                createUserAccountDto(),
                "content",
                LocalDateTime.now(),
                "luke",
                LocalDateTime.now(),
                "luke"
        );
    }

    private ArticleComment createArticleComment(String content) {
        return ArticleComment.builder()
                .article(createArticle())
                .userAccount(createUserAccount())
                .content(content)
                .build();
    }

    private Article createArticle() {
        return Article.builder()
                .title("title")
                .content("content")
                .hashtag("hashtag")
                .build();
    }

    private UserAccount createUserAccount() {
        return UserAccount.builder()
                .userId("userId")
                .userPassword("qwert1234")
                .email("email@mail.com")
                .nickname("luke")
                .memo("memo")
                .build();
    }
}