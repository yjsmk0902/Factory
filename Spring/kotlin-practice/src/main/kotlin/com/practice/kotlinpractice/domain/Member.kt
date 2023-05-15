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

    val email: String,
    var password: String? = null,
    val nickname: String,
    val imageUrl: String? = null,
    val age: Int,
    val city: String,

    @Enumerated(STRING)
    var role: Role,

    @Enumerated(STRING)
    val socialType: SocialType? = null,

    val socialId: String? = null,
    var refreshToken: String? = null,


    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null
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