package practice.javapractice.web.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import practice.javapractice.domain.entity.Role;
import practice.javapractice.domain.repository.MemberRepository;
import practice.javapractice.web.oauth2.CustomOAuth2User;
import practice.javapractice.web.service.JwtService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("OAuth Login Success!");
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            //User의 Role이 GUEST일 경우 처음 요청한 회원이므로 회원가입 페이지로 redirect
            if (oAuth2User.getRole() == Role.GUEST) {
                String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
                response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
                response.sendRedirect("oauth2/sign-up");    //프론트의 회원가입 추가 정보 입력 폼으로 redirect

                jwtService.sendAccessAndRefreshToken(response, accessToken, null);

                //Role을 GUEST -> USER로 업데이트시키는 로직
                //  회원가입 추가 폼 입력 시 업데이트시키는 컨트롤러를 만든 후 Role 업데이트 진행
//                Member findMember = memberRepository.findByEmail(oAuth2User.getEmail())
//                        .orElseThrow(() -> new IllegalArgumentException("해당 이메일을 가진 유저가 없습니다."));
//                findMember.authorizeUser();

            } else {
                //이미 로그인을 한번 이상 했던 유저 => 토큰만 발급
                loginSucess(response, oAuth2User); //로그인에 성공한 경우 access, refresh 토큰 생성
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void loginSucess(HttpServletResponse response, CustomOAuth2User oAuth2User) {

        String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
        String refreshToken = jwtService.createRefreshToken();

        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
        response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);
    }
}
