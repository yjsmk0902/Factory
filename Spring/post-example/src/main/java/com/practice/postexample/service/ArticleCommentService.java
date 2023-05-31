package com.practice.postexample.service;

import com.practice.postexample.domain.ArticleComment;
import com.practice.postexample.dto.ArticleCommentDto;
import com.practice.postexample.repository.ArticleCommentRepository;
import com.practice.postexample.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleCommentService {

    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleRepository articleRepository;

    public List<ArticleComment> searchArticleComment(long articleId) {
        return null;
    }

    @Transactional
    public void saveArticleComment(ArticleCommentDto request) {
    }

    @Transactional
    public void updateArticleComment(ArticleCommentDto request) {
    }

    @Transactional
    public void deleteArticleComment(Long articleCommentId) {
    }
}
