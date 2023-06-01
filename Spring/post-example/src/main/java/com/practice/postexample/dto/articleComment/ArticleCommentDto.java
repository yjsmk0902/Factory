package com.practice.postexample.dto.articleComment;

import com.practice.postexample.domain.ArticleComment;
import com.practice.postexample.dto.userAccount.UserAccountDto;
import com.practice.postexample.dto.article.ArticleDto;

import java.time.LocalDateTime;

public record ArticleCommentDto(
        Long id,
        ArticleDto articleDto,
        UserAccountDto userAccountDto,
        String content,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    public static ArticleCommentDto of(
            Long id,
            ArticleDto articleDto,
            UserAccountDto userAccountDto,
            String content,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime modifiedAt,
            String modifiedBy
    ) {
        return new ArticleCommentDto(
                id,
                articleDto,
                userAccountDto,
                content,
                createdAt,
                createdBy,
                modifiedAt,
                modifiedBy
        );
    }

    public static ArticleCommentDto from(ArticleComment entity) {
        return new ArticleCommentDto(
                entity.getId(),
                ArticleDto.from(entity.getArticle()),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public ArticleComment toEntity() {
        return ArticleComment.builder()
                .article(articleDto.toEntity())
                .userAccount(userAccountDto.toEntity())
                .content(content)
                .build();
    }
}
