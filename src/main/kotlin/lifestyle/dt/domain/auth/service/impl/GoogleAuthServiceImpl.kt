package lifestyle.dt.domain.auth.service.impl

import lifestyle.dt.domain.auth.presentation.data.dto.TokenDto
import lifestyle.dt.domain.auth.service.GoogleAuthService
import lifestyle.dt.domain.user.domain.repository.UserRepository
import lifestyle.dt.domain.user.util.UserConverter
import lifestyle.dt.global.security.GoogleAuthProperties
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
    private val userConverter: UserConverter
): GoogleAuthService {

    private fun createUser(email: String, name: String, picture: String){
        userRepository.findByEmail(email) ?: userRepository.save(userConverter.toEntity(email, name, picture))
    }

    override fun execute(code: String): TokenDto {
        val accessToken = googleAuth.googleAuth(
            GoogleCodeRequest(
                code = URLDecoder.decode(code, StandardCharsets.UTF_8),
                clientId = googleAuthProperties.clientId,
                clientSecret = googleAuthProperties.clientSecret,
                redirectUri = googleAuthProperties.redirectUri
            )
        ).accessToken

        val googleInfoResponse = googleInfo.googleInfo(accessToken)

        val email = googleInfoResponse.email
        val name = googleInfoResponse.name
        val picture = googleInfoResponse.picture

        createUser(email, name, URLDecoder.decode(picture, StandardCharsets.UTF_8))

    }
}