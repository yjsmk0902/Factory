package com.practice.postexample.controller;

import com.practice.postexample.config.SecurityConfig;
import com.practice.postexample.dto.article.ArticleWithArticleCommentsDto;
import com.practice.postexample.dto.userAccount.UserAccountDto;
import com.practice.postexample.service.ArticleService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("View 컨트롤러 - 게시글")
@Import(SecurityConfig.class)
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ArticleService articleService;

    @Test
    @DisplayName("[View] - [GET] 게시글 리스트 (게시판) 페이지")
    void get_ArticlesView_ApiTest() throws Exception {
        //given
        given(articleService.searchArticles(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());

        //when
        mvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"));

        //then
        then(articleService)
                .should()
                .searchArticles(eq(null), eq(null), any(Pageable.class));
    }

    @Test
    @DisplayName("[View] - [GET] 게시글 상세 페이지")
    void get_ArticleView_ApiTest() throws Exception {
        //given
        Long articleId = 1L;
        given(articleService.getArticle(articleId)).willReturn(createArticleWithArticleCommentsDto());

        //when
        mvc.perform(get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/detail"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("articleComments"));

        //then
        then(articleService)
                .should()
                .getArticle(articleId);
    }

    @Disabled("구현중")
    @Test
    @DisplayName("[View] - [GET] 게시글 검색 전용 페이지")
    void get_ArticleSearchView_ApiTest() throws Exception {
        //given

        //when
        mvc.perform(get("/articles/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("articles/search"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));

        //then
    }

    @Disabled("구현중")
    @Test
    @DisplayName("[View] - [GET] 게시글 해시태그 검색 페이지")
    void get_ArticleHashtagSearchView_ApiTest() throws Exception {
        //given

        //when
        mvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(view().name("articles/search-hashtag"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));

        //then
    }

    private ArticleWithArticleCommentsDto createArticleWithArticleCommentsDto() {
        return ArticleWithArticleCommentsDto.builder()
                .id(1L)
                .userAccountDto(createUserAccountDto())
                .articleCommentDtos(Set.of())
                .title("title")
                .content("content")
                .hashtag("hashtag")
                .createdAt(LocalDateTime.now())
                .createdBy("luke")
                .modifiedAt(LocalDateTime.now())
                .modifiedBy("luke")
                .build();
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

}