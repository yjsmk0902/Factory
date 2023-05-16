package com.practice.kotlinpractice.oauth2.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component

@Component
class OAuth2LoginFailureHandler:AuthenticationFailureHandler {

    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException?
    ) {
        response?.status = HttpServletResponse.SC_BAD_REQUEST
        response?.writer!!.write("소셜 로그인 실패! 서버 로그 확인 바람")
        println("소셜 로그인에 실패함. ${exception!!.message}")
    }
}