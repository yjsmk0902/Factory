package com.practice.kotlinpractice.service.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.practice.kotlinpractice.domain.MemberRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class JwtService(

    private val memberRepository: MemberRepository,

    @Value("\${jwt.secretKey}")
    val secretKey: String,
    @Value("\${jwt.access.expiration}")
    val accessTokenExpirationPeriod: Long,
    @Value("\${jwt.refresh.expiration}")
    val refreshTokenExpirationPeriod: Long,
    @Value("\${jwt.access.header}")
    val accessHeader: String,
    @Value("\${jwt.refresh.header}")
    val refreshHeader: String
) {
    companion object {
        private const val ACCESS_TOKEN_SUBJECT = "AccessToken"
        private const val REFRESH_TOKEN_SUBJECT = "RefreshToken"
        private const val EMAIL_CLAIM = "email"
        private const val BEARER = "Bearer "
    }

    fun createAccessToken(email: String): String {
        val now = Date()
        return JWT.create()
            .withSubject(ACCESS_TOKEN_SUBJECT)
            .withExpiresAt(Date(now.time + accessTokenExpirationPeriod))
            .withClaim(EMAIL_CLAIM, email)
            .sign(Algorithm.HMAC512(secretKey))
    }

    fun createRefreshToken(): String {
        val now = Date()
        return JWT.create()
            .withSubject(REFRESH_TOKEN_SUBJECT)
            .withExpiresAt(Date(now.time + refreshTokenExpirationPeriod))
            .sign(Algorithm.HMAC512(secretKey))
    }

    fun sendAccessAndRefreshToken(
        response: HttpServletResponse,
        accessToken: String?,
        refreshToken: String?
    ) {
        response.status = HttpServletResponse.SC_OK

        accessToken?.let { setAccessTokenHeader(response, it) }
        refreshToken?.let { setRefreshTokenHeader(response, it) }
    }

    fun extractRefreshToken(request: HttpServletRequest): String? {
        return request.getHeader(refreshHeader)
            ?.replace(BEARER, "")
    }

    fun extractAccessToken(request: HttpServletRequest): String? {
        return request.getHeader(accessHeader)
            ?.replace(BEARER, "")
    }

    fun extractEmail(accessToken: String): String? {
        try {
            return JWT.require(Algorithm.HMAC512(secretKey))
                .build()
                .verify(accessToken)
                .getClaim(EMAIL_CLAIM)
                .asString()

        } catch (e: Exception) {
            println("액세스 토큰이 유효하지 않습니다.")
            return null
        }
    }

    fun setRefreshTokenHeader(response: HttpServletResponse, refreshToken: String) {
        response.setHeader(refreshHeader, refreshToken)
    }

    fun setAccessTokenHeader(response: HttpServletResponse, accessToken: String) {
        response.setHeader(accessHeader, accessToken)
    }

    fun updateRefreshToken(email: String, refreshToken: String) {
        memberRepository.findByEmail(email)
            ?.updateRefreshToken(refreshToken)
            ?: throw Exception("일치하는 회원이 없습니다.")
    }

    fun isTokenValid(token: String): Boolean {
        return try {
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token)
            true
        } catch (e: Exception) {
            println("유효하지 않은 토큰입니다.")
            false
        }
    }
}