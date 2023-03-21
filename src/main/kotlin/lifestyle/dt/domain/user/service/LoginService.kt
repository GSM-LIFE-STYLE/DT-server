package lifestyle.dt.domain.user.service

import lifestyle.dt.domain.user.presentation.data.dto.LoginDto
import lifestyle.dt.domain.user.presentation.data.dto.TokenDto

interface LoginService {

    fun execute(loginDto: LoginDto): TokenDto

}