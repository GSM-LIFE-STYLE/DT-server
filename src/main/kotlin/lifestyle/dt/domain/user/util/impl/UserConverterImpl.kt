package lifestyle.dt.domain.user.util.impl

import lifestyle.dt.domain.user.domain.User
import lifestyle.dt.domain.user.presentation.data.dto.SignUpDto
import lifestyle.dt.domain.user.presentation.data.enums.UserRole
import lifestyle.dt.domain.user.presentation.data.request.SignUpRequest
import lifestyle.dt.domain.user.util.UserConverter
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserConverterImpl : UserConverter {

    override fun toDto(request: SignUpRequest): SignUpDto =
        SignUpDto(email = request.email, password = request.password, name = request.name)

    override fun toEntity(dto: SignUpDto, profileUrl: String): User =
        User(
            id = UUID.randomUUID(),
            email = dto.email,
            encodedPassword = dto.password,
            name = dto.name,
            roles = Collections.singletonList(UserRole.ROLE_USER),
            profileUrl = profileUrl
        )

}