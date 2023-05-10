package com.practice.kotlinpractice.domain.board

import com.practice.kotlinpractice.domain.member.Member
import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY

@Entity
class Board(
    @ManyToOne
    val member: Member,

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private val id: Long? = null
) {
}