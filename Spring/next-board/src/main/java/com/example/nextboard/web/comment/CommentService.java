package com.example.nextboard.web.comment;

import com.example.nextboard.domain.comment.dto.response.CommentResponseDto;
import com.example.nextboard.domain.comment.repository.CommentRepository;
import com.example.nextboard.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    //댓글 뷰 로직
    public Page<CommentResponseDto> viewComment(Post post, Pageable) {
        return
    }
}
