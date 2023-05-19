package com.example.nextboard.config;

import com.example.nextboard.domain.comment.repository.CommentRepository;
import com.example.nextboard.domain.member.entity.Member;
import com.example.nextboard.domain.member.repository.MemberRepository;
import com.example.nextboard.domain.post.entity.Category;
import com.example.nextboard.domain.post.entity.Post;
import com.example.nextboard.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initDB() {
        initTestMember();
    }

    public void initTestMember() {
        Member member1 = Member.builder()
                .email("test1@gmail.com")
                .password("test1")
                .name("테스트 사용자1")
                .grade(4)
                .fellowNumber(18)
                .build();

        Member member2 = Member.builder()
                .email("test2@gmail.com")
                .password("test2")
                .name("테스트 사용자2")
                .grade(3)
                .fellowNumber(19)
                .build();

        Member member3 = Member.builder()
                .email("test3@gmail.com")
                .password("test3")
                .name("테스트 사용자3")
                .grade(2)
                .fellowNumber(20)
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        Post post1 = Post.builder()
                .member(member1)
                .title("게시판 제목1")
                .content("게시판 내용1")
                .viewCount(40)
                .recommend(2)
                .category(Category.NOTICE)
                .date("2023-05-19 (금) 오후 13:44")
                .build();

        postRepository.save(post1);
    }
}
