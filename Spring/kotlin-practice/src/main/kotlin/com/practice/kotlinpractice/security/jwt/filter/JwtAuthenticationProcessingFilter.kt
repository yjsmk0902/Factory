package com.practice.kotlinpractice.security.jwt.filter

import com.practice.kotlinpractice.domain.MemberRepository
import com.practice.kotlinpractice.security.jwt.service.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationProcessingFilter(
    private val jwtService: JwtService,
    private val memberRepository: MemberRepository,
    private val authoritiesMapper: GrantedAuthoritiesMapper = NullAuthoritiesMapper()
) : OncePerRequestFilter(
) {

    companion object {
        private const val NO_CHECK_URL = "/login"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.requestURI.equals(NO_CHECK_URL)) {
            filterChain.doFilter(request, response)
            return
        }

        val refreshToken = jwtService.extractRefreshToken(request)
            ?.first{  }
    }
}