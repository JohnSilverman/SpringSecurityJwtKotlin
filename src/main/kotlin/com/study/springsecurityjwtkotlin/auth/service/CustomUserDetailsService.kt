package com.study.springsecurityjwtkotlin.auth.service

import com.study.springsecurityjwtkotlin.auth.repository.FakeUserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val fakeUserRepository: FakeUserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        return fakeUserRepository.getUserByUsername(username)
    }

}
