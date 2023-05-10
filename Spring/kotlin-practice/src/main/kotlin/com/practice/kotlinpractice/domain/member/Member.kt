package com.practice.kotlinpractice.domain.member

import com.practice.kotlinpractice.domain.board.Board
import jakarta.persistence.*
import jakarta.persistence.GenerationType.*
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
class Member(

    val email: String,

    @Enumerated(EnumType.STRING)
    val role : RoleType,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    val boards: MutableList<Board>? = mutableListOf(),

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private val id: Long? = null
) {


}

enum class RoleType {
    ROLE_USER,
    ROLE_ADMIN
}
