package com.practice.kotlinpractice.dto

data class MemberSignUpDto(
    val email: String,
    val password: String,
    val nickname: String,
    val age: Int,
    val city: String
)
