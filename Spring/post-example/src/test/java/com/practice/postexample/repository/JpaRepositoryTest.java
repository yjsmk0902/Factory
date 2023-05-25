package com.practice.postexample.repository;

import com.practice.postexample.config.JpaConfig;
import com.practice.postexample.domain.Article;
import com.practice.postexample.domain.ArticleComment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository
    ) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @Test
    @DisplayName("SELECT Test")
    void selectTest() {
        //given

        //when
        List<Article> articles = articleRepository.findAll();
        List<ArticleComment> articleComments = articleCommentRepository.findAll();

        //then
        assertThat(articles).isNotNull().hasSize(100);
        assertThat(articleComments).isNotNull().hasSize(1000);
    }
    
    @Test
    @DisplayName("INSERT Test")
    void insertTest(){
        //given
        long previousCount = articleRepository.count();
        Article article = Article.of("new article", "new content", "#spring");

        //when
        Article savedArticle = articleRepository.save(article);

        //then
        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
    }

    @Test
    @DisplayName("UPDATE Test")
    void updateTest(){
        //given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#springboot";
        article.setHashtag(updatedHashtag);

        //when
        Article savedArticle = articleRepository.saveAndFlush(article);

        //then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }
    
    @Test
    @DisplayName("DELETE Test")
    void deleteTest(){
        //given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        int deletedCommentsSize = article.getArticleComments().size();

        //when
        articleRepository.delete(article);

        //then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentsSize);
    }
}
