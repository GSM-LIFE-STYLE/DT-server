package lifestyle.dt.domain.auth.service

import lifestyle.dt.domain.auth.presentation.data.response.GoogleLoginLinkResponse

interface QueryGoogleAuthLinkService {
    fun execute(): GoogleLoginLinkResponse
}