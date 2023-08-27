package com.study.springsecurityjwtkotlin.auth.dto

data class SignInResponse(
    val success: Boolean,
    val token: String
)
