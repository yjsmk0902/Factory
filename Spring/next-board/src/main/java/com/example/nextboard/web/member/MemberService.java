package com.example.nextboard.web.member;

import com.example.nextboard.domain.member.dto.response.MemberResponseDto;
import com.example.nextboard.domain.member.entity.Member;
import com.example.nextboard.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    //회원 뷰 로직
    public MemberResponseDto viewMember(Long memberId) {
        return MemberResponseDto.toDto(findMember(memberId));
    }

    //회원 조회 로직(id)
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(RuntimeException::new);  //예외 처리 필요
    }

    //회원 조회 로직(name)
    public Member findMember(String name) {
        return memberRepository.findByName(name).orElseThrow(RuntimeException::new);
    }
}
