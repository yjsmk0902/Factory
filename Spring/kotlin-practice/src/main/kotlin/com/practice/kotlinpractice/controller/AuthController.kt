package com.practice.kotlinpractice.controller

import com.practice.kotlinpractice.dto.JwtDto
import com.practice.kotlinpractice.service.auth.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService
) {

    @GetMapping("/auth/login")
    fun login(@AuthenticationPrincipal oAuth2User: OAuth2User): ResponseEntity<JwtDto> {
        return ResponseEntity.ok(authService.login(oAuth2User))
    }

}