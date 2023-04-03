package lifestyle.dt.domain.auth.service.impl

import lifestyle.dt.domain.auth.domain.repository.RefreshTokenRepository
import lifestyle.dt.domain.auth.exception.UserNotFoundException
import lifestyle.dt.domain.auth.presentation.data.dto.TokenDto
import lifestyle.dt.domain.auth.service.TokenReissueService
import lifestyle.dt.domain.user.domain.repository.UserRepository
import lifestyle.dt.global.annotation.service.TransactionalService
import lifestyle.dt.global.security.exception.ExpiredRefreshTokenException
import lifestyle.dt.global.security.exception.InvalidTokenTypeException
import lifestyle.dt.global.security.jwt.JwtGenerator
import lifestyle.dt.global.security.jwt.JwtParser
import org.springframework.data.repository.findByIdOrNull

@TransactionalService
class TokenReissueServiceImpl(
    private val jwtGenerator: JwtGenerator,
    private val jwtParser: JwtParser,
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository
): TokenReissueService {

    override fun execute(refreshToken: String): TokenDto {
        val parsedRefreshToken = jwtParser.resolveRefreshToken(refreshToken)
            ?: throw InvalidTokenTypeException()

        val refreshTokenEntity = refreshTokenRepository.findByIdOrNull(parsedRefreshToken)
            ?: throw ExpiredRefreshTokenException()

        val user = userRepository.findByIdOrNull(refreshTokenEntity.userId)
            ?: throw UserNotFoundException()

        return jwtGenerator.generate(user.id)
    }

}