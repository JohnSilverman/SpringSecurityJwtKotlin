package com.study.springsecurityjwtkotlin.auth.controller

import com.study.springsecurityjwtkotlin.auth.dto.CustomUserDetails
import com.study.springsecurityjwtkotlin.auth.dto.SignInRequest
import com.study.springsecurityjwtkotlin.auth.dto.SignInResponse
import com.study.springsecurityjwtkotlin.auth.service.CustomUserDetailsService
import com.study.springsecurityjwtkotlin.auth.service.JwtUtilService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthenticationController(
    private val customUserDetailsService: CustomUserDetailsService,
    private val jwtUtilService: JwtUtilService
) {
    @PostMapping("/signin")
    fun signIn(@RequestBody signInRequest: SignInRequest): SignInResponse {
        val user = customUserDetailsService.loadUserByUsername(signInRequest.username) as CustomUserDetails
        if (signInRequest.password != user.password){
            return SignInResponse(success=false, token="")
        }
        val token = jwtUtilService.generateToken(user.username)
        return SignInResponse(success=true, token=token)
    }
}
