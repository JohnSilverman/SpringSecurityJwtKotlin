package com.study.springsecurityjwtkotlin.auth.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtUtilService {
    private val keySource = "faisjeoigjowijoifjewofnnnvi39asefasefesfe" // put random long string here
    private val secretKey = Base64.getEncoder().encodeToString(keySource.toByteArray())

    private val tokenValidTime = 30 * 60 * 1000L // 30 minutes

    fun generateToken(subject: String): String {
        return Jwts
            .builder()
            .setSubject(subject)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + tokenValidTime))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    fun isTokenValid(jwtToken: String): Boolean {
        return try {
            val claims = Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwtToken)
            !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }

    fun extractSubject(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun getSignInKey(): Key {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}
