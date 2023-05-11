package com.practice.kotlinpractice.domain

import com.practice.kotlinpractice.domain.Role.ROLE_USER
import jakarta.persistence.*
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.GenerationType.*
import org.springframework.security.crypto.password.PasswordEncoder

enum class Role {
    ROLE_USER,
    ROLE_GUEST
}

enum class SocialType {
    KAKAO,
    NAVER,
    GOOGLE
}

@Entity
class Member(

    private val email: String,
    private var password: String,
    private val nickname: String,
    private val imageUrl: String? = null,
    private val age: Int,
    private val city: String,

    @Enumerated(STRING)
    private var role: Role,

    @Enumerated(STRING)
    private val socialType: SocialType? = null,

    private val socialId: String? = null,
    private var refreshToken: String? = null,


    @Id
    @GeneratedValue(strategy = IDENTITY)
    private val id: Long? = null
) {

    fun authorizeUser() {
        this.role = ROLE_USER
    }

    fun passwordEncode(passwordEncoder: PasswordEncoder) {
        this.password = passwordEncoder.encode(this.password)
    }

    fun updateRefreshToken(updateRefreshToken: String) {
        this.refreshToken = updateRefreshToken
    }


}