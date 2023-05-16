package com.practice.kotlinpractice.service.login

import com.practice.kotlinpractice.domain.MemberRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LoginService(
    val memberRepository: MemberRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String?): UserDetails {
        val member = email?.let { memberRepository.findByEmail(it) }
            ?: throw UsernameNotFoundException("해당 이메일이 존재하지 않습니다.")

        return User.builder()
            .username(member.email)
            .password(member.password)
            .roles(member.role.name)
            .build()
    }

}