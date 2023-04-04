package lifestyle.dt.domain.auth.service.impl

import lifestyle.dt.domain.auth.presentation.data.dto.TokenDto
import lifestyle.dt.domain.auth.service.GoogleAuthService
import lifestyle.dt.domain.user.domain.repository.UserRepository
import lifestyle.dt.domain.user.util.UserConverter
import lifestyle.dt.global.annotation.service.TransactionalService
import lifestyle.dt.infrastructure.feign.properties.GoogleAuthProperties
import lifestyle.dt.global.security.jwt.JwtGenerator
import lifestyle.dt.infrastructure.feign.client.GoogleAuth
import lifestyle.dt.infrastructure.feign.client.GoogleInfo

@TransactionalService
class GoogleAuthServiceImpl(
    private val googleAuth: GoogleAuth,
    private val googleInfo: GoogleInfo,
    private val googleAuthProperties: GoogleAuthProperties,
    private val userRepository: UserRepository,
    private val userConverter: UserConverter,
    private val jwtGenerator: JwtGenerator
): GoogleAuthService {

    private fun createUser(email: String, name: String, picture: String) =
        userRepository.findByEmail(email) ?: userRepository.save(userConverter.toEntity(email, name, picture))

    private fun getOauthAccessToken(code: String): String =
        googleAuth.queryAccessToken(
            code = code,
            clientId = googleAuthProperties.clientId,
            clientSecret = googleAuthProperties.clientSecret,
            redirectUri = googleAuthProperties.redirectUri,
        ).accessToken

    override fun execute(code: String): TokenDto {
        val accessToken = getOauthAccessToken(code)
        val (email, name, picture) = googleInfo.googleInfo(accessToken)
        val user = createUser(email, name, picture)

        return jwtGenerator.generate(user.id)
    }
}