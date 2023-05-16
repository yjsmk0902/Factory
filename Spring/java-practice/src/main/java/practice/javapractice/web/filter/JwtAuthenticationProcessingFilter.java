package practice.javapractice.web.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import practice.javapractice.domain.entity.Member;
import practice.javapractice.domain.repository.MemberRepository;
import practice.javapractice.web.service.JwtService;
import practice.javapractice.web.util.PasswordUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//헤더에 JWT를 담아서 "/login" URL 이외의 요청을 보낼 때,
//해당 토큰들의 유효성을 검사하여 인증 처리 / 인증 실패 / 토큰 재발급 등을 수행하는 필터
//기본적으로 사용자는 header에 AccessToken만 담아서 요청
//AccessToken 만료 시에만 RefreshToken을 헤더에 담아 AccessToken과 함께 요청

//  담겨 온 AccessToken이 유효할 경우 => 인증 성공
//  담겨 온 AccessToken이 유효하지 않을 경우 => 인증 실패
//  담겨 온 RefreshToken이 있는 경우 => DB의 RefreshToken과 비교 일치 시 재발급, 인증은 실패 처리

//+ JWT 인증 로직 - AccessToken 만료 전 / AccessToken 만료 후로 구분
//  만료 전)
//      1. 클라이언트가 서버에 이메일/비밀번호를 담아 로그인 요청
//      2. 요청 받은 이메일/비밀번호를 DB에서 찾고,
//      유저가 존재하면 AccessToken과 RefreshToken을 생성하여 Response에 담아 반환 (RefreshToken은 DB에 저장)
//      3. 이후 클라이언트는 요청 시마다 AccessToken을 담아 API를 요청
//      4. 서버에서 요청받은 AccessToken을 검증하여 인증 처리
//  만료 후)
//      1. 클라이언트에서 AccessToken의 만료를 판단, RefreshToken만 담아 요청
//      2. 서버에서 요청받은 RefreshToken이 DB의 토큰과 일치하는 지 확인 후,
//          일치하는 경우 Access/RefreshToken을 재발급하여 Response에 담아 보냄
//          재발급한 RefreshToken으로 DB의 RefreshToken을 업데이트 (RTR)
//      3. 클라이언트는 재발급받은 AccessToken을 요청에 담아 API를 요청
//      4. 서버에서 요청받은 AccessToken을 검증하여 인증 처리

//  +RTR이란? => Refresh Token Rotation의 약자로 RefreshToken이 단 한번만 사용되게 만드는 방법
//      RefreshToken을 사용하여 만료된 AccessToken을 재발급 받을 때, RefreshToken도 재발급하는 방법
//      이유는 RefreshToken이 탈취될 시 AccessToken을 계속 생성할 수 있기 때문에 위험 (더군다나 RefreshToken은 만료기간도 김)
//      따라서 재발급 과정에서 RefreshToken도 같이 재발급하여 만료기간을 줄이는 방법임

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private static final String NO_CHECK_URL = "/login";    //"/login"으로 들어오는 요청은 Filter 처리 안함

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals(NO_CHECK_URL)) {
            filterChain.doFilter(request, response);
            //"/login" 요청이 들어오면, 다음 필터 호출
            return;     //필터 종료
        }

        //사용자 요청 헤더에서 RefreshToken 호출
        //  -> RefreshToken이 없거나 DB에 저장된 RefreshToken과 다르면 null을 반환
        //  RefreshToken이 있는 경우 AccessToken이 만료되어 요청한 경우밖에 없다. 결국 나머지는 전부 null
        String refreshToken = jwtService.extractRefreshToken(request)
                .filter(jwtService::isTokenValid)
                .orElse(null);

        //RefreshToken이 요청에 존재했다면, 사용자의 RefreshToken이 만료된 것이므로
        //보내진 토큰이 DB의 RefreshToken과 일치하는지 판단하고,
        //일치하면 AccessToken을 재발급해준다.
        if (refreshToken != null) {
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
            return;
        }

        //RefreshToken이 없거나 유효하지 않으면, AccessToken을 검사하고 인증 수행
        //AccessToken이 없거나 유효하지 않다면, 인증 객체가 담기지 않은 상태로 다음 필터로 넘어가기 때문에 에러 발생
        //AccessToken이 유효하면, 인증 객체가 담긴 상태로 다음 필터로 넘어가므로 인증 성공
        if (refreshToken == null) {
            checkAccessTokenAndAuthentication(request, response, filterChain);
        }

    }

    //RefreshToken으로 유저 정보찾기 & Access/RefreshToken 재발급
    private void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        memberRepository.findByRefreshToken(refreshToken)
                .ifPresent(member -> {
                    String reIssuedRefreshToken = reIssueRefreshToken(member);
                    jwtService.sendAccessAndRefreshToken(
                            response, jwtService.createAccessToken(member.getEmail()), reIssuedRefreshToken);
                });
    }

    //RefreshToken 재발급 & DB에 RefreshToken 업데이트
    //  jwtService.createRefreshToken()으로 RefreshToken 재발급
    //  DB에 재발급한 RefreshToken 업데이트 후 Flush
    private String reIssueRefreshToken(Member member) {
        String reIssuedRefreshToken = jwtService.createRefreshToken();
        member.updateRefreshToken(reIssuedRefreshToken);
        memberRepository.saveAndFlush(member);
        return reIssuedRefreshToken;
    }

    //AccessToken 체크 및 인증 처리
    private void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("checkAccessTokenAndAuthentication() 호출");
        jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .ifPresent(accessToken -> jwtService.extractEmail(accessToken)
                        .ifPresent(email -> memberRepository.findByEmail(email)
                                .ifPresent(this::saveAuthentication)));
        filterChain.doFilter(request, response);
    }

    //인증 허가
    private void saveAuthentication(Member member) {
        String password = member.getPassword();

        //소셜 로그인 유저의 비밀번호를 임의로 설정하여 소셜로그인 유저도 인증되도록 설정
        if (password == null) {
            password = PasswordUtil.getRamdomPassword();
        }

        UserDetails userDetailsUser = User.builder()
                .username(member.getEmail())
                .password(password)
                .roles(member.getRole().name())
                .build();

        //UsernamePasswordAuthenticationToken의 파라미터
        //  1. UserDetails 객체 (유저 정보)
        //  2. credential (비밀번호로, 인증시에는 보통 null로 제거)
        //  3. Collection< ? extends GrantedAuthority> 로,
        //      UserDetails의 User 객체 안에 Set<GrantedAuthority> authorities가 있어서
        //      getter를 호출한 후 new NullAuthoritiesMapper()로 GrantedAuthoritiesMapper 객체를 생성하고
        //      mapAuthorities()에 담기
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        //SecurityContextHolder.getContext()로 SecuriryContext를 꺼낸 후.
        //setAuthentication()을 이용하여 위에서 만든 Authentication 객체에 대한 인증 허가
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
