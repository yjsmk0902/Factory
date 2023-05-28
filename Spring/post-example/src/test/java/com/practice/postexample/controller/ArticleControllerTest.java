package com.practice.postexample.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("View 컨트롤러 - 게시글")
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    private final MockMvc mvc;


    public ArticleControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @Disabled("구현중")
    @Test
    @DisplayName("[View] - [GET] 게시글 리스트 (게시판) 페이지")
    void get_ArticlesView_ApiTest() throws Exception {
        //given

        //when
        mvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"));

        //then
    }

    @Disabled("구현중")
    @Test
    @DisplayName("[View] - [GET] 게시글 상세 페이지")
    void get_ArticleView_ApiTest() throws Exception {
        //given

        //when
        mvc.perform(get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/detail"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("articleComments"));


        //then
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
                .andExpect(content().contentType(MediaType.TEXT_HTML));

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
                .andExpect(content().contentType(MediaType.TEXT_HTML));

        //then
    }

}