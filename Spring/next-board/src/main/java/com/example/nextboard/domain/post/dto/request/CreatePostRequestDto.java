package com.example.nextboard.domain.post.dto.request;

import com.example.nextboard.domain.member.entity.Member;
import com.example.nextboard.domain.post.entity.Category;
import com.example.nextboard.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequestDto {
    private String title;
    private String content;
    private Category category;

    public Post toEntity(Member member) {
        return Post.builder()
                .member(member)
                .comments(null)
                .title(title)
                .content(content)
                .category(category)
                .viewCount(0)
                .recommend(0)
                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd(E) a HH:mm")))
                .build();
    }
}
