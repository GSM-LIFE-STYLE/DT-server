package lifestyle.dt.domain.user.util

import lifestyle.dt.domain.user.domain.User
import lifestyle.dt.domain.user.presentation.data.dto.SignUpDto
import lifestyle.dt.domain.user.presentation.data.request.SignUpRequest

interface UserConverter {

    fun toDto(request: SignUpRequest): SignUpDto
    fun toEntity(dto: SignUpDto, profileUrl: String): User

}