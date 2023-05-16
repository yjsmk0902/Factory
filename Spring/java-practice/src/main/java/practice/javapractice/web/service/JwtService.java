package practice.javapractice.web.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import practice.javapractice.domain.repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationTime;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationTime;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    //JWT 헤더에 들어오는 값 => 'Authorization(Key) = Bearer {토큰} (Value)' 형식

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";

    //JWT의 Subject와 Claim으로 email 사용 => Claim의 이름을 "email"로 설정
    private static final String EMAIL_CLAIM = "email";
    private static final String BEARER = "Bearer ";

    private final MemberRepository memberRepository;

    //AccessToken 생성 메서드
    public String createAccessToken(String email) {
        Date now = new Date();
        return JWT.create() //JWT를 생성하는 빌더를 반환
                .withSubject(ACCESS_TOKEN_SUBJECT) //JWT의 Subject 지정
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationTime)) //토큰 만료 시간
                .withClaim(EMAIL_CLAIM, email)
                .sign(Algorithm.HMAC512(secretKey));
        //HMAC512 알고리즘 사용, application-jwt.yml에서 지정한 secret키로 암호화
    }

    //RefreshToken 생성 메서드
    //RefreshToken의 경우 Claim에 email도 넣지 않음 (오직 AccessToken 재발급 용도)
    public String createRefreshToken() {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationTime))
                .sign(Algorithm.HMAC512(secretKey));
    }

    //AccessToken 헤더에 실어 보내기
    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(accessHeader, accessToken);
        log.info("재발급된 AccessToken:{}", accessToken);
    }

    //AccessToken + RefreshToken 헤더에 실어 보내기
    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(accessHeader, accessToken);
        response.setHeader(refreshHeader, refreshToken);
        log.info("AccessToken, RefreshToken 헤더 설정 완료");
    }

    //헤더에서 RefreshToken 추출
    //  Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기
    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    //헤더에서 AccessToken 추출
    //  Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(accessToken -> accessToken.startsWith(BEARER))
                .map(accessToken -> accessToken.replace(BEARER, ""));
    }

    //AccessToken에서 Email 추출
    //  추출 전에 JWT.require()로 검증기 생성
    //  verify로 AccessToken 검증 후
    //  유효하다면 getClaim()으로 이메일 추출
    //  유효하지 않으면 빈 Optional 객체 반환
    public Optional<String> extractEmail(String accessToken) {
        try {
            //토큰 유효성 검사 알고리즘이 있는 JWT verifier builder 반환
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                    .build()    //반환된 빌더로 JWT verifier 생성
                    .verify(accessToken)    //accessToken을 검증하고 유효하지 않다면 예외 발생
                    .getClaim(EMAIL_CLAIM)  //claim(Email) 가져오기
                    .asString());
        } catch (Exception e) {
            log.error("AccessToken이 유효하지 않음...");
            return Optional.empty();
        }
    }

    //RefreshToken DB 저장(업데이트)
    public void updateRefreshToken(String email, String refreshToken) {
        memberRepository.findByEmail(email)
                .ifPresentOrElse(
                        member -> member.updateRefreshToken(refreshToken),
                        () -> new Exception("일치하는 회원이 없습니다.")
                );
    }

    //토큰의 유효성 검사
    public boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 토큰입니다...{}", e.getMessage());
            return false;
        }
    }
}
