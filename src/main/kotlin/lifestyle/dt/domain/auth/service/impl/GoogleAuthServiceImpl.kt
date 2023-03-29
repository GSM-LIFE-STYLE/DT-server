package lifestyle.dt.domain.auth.service.impl

import lifestyle.dt.domain.auth.presentation.data.dto.TokenDto
import lifestyle.dt.domain.auth.service.GoogleAuthService
import lifestyle.dt.domain.auth.domain.RefreshToken
import lifestyle.dt.domain.auth.domain.repository.RefreshTokenRepository
import lifestyle.dt.domain.user.domain.repository.UserRepository
import lifestyle.dt.domain.user.util.UserConverter
import lifestyle.dt.global.security.GoogleAuthProperties
import lifestyle.dt.global.security.jwt.JwtGenerator
import lifestyle.dt.global.security.jwt.JwtParser
import lifestyle.dt.infrastructure.feign.client.GoogleAuth
import lifestyle.dt.infrastructure.feign.client.GoogleInfo
import lifestyle.dt.infrastructure.feign.dto.request.GoogleCodeRequest
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class GoogleAuthServiceImpl(
    private val googleAuth: GoogleAuth,
    private val googleInfo: GoogleInfo,
    private val googleAuthProperties: GoogleAuthProperties,
    private val userRepository: UserRepository,
    private val userConverter: UserConverter,
    private val jwtGenerator: JwtGenerator,
    private val jwtParser: JwtParser,
    private val refreshTokenRepository: RefreshTokenRepository
): GoogleAuthService {

    private fun createUser(email: String, name: String, picture: String) =
        userRepository.findByEmail(email) ?: userRepository.save(userConverter.toEntity(email, name, picture))


    override fun execute(code: String): TokenDto {
        val accessToken = googleAuth.googleAuth(
            GoogleCodeRequest(
                code = URLDecoder.decode(code, StandardCharsets.UTF_8),
                clientId = googleAuthProperties.clientId,
                clientSecret = googleAuthProperties.clientSecret,
                redirectUri = googleAuthProperties.redirectUri
            )
        ).accessToken

        val (email, name, picture) = googleInfo.googleInfo(accessToken)

        val user = createUser(email, name, URLDecoder.decode(picture, StandardCharsets.UTF_8))

        return jwtGenerator.generate(user.id)
    }
}