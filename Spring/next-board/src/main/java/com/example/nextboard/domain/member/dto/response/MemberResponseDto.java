package com.example.nextboard.domain.member.dto.response;

import com.example.nextboard.domain.comment.entity.Comment;
import com.example.nextboard.domain.member.entity.Member;
import com.example.nextboard.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponseDto {

    private String email;
    private String password;
    private String name;

    private int grade;
    private int fellowNumber;

    public static MemberResponseDto toDto(Member member) {
        return MemberResponseDto.builder()
                .name(member.getName())
                .grade(member.getGrade())
                .fellowNumber(member.getFellowNumber())
                .build();
    }

}
