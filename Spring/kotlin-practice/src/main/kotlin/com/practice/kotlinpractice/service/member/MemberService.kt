package com.practice.kotlinpractice.service.member

import com.practice.kotlinpractice.domain.member.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService (
    private val memberRepository: MemberRepository,
){


}