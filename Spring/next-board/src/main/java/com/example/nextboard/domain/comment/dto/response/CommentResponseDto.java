package com.example.nextboard.domain.comment.dto.response;

import com.example.nextboard.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDto {
    private String content;
    private String date;

    public static CommentResponseDto toDto(Comment comment) {
        return CommentResponseDto.builder()
                .content(comment.getContent())
                .date(comment.getDate().toString())
                .build();
    }
}
