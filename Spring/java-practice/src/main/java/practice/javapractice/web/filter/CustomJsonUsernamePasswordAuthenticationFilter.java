package practice.javapractice.web.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CustomJsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String DEFAULT_LOGIN_REQUEST_URL = "/auth/login";       //"/login"으로 오는 요청 처리
    private static final String HTTP_METHOD = "POST";                       //로그인 HTTP 메소드는 POST
    private static final String CONTENT_TYPE = "application/json";           //JSON 타입의 데이터로 오는 요청만 처리
    private static final String USERNAME_KEY = "email";                     //회원 로그인 시 이메일 요청 JSON Key = "email"
    private static final String PASSWORD_KEY = "password";                  //회원 로그인 시 비밀번호 요청 JSON Key = "password"
    private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URL, HTTP_METHOD);  //"/auth/login" + POST 요청 매칭

    private ObjectMapper objectMapper;

    public CustomJsonUsernamePasswordAuthenticationFilter(ObjectMapper objectMapper) {
        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER);  //위에서 설정한 요청을 처리하기 위해 설정
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        //"application/json이 아니면 예외 발생
        if (request.getContentType() == null || !request.getContentType().equals(CONTENT_TYPE)) {
            throw new AuthenticationServiceException("Authentication Content-Type not supported" + request.getContentType());
        }

        //StreamUtils를 통해 요청 messageBody(JSON) 반환
        String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);

        //objectMapper.readValue()로 Map으로 변환 ("email", "password")
        Map<String, String> usernamePasswordMap = objectMapper.readValue(messageBody, Map.class);

        //messageBody에서 이메일과 비밀번호 추출
        String email = usernamePasswordMap.get(USERNAME_KEY);
        String password = usernamePasswordMap.get(PASSWORD_KEY);

        //principal과 credentials 전달
        //UsernamePasswordAuthenticationFilter와 동일하게 UsernamePasswordAuthenticationToken 사용
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);

        //getAuthenticationManager()로 AuthenticationManager객체를 반환받고
        //authenticate()의 파라미터로 UsernamePasswordAuthenticationToken 객체를 넣고 인증 처리
        //AuthenticationManager 객체 ProviderManager -> SecurityConfig에서 설정
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
