package com.practice.postexample.dto.article;

import com.practice.postexample.domain.Article;
import com.practice.postexample.dto.userAccount.UserAccountDto;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ArticleDto(
        Long id,
        UserAccountDto userAccountDto,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static ArticleDto from(Article entity) {
        return ArticleDto.builder()
                .id(entity.getId())
                .userAccountDto(UserAccountDto.from(entity.getUserAccount()))
                .title(entity.getTitle())
                .content(entity.getContent())
                .hashtag(entity.getHashtag())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .modifiedAt(entity.getModifiedAt())
                .modifiedBy(entity.getModifiedBy())
                .build();
    }

    public Article toEntity() {
        return Article.builder()
                .userAccount(userAccountDto.toEntity())
                .title(title)
                .content(content)
                .hashtag(hashtag)
                .build();
    }
}
