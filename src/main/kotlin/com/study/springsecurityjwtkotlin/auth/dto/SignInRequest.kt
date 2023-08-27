package com.study.springsecurityjwtkotlin.auth.dto

data class SignInRequest(
    val username: String,
    val password: String
)
