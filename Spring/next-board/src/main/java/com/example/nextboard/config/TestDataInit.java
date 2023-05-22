package com.example.nextboard.config;

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

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

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

        List<Post> posts = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            Post post = Post.builder()
                    .member(member1)
                    .title("게시글 제목" + i)
                    .content("게시글 내용" + i)
                    .viewCount(40 + i)
                    .recommend(5 + i)
                    .category(Category.NOTICE)
                    .date("2023-05-20 (토) 오후 20:0" + (i + 6))
                    .build();
            posts.add(post);
        }

        postRepository.saveAll(posts);

        Post post1 = Post.builder()
                .member(member2)
                .title("게시글 제목1 - 사용자2")
                .content("게시글 내용1")
                .viewCount(2)
                .recommend(0)
                .category(Category.QUESTION)
                .date("2023-05-22 (월) 오전 10:17")
                .build();

        postRepository.save(post1);

    }
}
