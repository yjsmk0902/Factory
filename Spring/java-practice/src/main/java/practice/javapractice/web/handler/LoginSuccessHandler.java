package practice.javapractice.web.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import practice.javapractice.domain.dto.ResponseDto;
import practice.javapractice.domain.dto.TokenDto;
import practice.javapractice.domain.repository.MemberRepository;
import practice.javapractice.web.service.JwtService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final ObjectMapper mapper;

    @Value("${jwt.access.expiration}")
    private String accessTokenExpiration;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication){
        String email = extractUsername(authentication);
        String accessToken = jwtService.createAccessToken(email);
        String refreshToken = jwtService.createRefreshToken();

        //response에 accessToken, refreshToken 담기
        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        memberRepository.findByEmail(email)
                .ifPresent(member ->{
                    member.updateRefreshToken(refreshToken);    //업데이트
                    memberRepository.saveAndFlush(member);      //DB에 반영
                });

        //로그인시 토큰을 각각 바디에 넣어서 전달
        TokenDto token = new TokenDto(accessToken, refreshToken);
        ResponseDto responseDto = new ResponseDto(
                HttpStatus.OK,
                true,
                "Login Success",
                token
        );
        try {
            response.getWriter().write(mapper.writeValueAsString(responseDto));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("로그인에 성공하였습니다. 이메일: {}", email);
        log.info("로그인에 성공하였습니다. AccessToken: {}", accessToken);
        log.info("발급된 AccessToken 만료 기간: {}", accessTokenExpiration);
    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
