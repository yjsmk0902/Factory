package com.practice.kotlinpractice.service.security

import com.practice.kotlinpractice.domain.member.MemberRepository
import com.practice.kotlinpractice.dto.JwtDto
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component
import java.security.Key
import java.security.spec.KeySpec
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

}