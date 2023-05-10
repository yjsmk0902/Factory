package com.practice.kotlinpractice.service.auth

import com.practice.kotlinpractice.domain.member.Member
import com.practice.kotlinpractice.domain.member.MemberRepository
import com.practice.kotlinpractice.domain.member.RoleType
import com.practice.kotlinpractice.domain.member.RoleType.ROLE_USER
import com.practice.kotlinpractice.dto.JwtDto
import com.practice.kotlinpractice.service.security.JwtProvider
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService (
    private val memberRepository: MemberRepository,
    private val jwtProvider: JwtProvider
){

    @Transactional
    fun login(oAuth2User: OAuth2User): JwtDto {
        //회원이 아니면 회원가입을 시켜줌
        if (!memberRepository.existsByEmail(oAuth2User.attributes["email"] as String)) {
            val member = Member(
                email = oAuth2User.attributes["email"] as String,
                role = ROLE_USER
            )
            memberRepository.save(member)
        }

        //token을 생성해줌
        return jwtProvider.generateJwt(oAuth2User)
    }
}