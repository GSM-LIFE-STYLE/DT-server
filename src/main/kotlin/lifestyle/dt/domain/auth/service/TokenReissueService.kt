package lifestyle.dt.domain.auth.service

import lifestyle.dt.domain.auth.presentation.data.dto.TokenDto

interface TokenReissueService {

    fun execute(refreshToken: String): TokenDto

}