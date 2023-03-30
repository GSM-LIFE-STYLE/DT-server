package lifestyle.dt.domain.auth.service

import lifestyle.dt.domain.auth.presentation.data.dto.TokenDto

interface GoogleAuthService {
    fun execute(code: String): TokenDto
}