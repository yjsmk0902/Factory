package com.practice.kotlinpractice.config

import com.practice.kotlinpractice.service.security.CustomUserDetailService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig (
    private val customUserDetailService: CustomUserDetailService
){

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf{
                it.disable()
            }
            .httpBasic{
                it.disable()
            }
            .authorizeHttpRequests {
                it.requestMatchers("/guest").hasRole("USER")
                it.requestMatchers("/admin").hasRole("ADMIN")
            }
            .sessionManagement{
                it.sessionCreationPolicy(SessionCreationPolicy.NEVER)
            }
            .oauth2Login{
                it.userInfoEndpoint().userService(customUserDetailService)
                it.defaultSuccessUrl("/auth/login")
                it.failureUrl("/fail")
            }
        return http.build()
    }
}