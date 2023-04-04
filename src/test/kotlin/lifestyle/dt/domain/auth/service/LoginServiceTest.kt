package lifestyle.dt.domain.auth.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import lifestyle.dt.common.AnyValueObjectGenerator.anyValueObject
import lifestyle.dt.domain.user.domain.User
import lifestyle.dt.domain.user.domain.repository.UserRepository
import lifestyle.dt.domain.auth.exception.PasswordMismatchException
import lifestyle.dt.domain.auth.exception.UserNotFoundException
import lifestyle.dt.domain.auth.presentation.data.dto.LoginDto
import lifestyle.dt.domain.auth.presentation.data.dto.TokenDto
import lifestyle.dt.domain.auth.service.impl.LoginServiceImpl
import lifestyle.dt.global.security.jwt.JwtGenerator
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.ZonedDateTime

class LoginServiceTest: BehaviorSpec({
    val userRepository = mockk<UserRepository>()
    val passwordEncoder = mockk<PasswordEncoder>()
    val jwtGenerator = mockk<JwtGenerator>()
    val loginService = LoginServiceImpl(userRepository, passwordEncoder, jwtGenerator)

    Given("loginDto가 주어질때") {
        val email = "test@test.com"
        val password = "test password"

        val loginDto = LoginDto(email, password)
        val user = anyValueObject<User>("email" to email)
        val tokenDto = TokenDto(
            accessToken = "test accessToken",
            refreshToken = "test refreshToken",
            accessTokenExpiredAt = ZonedDateTime.now(),
            refreshTokenExpiredAt = ZonedDateTime.now()
        )

        every { userRepository.findByEmail(loginDto.email) } returns user
        every { passwordEncoder.matches(password, user.encodePassword) } returns true
        every { jwtGenerator.generate(user.id) } returns tokenDto

        When("로그인 요청을 하면") {
            val result = loginService.execute(loginDto)

            Then("토큰이 발급이 되어야한다.") {
                verify(exactly = 1) { jwtGenerator.generate(user.id) }
            }

            Then("tokenDto와 result는 같아야한다.") {
                tokenDto shouldBe result
            }
        }

        When("틀린 비밀번호로 요청을 하면") {
            every { userRepository.findByEmail(loginDto.email) } returns user
            every { passwordEncoder.matches(password, user.encodePassword) } returns false

            Then("PasswordMismatchException이 터져야한다.") {
                shouldThrow<PasswordMismatchException> {
                    loginService.execute(loginDto)
                }
            }
        }

        When("존재하지 않은 이메일로 요청을 하면") {
            every { userRepository.findByEmail(loginDto.email) } returns null

            Then("UserNotFoundException이 터져야한다.") {
                shouldThrow<UserNotFoundException> {
                    loginService.execute(loginDto)
                }
            }
        }
    }
})