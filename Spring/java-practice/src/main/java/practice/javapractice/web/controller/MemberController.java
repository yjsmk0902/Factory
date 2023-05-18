package practice.javapractice.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import practice.javapractice.domain.dto.MemberLoginDto;
import practice.javapractice.domain.dto.MemberSignUpDto;
import practice.javapractice.domain.dto.ResponseDto;
import practice.javapractice.domain.entity.Member;
import practice.javapractice.web.service.MemberService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    //회원가입 요청 시 자체 회원가입
    @PostMapping("/auth/sign-up")
    public ResponseEntity<ResponseDto> signUp(@RequestBody MemberSignUpDto memberSignUpDto) throws Exception {
        memberService.signUp(memberSignUpDto);
        return ResponseEntity.ok().body(
                new ResponseDto(
                        HttpStatus.OK.value(),
                        true,
                        "Sign Up Success"
                )
        );
    }

    //로그인 API -> CustomJsonUsernamePasswordAuthenticationFilter가 위임
//    @PostMapping("/auth/login")
//    public ResponseEntity<ResponseDto> login(@RequestBody MemberLoginDto memberLoginDto) {
//        memberService.login(memberLoginDto);
//        return ResponseEntity.ok().body(
//                new ResponseDto(
//                        HttpStatus.OK,
//                        true,
//                        "Login Success"
//                )
//        );
//    }

    //JWT-Test
    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "JWT - TEST Success!!";
    }
}
