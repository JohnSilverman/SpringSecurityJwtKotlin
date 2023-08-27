package com.study.springsecurityjwtkotlin.auth.dto

data class SignUpRequest(
    val username: String,
    val password: String,
    val email: String
)
