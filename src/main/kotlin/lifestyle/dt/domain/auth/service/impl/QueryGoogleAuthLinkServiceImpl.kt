package lifestyle.dt.domain.auth.service.impl

import lifestyle.dt.domain.auth.presentation.data.response.GoogleLoginLinkResponse
import lifestyle.dt.domain.auth.service.QueryGoogleAuthLinkService
import lifestyle.dt.global.security.GoogleAuthProperties
import org.springframework.stereotype.Service

@Service
class QueryGoogleAuthLinkServiceImpl(
    private val googleAuthProperties: GoogleAuthProperties
): QueryGoogleAuthLinkService {

    companion object {
        private const val URL =
            "%s?client_id=%s&redirect_uri=%s&response_type=code&scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile"
    }

    override fun execute(): GoogleLoginLinkResponse =
        GoogleLoginLinkResponse(
            URL.format(
                googleAuthProperties.baseUrl,
                googleAuthProperties.clientId,
                googleAuthProperties.redirectUri
            )
        )
}