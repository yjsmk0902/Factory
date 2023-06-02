package com.practice.postexample.dto.articleComment.response;

import com.practice.postexample.dto.articleComment.ArticleCommentDto;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ArticleCommentResponse(
        Long id,
        String content,
        LocalDateTime createdAt,
        String email,
        String nickname
) {
    public static ArticleCommentResponse from(ArticleCommentDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }
        return ArticleCommentResponse.builder()
                .id(dto.id())
                .content(dto.content())
                .createdAt(dto.createdAt())
                .email(dto.userAccountDto().email())
                .nickname(nickname)
                .build();
    }
}
