package com.study.springsecurityjwtkotlin.auth.repository

import com.study.springsecurityjwtkotlin.auth.dto.CustomUserDetails
import org.springframework.stereotype.Service

@Service
class FakeUserRepository {
    // TODO: DB 연동
    val users = listOf(
        "username1" to "password1",
        "username2" to "password2",
        "username3" to "password3"
    )

    fun getUserByUsername(username: String?): CustomUserDetails {
        return users.find { it.first == username }?.let {
            CustomUserDetails(
                username = it.first,
                password = it.second,
                email = it.first + "@naver.com",
                roles = listOf("ROLE_ADMIN", "ROLE_USER")
            )
        } ?: throw Exception("User Not Found")
    }
}
