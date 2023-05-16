package com.practice.kotlinpractice.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.practice.kotlinpractice.domain.MemberRepository
import com.practice.kotlinpractice.filter.jwt.JwtAuthenticationProcessingFilter
import com.practice.kotlinpractice.filter.login.CustomJsonUsernamePasswordAuthenticationFilter
import com.practice.kotlinpractice.handler.login.LoginFailureHandler
import com.practice.kotlinpractice.handler.login.LoginSuccessHandler
import com.practice.kotlinpractice.oauth2.handler.OAuth2LoginFailureHandler
import com.practice.kotlinpractice.oauth2.handler.OAuth2LoginSuccessHandler
import com.practice.kotlinpractice.oauth2.service.CustomOAuth2UserService
import com.practice.kotlinpractice.service.jwt.JwtService
import com.practice.kotlinpractice.service.login.LoginService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.LogoutFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val loginService: LoginService,
    private val jwtService: JwtService,
    private val memberRepository: MemberRepository,
    private val objectMapper: ObjectMapper,
    private val oAuth2LoginSuccessHandler: OAuth2LoginSuccessHandler,
    private val oAuth2LoginFailureHandler: OAuth2LoginFailureHandler,
    private val customOAuth2UserService: CustomOAuth2UserService
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .formLogin().disable()
            .httpBasic().disable()
            .csrf().disable()
            .headers().frameOptions().disable()
            .and()

            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()

            .authorizeHttpRequests()
            .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/favicon.ico", "h2-console/**").permitAll()
            .requestMatchers("/sign-up").permitAll()
            .anyRequest().authenticated()
            .and()

            .oauth2Login()
            .successHandler(oAuth2LoginSuccessHandler)
            .failureHandler(oAuth2LoginFailureHandler)
            .userInfoEndpoint().userService(customOAuth2UserService)

        http.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter::class.java)
        http.addFilterBefore(jwtAuthenticationProcessingFilter(), CustomJsonUsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun customJsonUsernamePasswordAuthenticationFilter(): CustomJsonUsernamePasswordAuthenticationFilter {
        val customJsonUsernamePasswordLoginFilter = CustomJsonUsernamePasswordAuthenticationFilter(objectMapper)
        customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager())
        customJsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler())
        customJsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler())
        return customJsonUsernamePasswordLoginFilter
    }

    @Bean
    fun jwtAuthenticationProcessingFilter(): JwtAuthenticationProcessingFilter {
        return JwtAuthenticationProcessingFilter(jwtService, memberRepository)
    }

    @Bean
    fun authenticationManager(): AuthenticationManager {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordEncoder())
        provider.setUserDetailsService(loginService)
        return ProviderManager(provider)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun loginSuccessHandler(): LoginSuccessHandler {
        return LoginSuccessHandler(jwtService,memberRepository)
    }

    @Bean
    fun loginFailureHandler(): LoginFailureHandler {
        return LoginFailureHandler()
    }

}