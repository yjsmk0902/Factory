package com.group.libraryapp.repository

import com.group.libraryapp.domain.book.QBook
import com.group.libraryapp.dto.book.response.BookStatResponse
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class BookQuerydslRepository(
    private val queryFactory: JPAQueryFactory
) {
    fun getStats(): List<BookStatResponse> {
        return queryFactory
            .select(
                Projections.constructor(
                    BookStatResponse::class.java,
                    QBook.book.type,
                    QBook.book.id.count()
                )
            )
            .from(QBook.book)
            .groupBy(QBook.book.type)
            .fetch()
    }
}