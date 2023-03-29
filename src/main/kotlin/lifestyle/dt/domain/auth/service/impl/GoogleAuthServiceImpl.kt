package lifestyle.dt.domain.auth.service.impl

import lifestyle.dt.domain.auth.presentation.data.dto.TokenDto
import lifestyle.dt.domain.auth.service.GoogleAuthService
import lifestyle.dt.global.security.GoogleAuthProperties
import lifestyle.dt.infrastructure.feign.client.GoogleAuth
import lifestyle.dt.infrastructure.feign.client.GoogleInfo
import lifestyle.dt.infrastructure.feign.dto.request.GoogleCodeRequest
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class GoogleAuthServiceImpl(
    private val googleAuth: GoogleAuth,
    private val googleInfo: GoogleInfo,
    private val googleAuthProperties: GoogleAuthProperties
): GoogleAuthService {

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
    }
}