package com.practice.kotlinpractice.domain

import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByEmail(email: String): Member?
    fun findByNickname(nickname: String): Member?
    fun findByRefreshToken(refreshToken: String): Member?
    fun findBySocialTypeAndSocialId(socialType: SocialType, socialId: String): Member?
}