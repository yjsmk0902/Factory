package practice.javapractice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import practice.javapractice.domain.repository.MemberRepository;
import practice.javapractice.web.filter.CustomJsonUsernamePasswordAuthenticationFilter;
import practice.javapractice.web.filter.JwtAuthenticationProcessingFilter;
import practice.javapractice.web.handler.LoginFailureHandler;
import practice.javapractice.web.handler.LoginSuccessHandler;
import practice.javapractice.web.handler.OAuth2LoginFailureHandler;
import practice.javapractice.web.handler.OAuth2LoginSuccessHandler;
import practice.javapractice.web.service.CustomOAuth2UserService;
import practice.javapractice.web.service.JwtService;
import practice.javapractice.web.service.LoginService;


@Configuration
@EnableWebSecurity  //Spring Secuirty 관련 클래스들 import -> Spring Secuiry 기능을 사용하기 위해 붙여줘야함
@RequiredArgsConstructor
public class SecurityConfig {

    private final LoginService loginService;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //기타 설정
                .formLogin().disable()  //FormLogin 사용 안함 (자체 폼 제공할 것)
                .httpBasic().disable()  //httpBasic 사용 안함 (JWT를 사용해서 로그인 할 것)
                .csrf().disable()   //csrf 보안 사용 안함 (서버에 인증 정보를 저장하지 않고, 요청 시 인증 정보를 담아서 요청할 것)
                //Defualt = X-Frame-Options Click jacking에 대한 공격 막기 설정이 되어있지만
                //h2-console에 접근하기 위해 사용 안함
                .headers().frameOptions().disable()
                .and()
                //세션을 사용하지 않음
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                //URL별 권한 관리 옵션
                .authorizeHttpRequests()    //인증/인가 설정시 HttpServletRequest를 이용
                //인증 절차 없이 접근할 URL 설정
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/favicon.ico", "/h2-console/**", "/sign-up").permitAll()
                //나머지는 다 인증 필요
                .anyRequest().authenticated()
                .and()

                //소셜 로그인 설정
                .oauth2Login()  //OAuth2 로그인에 관한 기능 사용 (OAuth2LoginConfigurer 반환)
                .successHandler(oAuth2LoginSuccessHandler)  //커스텀한 successHandler 사용
                .failureHandler(oAuth2LoginFailureHandler)  //커스텀한 failureHandler 사용
                .userInfoEndpoint().userService(customOAuth2UserService);   //커스텀한 userService 사용

        //필터 동작 설정
        //Default => Spring Security Filter 순서가 LogoutFilter 이후에 로그인 필터 동작
        //따라서, LogoutFilter 이후에 우리가 만든 필터가 동작하도록 설정
        //LogoutFilter -> JwtAuthenticationProcessingFilter -> CustomJsonUsernamePasswordAuthenticationFilter
        http.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationProcessingFilter(), CustomJsonUsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean   //Provider에서 설정할 passwordEncoder 빈 등록
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean   //커스텀 필터 빈 등록에서 설정할 AuthenticationManager 빈 등록
    public AuthenticationManager authenticationManager() {
        //기존 Spring Security에서 사용하는 DaoAuthenticationProvider를 사용하여
        //AuthenticationManger를 생성하여 반환
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(passwordEncoder()); //PasswordEncoder 설정
        provider.setUserDetailsService(loginService);   //LoginService 설정
        return new ProviderManager(provider);           //반환
    }

    //커스텀 JSON 필터 빈 등록을 위한 Success/Failure 핸들러 빈 등록
    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(jwtService, memberRepository);
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    @Bean   //커스텀 JSON 필터 빈 등록
    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() {
        CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter
                = new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper);
        customJsonUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
        customJsonUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        customJsonUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(loginFailureHandler());

        return customJsonUsernamePasswordAuthenticationFilter;
    }

    @Bean   //JWT 인증 필터 빈 등록
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {

        return new JwtAuthenticationProcessingFilter(jwtService, memberRepository);
    }
}
