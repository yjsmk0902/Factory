package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@DisplayName("유저 서비스 테스트")
class UserServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val userService: UserService,
    private val userLoanHistoryRepository: UserLoanHistoryRepository
) {

    @AfterEach
    fun clean() {
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("유저 저장 테스트")
    fun saveUserTest() {
        //given
        val request = UserCreateRequest("Luke", null)

        //when
        userService.saveUser(request)

        //then
        val results = userRepository.findAll()

        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("Luke")
        assertThat(results[0].age).isNull()
    }

    @Test
    @DisplayName("유저 조회 테스트")
    fun getUsersTest() {
        //given
        userRepository.saveAll(
            listOf(
                User("A", 20),
                User("B", null)
            )
        )

        //when
        val results = userService.getUsers()

        //then
        assertThat(results).hasSize(2)
        assertThat(results).extracting("name").containsExactlyInAnyOrder("A", "B")
        assertThat(results).extracting("age").containsExactlyInAnyOrder(20, null)

    }

    @Test
    @DisplayName("유저 업데이트 테스트")
    fun updateUserNameTest() {
        //given
        val savedUser = userRepository.save(User("A", null))
        val request = UserUpdateRequest(savedUser.id!!, "B")

        //when
        userService.updateUserName(request)

        //then
        val result = userRepository.findAll()[0]
        assertThat(result.name).isEqualTo("B")
    }

    @Test
    @DisplayName("유저 삭제 테스트")
    fun deleteUserTest() {
        //given
        userRepository.save(User("A", null))

        //when
        userService.deleteUser("A")

        //then
        assertThat(userRepository.findAll()).isEmpty()
    }

    @Test
    @DisplayName("대출 기록이 없는 유저 테스트")
    fun getUserLoanHistoriesTest1() {
        // given
        userRepository.save(User("A", null))

        // when
        val results = userService.getUserLoanHistories()

        // then
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("A")
        assertThat(results[0].books).isEmpty()
    }

    @Test
    @DisplayName("대출 기록이 다양한 유저 테스트")
    fun getUserLoanHistoriesTest2() {
        // given
        val savedUser = userRepository.save(User("A", null))
        userLoanHistoryRepository.saveAll(
            listOf(
                UserLoanHistory.fixture(savedUser, "책1", UserLoanStatus.LOANED),
                UserLoanHistory.fixture(savedUser, "책2", UserLoanStatus.LOANED),
                UserLoanHistory.fixture(savedUser, "책3", UserLoanStatus.RETURNED),
            )
        )

        // when
        val results = userService.getUserLoanHistories()

        // then
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("A")
        assertThat(results[0].books).hasSize(3)
        assertThat(results[0].books).extracting("name")
            .containsExactlyInAnyOrder("책1", "책2", "책3")
        assertThat(results[0].books).extracting("isReturn")
            .containsExactlyInAnyOrder(false, false, true)
    }

    //위에 두개 합친거 (유지보수가 별로 안좋아서 그냥 따로하는게 나을듯)
//    @Test
//    @DisplayName("대출 기록 유저 테스트")
//    fun getUserLoanHistoriesTest() {
//        // given
//        val savedUser = userRepository.saveAll(
//            listOf(
//                User("A", null),
//                User("B", null),
//            )
//        )
//
//        userLoanHistoryRepository.saveAll(
//            listOf(
//                UserLoanHistory.fixture(savedUser[0], "책1", UserLoanStatus.LOANED),
//                UserLoanHistory.fixture(savedUser[0], "책2", UserLoanStatus.LOANED),
//                UserLoanHistory.fixture(savedUser[0], "책3", UserLoanStatus.RETURNED),
//            )
//        )
//
//        // when
//        val results = userService.getUserLoanHistories()
//
//        // then
//        assertThat(results).hasSize(2)
//
//        val userAResult = results.first{ it.name == "A"}
//
//        assertThat(userAResult.books).hasSize(3)
//        assertThat(userAResult.books).extracting("name")
//            .containsExactlyInAnyOrder("책1", "책2", "책3")
//        assertThat(userAResult.books).extracting("isReturn")
//            .containsExactlyInAnyOrder(false, false, true)
//
//        val userBResult = results.first{ it.name == "B"}
//
//        assertThat(userBResult.books).isEmpty()
//    }
}