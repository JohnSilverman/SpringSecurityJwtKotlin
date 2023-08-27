package com.study.springsecurityjwtkotlin.auth.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

// TODO: DB 연동
class CustomUserDetails (
    private val username: String,
    private val password: String,
    private val email: String,
    private val roles: List<String>
        )
    : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return roles.map { role ->
            GrantedAuthority { role }
        }.toMutableList()
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
