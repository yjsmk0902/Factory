package com.practice.kotlinpractice.dto.Member

data class MemberSignUpRequest(
    val email: String,
    val password: String,
    val name: String,
    val age: Int
)
