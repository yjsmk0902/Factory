package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.lang.IllegalArgumentException

@SpringBootTest
@DisplayName("책 서비스 테스트")
class BookServiceTest @Autowired constructor(
    private val bookService: BookService,
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
) {

    @AfterEach
    fun clean() {
        bookRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("책 저장 테스트")
    fun saveBookTest() {
        //given
        val request = BookRequest("Harry Potter")

        //when
        bookService.saveBook(request)

        //then
        val books = bookRepository.findAll()
        assertThat(books).hasSize(1)
        assertThat(books[0].name).isEqualTo("Harry Potter")
    }

    @Test
    @DisplayName("책 대출 성공 테스트")
    fun loanBookTest() {
        //given
        bookRepository.save(Book("Harry Potter"))
        val savedUser = userRepository.save(User("Luke", null))
        val request = BookLoanRequest("Luke", "Harry Potter")

        //when
        bookService.loanBook(request)

        //then
        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].bookName).isEqualTo("Harry Potter")
        assertThat(results[0].user.id).isEqualTo(savedUser.id)
        assertThat(results[0].isReturn).isFalse
    }

    @Test
    @DisplayName("책 대출 실패 테스트(이미 대출 상태)")
    fun loanBookFailTest() {
        //given
        bookRepository.save(Book("Harry Potter"))
        val savedUser = userRepository.save(User("Luke", null))
        userLoanHistoryRepository.save(UserLoanHistory(savedUser, "Harry Potter", false))
        val request = BookLoanRequest("Luke", "Harry Potter")

        //when

        //then
        assertThrows<IllegalArgumentException> {
            bookService.loanBook(request)
        }.apply {
            assertThat(message).isEqualTo("진작 대출되어 있는 책입니다")
        }
    }

    @Test
    @DisplayName("책 반납 테스트")
    fun returnBookTest() {
        //given
        val savedUser = userRepository.save(User("Luke", null))
        userLoanHistoryRepository.save(UserLoanHistory(savedUser, "Harry Potter", false))
        val request = BookReturnRequest("Luke", "Harry Potter")

        //when
        bookService.returnBook(request)

        //then
        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].isReturn).isTrue
    }
}