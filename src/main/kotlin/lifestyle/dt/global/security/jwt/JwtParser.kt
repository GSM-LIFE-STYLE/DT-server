package lifestyle.dt.global.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import lifestyle.dt.global.security.auth.AuthDetailsService
import lifestyle.dt.global.security.exception.ExpiredTokenException
import lifestyle.dt.global.security.exception.InvalidTokenException
import lifestyle.dt.global.security.jwt.properties.JwtProperties
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import java.security.Key
import javax.servlet.http.HttpServletRequest

class JwtParser(
    private val authDetailsService: AuthDetailsService,
    private val jwtProperties: JwtProperties,
    private val jwtGenerator: JwtGenerator
) {

    fun authentication(token: String): Authentication {
        val userDetails = authDetailsService.loadUserByUsername(getTokenSubject(token, jwtProperties.accessSecret))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun exactEmailFromRefreshToken(refresh: String): String {
        return getTokenSubject(refresh, jwtProperties.refreshSecret)
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val token = request.getHeader("Authorization") ?: return null
        return jwtGenerator.parseToken(token)
    }

    private fun getTokenSubject(token: String, secret: Key): String =
        getTokenBody(token, secret).subject

    private fun getTokenBody(token: String, secret: Key): Claims {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: ExpiredJwtException) {
            throw ExpiredTokenException()
        } catch (e: Exception) {
            throw InvalidTokenException()
        }
    }
}