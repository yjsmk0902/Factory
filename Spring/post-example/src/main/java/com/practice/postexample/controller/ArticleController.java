package com.practice.postexample.controller;

import com.practice.postexample.domain.type.SearchType;
import com.practice.postexample.dto.article.ArticleWithArticleCommentsDto;
import com.practice.postexample.dto.article.response.ArticleResponse;
import com.practice.postexample.dto.article.response.ArticleWithArticleCommentsResponse;
import com.practice.postexample.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public String articles(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ) {
        map.addAttribute("articles", articleService.searchArticles(searchType, searchValue, pageable).map(ArticleResponse::from));

        return "articles/index";
    }

    @GetMapping("/{articleId}")
    public String article(
            @PathVariable Long articleId,
            ModelMap map
    ) {
        ArticleWithArticleCommentsResponse article = ArticleWithArticleCommentsResponse.from(articleService.getArticle(articleId));
        map.addAttribute("article", article); //TODO: 구현할 때 실제 데이터로 바꿔줘야함
        map.addAttribute("articleComments", article.articleCommentsResponse());
        return "articles/detail";
    }
}
