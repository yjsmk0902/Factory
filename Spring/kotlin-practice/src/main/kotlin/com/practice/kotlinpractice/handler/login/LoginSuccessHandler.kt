package com.practice.kotlinpractice.handler.login

import com.practice.kotlinpractice.domain.MemberRepository
import com.practice.kotlinpractice.service.jwt.JwtService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler

class LoginSuccessHandler(
    private val jwtService: JwtService,
    private val memberRepository: MemberRepository,

    @Value("\${jwt.access.expiration}")
    val accessTokenExpiration: String? = null
) : SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val email = extractUsername(authentication)
        val accessToken = jwtService.createAccessToken(email)
        val refreshToken = jwtService.createRefreshToken()

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken)
        memberRepository.findByEmail(email)
            ?.let { member ->
                member.updateRefreshToken(refreshToken)
                memberRepository.saveAndFlush(member)
            }
        println("로그인에 성공하였습니다. 이메일 : $email")
        println("로그인에 성공하였습니다. AccessToken : $accessToken")
        println("발급된 AccessToken 만료 기간 : $accessTokenExpiration")
    }

    private fun extractUsername(authentication: Authentication): String {
        val userDetails = authentication.principal as UserDetails
        return userDetails.username

    }

}

