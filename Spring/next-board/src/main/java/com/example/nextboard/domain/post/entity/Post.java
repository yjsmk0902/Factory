package com.example.nextboard.domain.post.entity;

import com.example.nextboard.domain.comment.entity.Comment;
import com.example.nextboard.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_no")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    private String title;
    private String content;

    private int viewCount;
    private int recommend;

    private Category category;
    private String date;

    public void modifyPost(
            String title,
            String content,
            Category category
    ) {
        this.title = title;
        this.content = content;
        this.viewCount = 0;
        this.recommend = 0;
        this.category = category;
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd(E) a HH:mm"));
    }
}
