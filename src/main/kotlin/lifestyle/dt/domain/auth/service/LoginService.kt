package lifestyle.dt.domain.auth.service

import lifestyle.dt.domain.auth.presentation.data.dto.LoginDto
import lifestyle.dt.domain.auth.presentation.data.dto.TokenDto

interface LoginService {

    fun execute(loginDto: LoginDto): TokenDto

}