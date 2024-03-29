package lifestyle.dt.global.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import lifestyle.dt.global.security.auth.AuthDetailsService
import lifestyle.dt.global.security.exception.ExpiredTokenException
import lifestyle.dt.global.security.exception.InvalidTokenException
import lifestyle.dt.global.security.jwt.properties.JwtProperties
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import javax.servlet.http.HttpServletRequest

@Component
class JwtParser(
    private val authDetailsService: AuthDetailsService,
    private val jwtProperties: JwtProperties
) {

    fun authentication(token: String): Authentication {
        val userDetails = authDetailsService.loadUserByUsername(getTokenSubject(token, jwtProperties.accessSecret))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun resolveAccessToken(request: HttpServletRequest): String? =
        request.getHeader("Authorization")
            .let{ it ?: return null }
            .let { if(it.startsWith(JwtProperties.TOKEN_PREFIX)) it.replace(JwtProperties.TOKEN_PREFIX, "") else null }

    fun resolveRefreshToken(refreshToken: String): String? =
        if (refreshToken.startsWith(JwtProperties.TOKEN_PREFIX)) refreshToken.replace(JwtProperties.TOKEN_PREFIX, "") else null


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
        } catch (e: JwtException) {
            throw InvalidTokenException()
        }
    }
}