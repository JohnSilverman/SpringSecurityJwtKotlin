package com.study.springsecurityjwtkotlin.auth.controller

import com.study.springsecurityjwtkotlin.auth.dto.CustomUserDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SampleApiController {
    @GetMapping("/public/sample")
    fun unauthorizedApi(): String {
        return "This is public api"
    }

    @GetMapping("/sample")
    fun authorizedApi(): String {
        return "This is authorized api"
    }

    @GetMapping("/get-current-user")
    fun getCurrentUser(): CustomUserDetails {
        return SecurityContextHolder.getContext().authentication.principal as CustomUserDetails
    }
}
