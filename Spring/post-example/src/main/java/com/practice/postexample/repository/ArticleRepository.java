package com.practice.postexample.repository;

import com.practice.postexample.domain.Article;
import com.practice.postexample.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>,     //엔티티 안에 모든 필드에 대한 기본 검색기능 추가 (Exact Match)
        QuerydslBinderCustomizer<QArticle>      //QueryDSL 을 입맛대로 커스타마이징 할 수 있게 해줌
{

    /**
     * Customize the {@link QuerydslBindings} for the given root.
     *
     * @param bindings the {@link QuerydslBindings} to customize, will never be {@literal null}.
     * @param root     the entity root, will never be {@literal null}.
     */
    @Override
    //default 메소드로 인터페이스 안에 구현 가능하도록 만듬
    default void customize(QuerydslBindings bindings, QArticle root) {

        bindings.excludeUnlistedProperties(true);   //모든 엔티티 요소를 제외
        bindings.including(root.title, root.hashtag, root.createdAt, root.createdBy);   //원하는 요소를 검색 대상으로 추가
        bindings.bind(root.title)       //title 요소에서
                .first(StringExpression::containsIgnoreCase);     //첫번째로 받은 value 를 대소문자 구분없이 검색
        bindings.bind(root.hashtag)
                .first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt)
                .first(DateTimeExpression::eq);                   //시분초까지 동일해야 함 (Exact) -> 수정 필요
        bindings.bind(root.createdBy)
                .first(StringExpression::containsIgnoreCase);

    }
}
