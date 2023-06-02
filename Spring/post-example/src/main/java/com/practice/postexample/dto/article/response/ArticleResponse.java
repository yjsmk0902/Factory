package com.practice.postexample.dto.article.response;

import com.practice.postexample.dto.article.ArticleDto;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ArticleResponse(
        Long id,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String email,
        String nickname
) {
    public static ArticleResponse from(ArticleDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }

        return ArticleResponse.builder()
                .id(dto.id())
                .title(dto.title())
                .content(dto.content())
                .hashtag(dto.hashtag())
                .createdAt(dto.createdAt())
                .email(dto.userAccountDto().email())
                .nickname(nickname)
                .build();
    }
}
