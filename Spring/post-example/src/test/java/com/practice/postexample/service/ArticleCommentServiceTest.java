package com.practice.postexample.service;

import com.practice.postexample.domain.Article;
import com.practice.postexample.domain.ArticleComment;
import com.practice.postexample.domain.UserAccount;
import com.practice.postexample.dto.articleComment.ArticleCommentDto;
import com.practice.postexample.dto.article.ArticleDto;
import com.practice.postexample.dto.userAccount.UserAccountDto;
import com.practice.postexample.repository.ArticleCommentRepository;
import com.practice.postexample.repository.ArticleRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("[Service] - 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {

    @InjectMocks
    private ArticleCommentService sut;

    @Mock
    private ArticleCommentRepository articleCommentRepository;
    @Mock
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("[Service] - 게시글 ID로 댓글 조회 :Success" +
            "(Param: Long)" +
            "(Return: List<ArticleComment>)")
    void searchArticleCommentsTest() {
        //given
        Long articleId = 1L;
        ArticleComment expected = createArticleComment("content");
        given(articleCommentRepository.findByArticle_Id(articleId)).willReturn(List.of(expected));

        //when
        List<ArticleComment> articleComments = sut.searchArticleComment(articleId);

        //then
        assertThat(articleComments)
                .hasSize(1)
                .first().hasFieldOrPropertyWithValue("content", expected.getContent());

        then(articleCommentRepository).should().findByArticle_Id(articleId);
    }


    @Test
    @DisplayName("[Service] - 댓글 생성 :Success" +
            "(Param: ArticleCommentDto)" +
            "(Return: void)")
    void createArticleCommentTest() {
        //given
        ArticleCommentDto request = createArticleCommentDto("댓글");
        given(articleRepository.getReferenceById(request.articleDto().id())).willReturn(createArticle());
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);

        //when
        sut.saveArticleComment(request);

        //then
        then(articleRepository).should().getReferenceById(request.articleDto().id());
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }

    @Test
    @DisplayName("[Service] - 댓글 생성시 게시글이 없는 경우 -> 로그 찍고 아무것도 안함 :Failed" +
            "(Param: ArticleCommentDto)" +
            "(Return: void)")
    void createArticleCommentTest_caseCantFindArticle() {
        //given
        ArticleCommentDto request = createArticleCommentDto("댓글");
        given(articleRepository.getReferenceById(request.articleDto().id())).willThrow(EntityNotFoundException.class);

        //when
        sut.saveArticleComment(request);

        //then
        then(articleRepository).should().getReferenceById(request.articleDto().id());
        then(articleCommentRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("[Service] - 댓글 수정 :Success" +
            "(Param: ArticleCommentDto)" +
            "(Return: void)")
    void updateArticleCommentTest() {
        //given
        String oldContent = "content";
        String updateContent = "댓글";
        ArticleComment articleComment = createArticleComment(oldContent);
        ArticleCommentDto request = createArticleCommentDto(updateContent);

        given(articleCommentRepository.getReferenceById(request.id())).willReturn(articleComment);

        //when
        sut.updateArticleComment(request);

        //then
        assertThat(articleComment.getContent())
                .isNotEqualTo(oldContent)
                .isEqualTo(updateContent);
        then(articleCommentRepository).should().getReferenceById(request.id());
    }

    @Test
    @DisplayName("[Service] - 댓글 수정시 게시글이 없는 경우 -> 로그 찍고 아무것도 안함 :Failed" +
            "(Param: ArticleCommentDto)" +
            "(Return: void)")
    void updateArticleCommentTest_caseCantFindArticle() {
        //given
        ArticleCommentDto request = createArticleCommentDto("댓글");

        given(articleCommentRepository.getReferenceById(request.id())).willThrow(EntityNotFoundException.class);

        //when
        sut.updateArticleComment(request);

        //then
        then(articleCommentRepository).should().getReferenceById(request.id());
    }

    @Test
    @DisplayName("[Service] - 댓글 삭제 :Success" +
            "(Param: Long)" +
            "(Return: void)")
    void deleteArticleCommentTest() {
        //given
        Long articleCommentId = 1L;
        willDoNothing().given(articleCommentRepository).deleteById(articleCommentId);

        //when
        sut.deleteArticleComment(articleCommentId);

        //then
        then(articleCommentRepository).should().deleteById(articleCommentId);
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                1L,
                "userId",
                "userPassword",
                "email",
                "nickname",
                "memo",
                LocalDateTime.now(),
                "luke",
                LocalDateTime.now(),
                "luke"
        );
    }

    private ArticleDto createArticleDto() {
        return ArticleDto.of(
                1L,
                createUserAccountDto(),
                "title",
                "content",
                "hashtag",
                LocalDateTime.now(),
                "luke",
                LocalDateTime.now(),
                "luke"
        );
    }

    private ArticleCommentDto createArticleCommentDto(String content) {
        return ArticleCommentDto.of(
                1L,
                createArticleDto(),
                createUserAccountDto(),
                content,
                LocalDateTime.now(),
                "luke",
                LocalDateTime.now(),
                "luke"
        );
    }

    private ArticleComment createArticleComment(String content) {
        return ArticleComment.builder()
                .article(createArticle())
                .userAccount(createUserAccount())
                .content(content)
                .build();
    }

    private Article createArticle() {
        return Article.builder()
                .title("title")
                .content("content")
                .hashtag("hashtag")
                .build();
    }

    private UserAccount createUserAccount() {
        return UserAccount.builder()
                .userId("userId")
                .userPassword("qwert1234")
                .email("email@mail.com")
                .nickname("luke")
                .memo("memo")
                .build();
    }
}