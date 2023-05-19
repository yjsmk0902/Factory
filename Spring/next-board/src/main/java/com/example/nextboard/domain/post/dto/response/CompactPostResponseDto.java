package com.example.nextboard.domain.post.dto.response;

import com.example.nextboard.domain.member.entity.Member;
import com.example.nextboard.domain.post.entity.Category;
import com.example.nextboard.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompactPostResponseDto {

    private String title;
    private int viewCount;
    private int recommend;
    private String author;
    private String category;
    private String date;

    public static CompactPostResponseDto toDto(Post post) {
        return CompactPostResponseDto.builder()
                .title(post.getTitle())
                .viewCount(post.getViewCount())
                .recommend(post.getRecommend())
                .author(post.getMember().getName())
                .category(post.getCategory().getKey())
                .date(post.getDate())
                .build();
    }

}
