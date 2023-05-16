package com.practice.kotlinpractice.controller

import com.practice.kotlinpractice.dto.MemberSignUpDto
import com.practice.kotlinpractice.service.member.MemberService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping("/sign-up")
    fun signUp(@RequestBody memberSignUpDto: MemberSignUpDto): String {
        memberService.signUp(memberSignUpDto)
        return "Signup Success!"
    }

    @GetMapping("/jwt-test")
    fun jwtTest(): String {
        return "jwtTest Request Success!"
    }
}