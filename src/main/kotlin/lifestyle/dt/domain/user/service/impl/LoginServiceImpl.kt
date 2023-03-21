package lifestyle.dt.domain.user.service.impl

import lifestyle.dt.domain.user.domain.repository.UserRepository
import lifestyle.dt.domain.user.exception.PasswordMismatchException
import lifestyle.dt.domain.user.exception.UserNotFoundException
import lifestyle.dt.domain.user.presentation.data.dto.LoginDto
import lifestyle.dt.domain.user.presentation.data.dto.LoginTokenDto
import lifestyle.dt.domain.user.service.LoginService
import lifestyle.dt.global.annotation.service.TransactionalService
import lifestyle.dt.global.security.jwt.JwtGenerator
import org.springframework.security.crypto.password.PasswordEncoder

@TransactionalService
class LoginServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtGenerator: JwtGenerator
): LoginService {

    override fun execute(loginDto: LoginDto): LoginTokenDto {
        val user = userRepository.findByEmail(loginDto.email) ?: throw UserNotFoundException()

        if (!passwordEncoder.matches(loginDto.password, user.encodePassword)) {
            throw PasswordMismatchException()
        }

        return jwtGenerator.generate(user.id)
    }

}