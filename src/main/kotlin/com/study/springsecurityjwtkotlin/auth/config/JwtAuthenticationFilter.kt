package com.study.springsecurityjwtkotlin.auth.config

import com.study.springsecurityjwtkotlin.auth.service.CustomUserDetailsService
import com.study.springsecurityjwtkotlin.auth.service.JwtUtilService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtUtilService: JwtUtilService,
    private val customUserDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // 1. retrieve token from header
        val authHeader : String? = request.getHeader("Authorization")
        val token = authHeader?.replace("Bearer ", "") ?: ""

        // 2. validate token
        if (jwtUtilService.isTokenValid(token)) {
            // 3. retrieve user info from token
            val username = jwtUtilService.extractSubject(token)

            // 4. retrieve user info from db
            val userDetails = customUserDetailsService.loadUserByUsername(username)

            // 5. set authentication to security context
            val authToken = UsernamePasswordAuthenticationToken( // implements Authentication
                userDetails, //principal
                null, // credentials
                userDetails.authorities // authorities
            )
            authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authToken

            // SecurityContextHolder.getContext().authentication.principal => get current authenticated user
        }

        // 6. pass to next filter
        filterChain.doFilter(request, response)
    }

}
