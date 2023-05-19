package com.example.nextboard.domain.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    NOTICE("공지 게시판"),
    QUESTION("질문 게시판"),
    SEMINAR("세미나 게시판");

    private final String key;
}
