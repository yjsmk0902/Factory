package practice.javapractice.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import practice.javapractice.domain.dto.MemberSignUpDto;
import practice.javapractice.web.service.MemberService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    //해당 URI로 회원가입 요청 시 자체 회원가입
    @PostMapping("/sign-up")
    public String signUp(@RequestBody MemberSignUpDto memberSignUpDto) throws Exception {
        memberService.signUp(memberSignUpDto);
        return "Sign-Up Complete!!";
    }

    //JWT-Test
    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "JWT - TEST Success!!";
    }
}
