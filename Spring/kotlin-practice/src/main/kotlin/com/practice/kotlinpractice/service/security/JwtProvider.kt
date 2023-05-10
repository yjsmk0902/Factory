package com.practice.kotlinpractice.service.security

import com.nimbusds.oauth2.sdk.token.AccessTokenType
import com.practice.kotlinpractice.domain.member.MemberRepository
import com.practice.kotlinpractice.dto.JwtDto
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.lang.IllegalArgumentException
import java.security.Key
import java.util.Date

@Component
class JwtProvider (
    private val memberRepository: MemberRepository
){
    companion object {
        private const val AUTHORITIES_KEY = "auth"
        private const val BEARER_TYPE = "bearer"
        private const val ACCESS_TOKEN_EXPIRE_TIME = (1000 * 60 * 30)
        private const val REFRESH_TOKEN_EXPIRE_TIME = (1000 * 60 * 30 * 24 * 7)
    }

    private val key: Key by lazy {
        val secretKey = "ZVc3Z0g4bm5TVzRQUDJxUXBIOGRBUGtjRVg2WDl0dzVYVkMyWWs1Qlk3NkZBOXh1UzNoRWUzeTd6cVdEa0x2eQo="
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
    }

    fun generateJwt(oAuth2User: OAuth2User): JwtDto {
        val now = Date().time
        val accessTokenExpiresIn = Date(now + ACCESS_TOKEN_EXPIRE_TIME)

        val member = memberRepository.findByEmail(oAuth2User.attributes["email"] as String)

        val accessToken = Jwts.builder()
            .setSubject(oAuth2User.attributes["email"] as String)   //payload "sub" => "email
            .claim(AUTHORITIES_KEY, member?.role)   //payload "auth"
            .setExpiration(accessTokenExpiresIn)    //payload "exp"
            .signWith(key, SignatureAlgorithm.HS512)//header "alg"
            .compact()

        val refreshToken = Jwts.builder()
            .setExpiration(Date(now + REFRESH_TOKEN_EXPIRE_TIME))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()

        return JwtDto(
            grantType = BEARER_TYPE,
            accessToken = accessToken,
            refreshToken = refreshToken,
            accessTokenExpiresIn = accessTokenExpiresIn.time
        )
    }

    fun resolveToken(request: HttpServletRequest): String {
        val bearerToken = request.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION)



        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(AccessTokenType.BEARER.value)) {
            return bearerToken.substring(7)
        }
        return ""
    }

    fun validateAccessToken(token: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            return true
        } catch (e: SecurityException) {
            println("올바르지 못한 토큰입니다.")
        } catch (e: MalformedJwtException) {
            println("올바르지 못한 토큰입니다.")
        } catch (e: ExpiredJwtException) {
            println("만료된 토큰입니다.")
        } catch (e: UnsupportedJwtException) {
            println("지원되지 않는 토큰입니다.")
        } catch (e: IllegalArgumentException) {
            println("잘못된 토큰입니다.")
        }
        return true
    }

    fun findAuthentication(accessToken: String): Authentication {
        val claims = parseClaims(accessToken)

        val authorities = mutableListOf(claims[AUTHORITIES_KEY] as String)
            .map{ role -> SimpleGrantedAuthority(role)}
        val user = User(claims[Claims.SUBJECT] as String, "",authorities)

        return UsernamePasswordAuthenticationToken(user, "", authorities)

    }

    private fun parseClaims(accessToken: String): Claims {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).body
        } catch (e: ExpiredJwtException) {
            e.claims
        }
    }

}