package com.group.libraryapp.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional
import javax.swing.text.html.Option

interface UserRepository : JpaRepository<User, Long> {
    fun findByName(name: String): User?
}