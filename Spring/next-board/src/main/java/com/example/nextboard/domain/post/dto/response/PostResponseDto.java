package com.example.nextboard.domain.post.dto.response;

import com.example.nextboard.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponseDto {

    private CompactPostResponseDto compactPostResponseDto;
    private String content;

    public static PostResponseDto toDto(Post post) {
        return PostResponseDto.builder()
                .compactPostResponseDto(CompactPostResponseDto.toDto(post))
                .content(post.getContent())
                .build();
    }

}
