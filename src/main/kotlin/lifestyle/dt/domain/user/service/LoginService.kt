package lifestyle.dt.domain.user.service

import lifestyle.dt.domain.user.presentation.data.dto.LoginDto
import lifestyle.dt.domain.user.presentation.data.dto.LoginTokenDto

interface LoginService {

    fun execute(loginDto: LoginDto): LoginTokenDto

}