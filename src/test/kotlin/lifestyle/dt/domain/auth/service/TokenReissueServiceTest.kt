package lifestyle.dt.domain.auth.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import lifestyle.dt.common.AnyValueObjectGenerator.anyValueObject
import lifestyle.dt.domain.auth.domain.RefreshToken
import lifestyle.dt.domain.auth.domain.repository.RefreshTokenRepository
import lifestyle.dt.domain.auth.exception.UserNotFoundException
import lifestyle.dt.domain.auth.presentation.data.dto.TokenDto
import lifestyle.dt.domain.auth.service.impl.TokenReissueServiceImpl
import lifestyle.dt.domain.user.domain.User
import lifestyle.dt.domain.user.domain.repository.UserRepository
import lifestyle.dt.global.security.exception.ExpiredRefreshTokenException
import lifestyle.dt.global.security.jwt.JwtGenerator
import lifestyle.dt.global.security.jwt.JwtParser
import org.springframework.data.repository.findByIdOrNull
import java.time.ZonedDateTime
import java.util.*

class TokenReissueServiceTest: BehaviorSpec({
    val jwtGenerator = mockk<JwtGenerator>()
    val jwtParser = mockk<JwtParser>()
    val userRepository = mockk<UserRepository>()
    val refreshTokenRepository = mockk<RefreshTokenRepository>()
    val tokenReissueService = TokenReissueServiceImpl(jwtGenerator, jwtParser, userRepository, refreshTokenRepository)

    Given("refreshToken이 주어질때") {
        val refreshToken = "Bearer refresh_token"
        val parsedRefreshToken = "refresh_token"
        val tokenDto = TokenDto(
            accessToken = "access_token",
            refreshToken = "refresh_token",
            accessTokenExpiredAt = ZonedDateTime.now(),
            refreshTokenExpiredAt = ZonedDateTime.now()
        )
        val userId = UUID.randomUUID()
        val refreshTokenEntity = anyValueObject<RefreshToken>("refreshToken" to refreshToken)
        val user = anyValueObject<User>("id" to userId)

        every { jwtParser.resolveRefreshToken(refreshToken) } returns parsedRefreshToken
        every { refreshTokenRepository.findByIdOrNull(parsedRefreshToken) } returns refreshTokenEntity
        every { userRepository.findByIdOrNull(refreshTokenEntity.userId) } returns user
        every { jwtGenerator.generate(user.id) } returns tokenDto

        When("토큰 재발급을 요청하면") {
            val result = tokenReissueService.execute(refreshToken)

            Then("토큰이 재발급 되어야 한다.") {
                verify(exactly = 1) { jwtGenerator.generate(user.id) }
            }

            Then("tokenDto와 result는 같아야 한다.") {
                tokenDto shouldBe result
            }
        }

        When("만료된 삭제된 refreshToken으로 요청하면") {
            every { jwtParser.resolveRefreshToken(refreshToken) } returns parsedRefreshToken
            every { refreshTokenRepository.findByIdOrNull(parsedRefreshToken) } returns null

            Then("ExpiredRefreshTokenException이 터져야 한다.") {
                shouldThrow<ExpiredRefreshTokenException> {
                    tokenReissueService.execute(refreshToken)
                }
            }
        }

        When("탈퇴된 유저로 재발급을 요청하면") {
            every { refreshTokenRepository.findByIdOrNull(parsedRefreshToken) } returns refreshTokenEntity
            every { jwtParser.resolveRefreshToken(refreshToken) } returns parsedRefreshToken
            every { userRepository.findByIdOrNull(refreshTokenEntity.userId) } returns null

            Then("UserNotFoundException이 터져야 한다.") {
                shouldThrow<UserNotFoundException> {
                    tokenReissueService.execute(refreshToken)
                }
            }
        }
    }

})