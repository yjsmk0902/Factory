package com.practice.postexample.service;

import com.practice.postexample.domain.Article;
import com.practice.postexample.domain.UserAccount;
import com.practice.postexample.domain.type.SearchType;
import com.practice.postexample.dto.article.ArticleWithArticleCommentsDto;
import com.practice.postexample.dto.article.ArticleDto;
import com.practice.postexample.dto.userAccount.UserAccountDto;
import com.practice.postexample.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@TestMethodOrder(value = MethodOrderer.DisplayName.class)
@DisplayName("[Service] - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks
    private ArticleService sut;

    @Mock
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("[Service] - 게시글 검색으로 조회 " +
            "(Param: SearchType, String, Page)" +
            "(Return: List<ArticleDto>)")
    void searchArticlesTest() {
        //given
        SearchType searchType = SearchType.TITLE;
        String keyword = "title";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByTitleContaining(keyword, pageable)).willReturn(Page.empty());

        //when
        Page<ArticleDto> articles = sut.searchArticles(searchType, keyword, pageable);   //제목, 본문, ID, 닉네임, 해시태그

        //then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findByTitleContaining(keyword, pageable);
    }

    @Test
    @DisplayName("[Service] - 게시글 검색으로 조회 : 검색어가 없는 경우" +
            "(Param: SearchType, String)" +
            "(Return: List<ArticleDto>)")
    void searchArticles_NoKeywordTest() {
        //given
        SearchType searchType = SearchType.TITLE;
        String keyword = "";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findAll(pageable)).willReturn(Page.empty());

        //when
        Page<ArticleDto> articles = sut.searchArticles(searchType, keyword, pageable);   //제목, 본문, ID, 닉네임, 해시태그

        //then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findAll(pageable);
    }

    @Test
    @DisplayName("[Service] - 단일 게시글 ID로 조회 " +
            "(Param: Long)" +
            "(Return: ArticleWithArticleCommentsDto)")
    void getArticleTest() {
        //given
        Long articleId = 1L;
        Article article = createArticle();
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

        //when
        ArticleWithArticleCommentsDto result = sut.getArticle(articleId);

        //then
        assertThat(result)
                .hasFieldOrPropertyWithValue("title", article.getTitle())
                .hasFieldOrPropertyWithValue("content", article.getContent())
                .hasFieldOrPropertyWithValue("hashtag", article.getHashtag());
        then(articleRepository).should().findById(articleId);
    }

    @Test
    @DisplayName("[Service] - 단일 게시글 ID로 조회 : 없는 게시글 조회 시 예외 던짐 " +
            "(Param: Long)" +
            "(Return: ArticleWithArticleCommentsDto)")
    void getArticle_NoArticleTest() {
        //given
        Long articleId = 0L;
        given(articleRepository.findById(articleId)).willReturn(Optional.empty());

        //when
        Throwable result = catchThrowable(() -> sut.getArticle(articleId));

        //then
        assertThat(result)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("게시글이 없습니다.");
        then(articleRepository).should().findById(articleId);
    }

    @Test
    @DisplayName("[Service] - 게시글 생성 " +
            "(Param: ArticleDto)" +
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
            "(Param: ArticleDto)" +
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
    @DisplayName("[Service] - 게시글 수정 : 없는 게시글 수정 시도시 경고 로그 찍기 " +
            "(Param: ArticleDto)" +
            "(Return: void)")
    void updateArticle_NoArticleTest() {
        //given
        ArticleDto request = createArticleDto("새 타이틀", "새 내용", "#새 해시태그");
        given(articleRepository.getReferenceById(request.id())).willThrow(EntityNotFoundException.class);

        //when
        sut.updateArticle(request);

        //then
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
        return UserAccountDto.builder()
                .id(1L)
                .userId("userId")
                .userPassword("userPassword")
                .email("email")
                .nickname("nickname")
                .memo("memo")
                .createdAt(LocalDateTime.now())
                .createdBy("luke")
                .modifiedAt(LocalDateTime.now())
                .modifiedBy("luke")
                .build();
    }

    private ArticleDto createArticleDto() {
        return ArticleDto.builder()
                .id(1L)
                .userAccountDto(createUserAccountDto())
                .title("title")
                .content("content")
                .hashtag("hashtag")
                .createdAt(LocalDateTime.now())
                .createdBy("luke")
                .modifiedAt(LocalDateTime.now())
                .modifiedBy("luke")
                .build();
    }

    private ArticleDto createArticleDto(String title, String content, String hashtag) {
        return ArticleDto.builder()
                .id(1L)
                .userAccountDto(createUserAccountDto())
                .title(title)
                .content(content)
                .hashtag(hashtag)
                .createdAt(LocalDateTime.now())
                .createdBy("luke")
                .modifiedAt(LocalDateTime.now())
                .modifiedBy("luke")
                .build();
    }

    private Article createArticle() {
        return Article.builder()
                .userAccount(createUserAccount())
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