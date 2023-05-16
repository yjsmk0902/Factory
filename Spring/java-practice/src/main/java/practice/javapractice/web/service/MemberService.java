package practice.javapractice.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.javapractice.domain.dto.MemberSignUpDto;
import practice.javapractice.domain.entity.Member;
import practice.javapractice.domain.entity.Role;
import practice.javapractice.domain.repository.MemberRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //자체 회원가입 시 사용하는 회원가입 API

    public void signUp(MemberSignUpDto memberSignUpDto) throws Exception {
        if (memberRepository.findByEmail(memberSignUpDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }
        if (memberRepository.findByUsername(memberSignUpDto.getUsername()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }
        Member member = Member.builder()
                .email(memberSignUpDto.getEmail())
                .password(memberSignUpDto.getPassword())
                .username(memberSignUpDto.getUsername())
                .name(memberSignUpDto.getName())
                //자체 로그인이기 때문에 추가 정보까지 다 받아서 로그인
                .age(memberSignUpDto.getAge())
                .city(memberSignUpDto.getCity())
                .role(Role.USER)
                .build();   //builder로 엔티티 생성 후

        member.passwordEncode(passwordEncoder);
        memberRepository.save(member);  //DB에 저장
    }
}
