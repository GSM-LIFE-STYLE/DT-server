package lifestyle.dt.domain.user.util

import lifestyle.dt.domain.user.domain.User
import lifestyle.dt.domain.user.presentation.data.dto.LoginDto
import lifestyle.dt.domain.user.presentation.data.dto.LoginTokenDto
import lifestyle.dt.domain.user.presentation.data.dto.SignUpDto
import lifestyle.dt.domain.user.presentation.data.request.LoginRequest
import lifestyle.dt.domain.user.presentation.data.request.SignUpRequest
import lifestyle.dt.domain.user.presentation.data.response.LoginTokenResponse

interface UserConverter {

    fun toDto(request: SignUpRequest): SignUpDto
    fun toDto(request: LoginRequest): LoginDto
    fun toEntity(dto: SignUpDto, profileUrl: String): User
    fun toResponse(dto: LoginTokenDto): LoginTokenResponse

}