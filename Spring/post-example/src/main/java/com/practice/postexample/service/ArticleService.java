package com.practice.postexample.service;

import com.practice.postexample.domain.type.SearchType;
import com.practice.postexample.dto.ArticleCommentDto;
import com.practice.postexample.dto.ArticleDto;
import com.practice.postexample.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Page<ArticleDto> searchArticles(SearchType title, String searchKeyword) {
        return Page.empty();
    }
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        return null;
    }
    public ArticleDto getArticle(Long articleId) {
        return null;
    }

    @Transactional
    public void saveArticle(ArticleDto dto) {
    }

    @Transactional
    public void updateArticle(ArticleDto dto) {
    }

    public void deleteArticle(long articleId) {
    }



}
