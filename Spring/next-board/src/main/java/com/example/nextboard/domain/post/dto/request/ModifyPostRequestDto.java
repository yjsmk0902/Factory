package com.example.nextboard.domain.post.dto.request;

import com.example.nextboard.domain.post.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyPostRequestDto {
    String title;
    String content;
    Category category;
}
