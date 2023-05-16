package com.practice.kotlinpractice.oauth2.handler

import com.practice.kotlinpractice.domain.MemberRepository
import com.practice.kotlinpractice.domain.Role
import com.practice.kotlinpractice.domain.Role.ROLE_GUEST
import com.practice.kotlinpractice.oauth2.CustomOAuth2User
import com.practice.kotlinpractice.service.jwt.JwtService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2LoginSuccessHandler(
    val jwtService: JwtService,
    val memberRepository: MemberRepository
) : AuthenticationSuccessHandler {


    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        println("OAuth2 Login 성공!!")
        try {
            val oAuth2User = authentication?.principal as CustomOAuth2User

            if (oAuth2User.role == ROLE_GUEST) {
                val accessToken = jwtService.createAccessToken(oAuth2User.email)
                response?.addHeader(jwtService.accessHeader, "Bearer $accessToken")
                response?.sendRedirect("oauth2/sign-up")

                response?.let { jwtService.sendAccessAndRefreshToken(it, accessToken, null) }
            } else {
                loginSuccess(response, oAuth2User)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    private fun loginSuccess(response: HttpServletResponse?, oAuth2User: CustomOAuth2User) {
        val accessToken = jwtService.createAccessToken(oAuth2User.email)
        val refreshToken = jwtService.createRefreshToken()
        response?.addHeader(jwtService.accessHeader, "Bearer $accessToken")
    }
}