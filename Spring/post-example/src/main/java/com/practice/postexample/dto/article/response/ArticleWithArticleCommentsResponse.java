package com.practice.postexample.dto.article.response;

import com.practice.postexample.dto.article.ArticleWithArticleCommentsDto;
import com.practice.postexample.dto.articleComment.ArticleCommentDto;
import com.practice.postexample.dto.articleComment.response.ArticleCommentResponse;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record ArticleWithArticleCommentsResponse(
        Long id,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String email,
        String nickname,
        Set<ArticleCommentResponse> articleCommentsResponse
) {
    public static ArticleWithArticleCommentsResponse from(ArticleWithArticleCommentsDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }

        return ArticleWithArticleCommentsResponse.builder()
                .id(dto.id())
                .title(dto.title())
                .content(dto.content())
                .hashtag(dto.hashtag())
                .createdAt(dto.createdAt())
                .email(dto.userAccountDto().email())
                .nickname(nickname)
                .articleCommentsResponse(
                        dto.articleCommentDtos().stream()
                                .map(ArticleCommentResponse::from)
                                .collect(Collectors.toCollection(LinkedHashSet::new))
                )
                .build();
    }
}
