package lifestyle.dt.global.security.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import lifestyle.dt.domain.user.domain.RefreshToken
import lifestyle.dt.domain.user.domain.repository.RefreshTokenRepository
import lifestyle.dt.domain.user.presentation.data.dto.TokenDto
import lifestyle.dt.global.security.jwt.properties.JwtExpTimeProperties
import lifestyle.dt.global.security.jwt.properties.JwtProperties
import org.springframework.stereotype.Component
import java.security.Key
import java.time.ZonedDateTime
import java.util.*

@Component
class JwtGenerator(
    private val jwtProperties: JwtProperties,
    private val jwtExpTimeProperties: JwtExpTimeProperties,
    private val refreshTokenRepository: RefreshTokenRepository
) {

    fun generate(userId: UUID): TokenDto = TokenDto(
            accessToken = generateAccessToken(userId),
            refreshToken = generateRefreshToken(userId),
            accessTokenExpiredAt = accessExpiredTime,
            refreshTokenExpiredAt = refreshExpiredTime
        )

    val accessExpiredTime: ZonedDateTime
        get() = ZonedDateTime.now().plusSeconds(jwtExpTimeProperties.accessExp.toLong())

    val refreshExpiredTime: ZonedDateTime
        get() = ZonedDateTime.now().plusSeconds(jwtExpTimeProperties.refreshExp.toLong())

    fun generateAccessToken(userId: UUID): String =
        generateToken(userId, JwtProperties.ACCESS_TYPE, jwtProperties.accessSecret, jwtExpTimeProperties.accessExp)

    fun generateRefreshToken(userId: UUID): String {
        val refreshToken = generateToken(userId, JwtProperties.REFRESH_TYPE, jwtProperties.refreshSecret, jwtExpTimeProperties.refreshExp)
        refreshTokenRepository.save(RefreshToken(refreshToken, userId, jwtExpTimeProperties.refreshExp))
        return refreshToken
    }

    fun generateToken(sub: UUID, type: String, secret: Key, exp: Int): String =
        Jwts.builder()
            .signWith(secret, SignatureAlgorithm.HS256)
            .setSubject(sub.toString())
            .claim("type", type)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + exp * 1000))
            .compact()

}