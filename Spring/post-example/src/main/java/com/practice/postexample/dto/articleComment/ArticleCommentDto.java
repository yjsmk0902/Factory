package com.practice.postexample.dto.articleComment;

import com.practice.postexample.domain.ArticleComment;
import com.practice.postexample.dto.userAccount.UserAccountDto;
import com.practice.postexample.dto.article.ArticleDto;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
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

    public static ArticleCommentDto from(ArticleComment entity) {
        return ArticleCommentDto.builder()
                .id(entity.getId())
                .articleDto(ArticleDto.from(entity.getArticle()))
                .userAccountDto(UserAccountDto.from(entity.getUserAccount()))
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .modifiedAt(entity.getModifiedAt())
                .modifiedBy(entity.getModifiedBy())
                .build();
    }

    public ArticleComment toEntity() {
        return ArticleComment.builder()
                .article(articleDto.toEntity())
                .userAccount(userAccountDto.toEntity())
                .content(content)
                .build();
    }
}
