package lifestyle.dt.global.security.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import lifestyle.dt.global.security.jwt.properties.JwtExpTimeProperties
import lifestyle.dt.global.security.jwt.properties.JwtProperties
import org.springframework.stereotype.Component
import java.security.Key
import java.time.ZonedDateTime
import java.util.*

@Component
class JwtGenerator(
    private val jwtProperties: JwtProperties,
    private val jwtExpTimeProperties: JwtExpTimeProperties
) {

    val accessExpiredTime: ZonedDateTime
        get() = ZonedDateTime.now().plusSeconds(jwtExpTimeProperties.accessExp.toLong())

    fun generateAccessToken(email: String): String =
        generateToken(email, JwtProperties.ACCESS_TYPE, jwtProperties.accessSecret, jwtExpTimeProperties.accessExp)

    fun generateRefreshToken(email: String): String =
        generateToken(email, JwtProperties.REFRESH_TYPE, jwtProperties.refreshSecret, jwtExpTimeProperties.refreshExp)

    fun generateToken(sub: String, type: String, secret: Key, exp: Int): String =
        Jwts.builder()
            .signWith(secret, SignatureAlgorithm.HS256)
            .setSubject(sub)
            .claim("type", type)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + exp * 1000))
            .compact()

}