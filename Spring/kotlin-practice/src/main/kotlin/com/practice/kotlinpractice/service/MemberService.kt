package com.practice.kotlinpractice.service

import com.practice.kotlinpractice.domain.Member
import com.practice.kotlinpractice.domain.MemberRepository
import com.practice.kotlinpractice.domain.Role
import com.practice.kotlinpractice.domain.Role.ROLE_USER
import com.practice.kotlinpractice.dto.MemberSignUpDto
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun signUp(memberSignUpDto: MemberSignUpDto) {
        memberRepository.findByEmail(memberSignUpDto.email)
            ?.let { throw Exception("이미 존재하는 이메일입니다.") }
        memberRepository.findByNickname(memberSignUpDto.nickname)
            ?.let { throw Exception("이미 존재하는 닉네임입니다.") }

        val member = Member(
            email = memberSignUpDto.email,
            password = memberSignUpDto.password,
            nickname = memberSignUpDto.nickname,
            age = memberSignUpDto.age,
            city = memberSignUpDto.city,
            role = ROLE_USER
        )

        member.passwordEncode(passwordEncoder)
        memberRepository.save(member)
    }
}