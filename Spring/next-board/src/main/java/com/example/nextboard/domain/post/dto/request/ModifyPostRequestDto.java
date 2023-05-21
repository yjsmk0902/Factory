package com.example.nextboard.domain.post.dto.request;

import com.example.nextboard.domain.post.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModifyPostRequestDto {
    String title;
    String content;
    Category category;
}
