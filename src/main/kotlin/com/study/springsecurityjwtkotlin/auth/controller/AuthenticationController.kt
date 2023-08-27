package com.study.springsecurityjwtkotlin.auth.controller

import com.study.springsecurityjwtkotlin.auth.dto.SignInRequest
import com.study.springsecurityjwtkotlin.auth.dto.SignInResponse
import com.study.springsecurityjwtkotlin.auth.dto.SignUpRequest
import com.study.springsecurityjwtkotlin.auth.dto.SignUpResponse
import com.study.springsecurityjwtkotlin.auth.repository.FakeUserRepository
import com.study.springsecurityjwtkotlin.auth.service.JwtUtilService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthenticationController(
    private val fakeUserRepository: FakeUserRepository,
    private val jwtUtilService: JwtUtilService
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody signUpRequest: SignUpRequest): SignUpResponse {
        TODO("DB 연동")
    }

    @PostMapping("/signin")
    fun signIn(@RequestBody signInRequest: SignInRequest): SignInResponse {
        val user = fakeUserRepository.getUserByUsername(signInRequest.username)
        if (signInRequest.password != user.password){
            return SignInResponse(success=false, token="")
        }
        val token = jwtUtilService.generateToken(user.username)
        return SignInResponse(success=true, token=token)
    }
}
