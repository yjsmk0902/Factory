package com.practice.kotlinpractice.filter.jwt

import com.practice.kotlinpractice.domain.Member
import com.practice.kotlinpractice.domain.MemberRepository
import com.practice.kotlinpractice.service.jwt.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.filter.OncePerRequestFilter
import kotlin.random.Random

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
            ?.takeIf { jwtService.isTokenValid(it) }

        refreshToken?.let {
            checkRefreshTokenAndReIssueAccessToken(response, it)
        } ?: checkAccessTokenAndAuthentication(request, response, filterChain)


    }

    private fun checkRefreshTokenAndReIssueAccessToken(
        response: HttpServletResponse,
        refreshToken: String
    ) {
        memberRepository.findByRefreshToken(refreshToken)
            ?.let {
                val reIssuedRefreshToken = reIssueRefreshToken(it)
                jwtService.sendAccessAndRefreshToken(
                    response,
                    jwtService.createAccessToken(it.email),
                    reIssuedRefreshToken
                )

            }
    }

    private fun reIssueRefreshToken(member: Member): String {
        val reIssuedRefreshToken = jwtService.createRefreshToken()
        member.updateRefreshToken(reIssuedRefreshToken)
        memberRepository.saveAndFlush(member)
        return reIssuedRefreshToken
    }

    private fun checkAccessTokenAndAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        jwtService.extractAccessToken(request)
            ?.takeIf { jwtService.isTokenValid(it) }
            ?.let {
                jwtService.extractEmail(it)
                    ?.let { email ->
                        memberRepository.findByEmail(email)
                            ?.let { member ->
                                this.saveAuthentication(member)
                            }
                    }
            }

        filterChain.doFilter(request, response)
    }

    private fun saveAuthentication(
        member: Member,
        passwordEncoder: PasswordEncoder = BCryptPasswordEncoder(),
    ) {
        val randomPassword = (1..8).map { Random.nextInt(0, 10) }.joinToString("")
        val password = member.password
            ?: passwordEncoder.encode(randomPassword)

        val userDetailUser =
            User.builder()
                .username(member.email)
                .password(password)
                .roles(member.role.name)
                .build()

        val authentication =
            UsernamePasswordAuthenticationToken(
                userDetailUser,
                null,
                authoritiesMapper.mapAuthorities(userDetailUser.authorities)
            )

        SecurityContextHolder.getContext().authentication = authentication
    }


}