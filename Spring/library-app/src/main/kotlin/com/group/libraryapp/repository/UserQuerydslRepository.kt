package com.group.libraryapp.repository

import com.group.libraryapp.domain.user.QUser
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.loanhistory.QUserLoanHistory
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class UserQuerydslRepository(
    private val queryFactory:JPAQueryFactory
) {
    fun findAllWithHistories(): List<User> {
        return queryFactory
            .select(QUser.user).distinct()
            .from(QUser.user)
            .leftJoin(QUserLoanHistory.userLoanHistory)
            .on(QUserLoanHistory.userLoanHistory.id.eq(QUser.user.id))
            .fetchJoin()
            .fetch()
    }
}