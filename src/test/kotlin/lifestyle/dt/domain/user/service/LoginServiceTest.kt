package lifestyle.dt.domain.user.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import lifestyle.dt.domain.user.domain.User
import lifestyle.dt.domain.user.domain.repository.UserRepository
import lifestyle.dt.domain.user.exception.PasswordMismatchException
import lifestyle.dt.domain.user.exception.UserNotFoundException
import lifestyle.dt.domain.user.presentation.data.dto.LoginDto
import lifestyle.dt.domain.user.presentation.data.dto.TokenDto
import lifestyle.dt.domain.user.presentation.data.enums.UserRole
import lifestyle.dt.domain.user.service.impl.LoginServiceImpl
import lifestyle.dt.global.security.jwt.JwtGenerator
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.ZonedDateTime
import java.util.*

class LoginServiceTest: BehaviorSpec({
    val userRepository = mockk<UserRepository>()
    val passwordEncoder = mockk<PasswordEncoder>()
    val jwtGenerator = mockk<JwtGenerator>()
    val loginService = LoginServiceImpl(userRepository, passwordEncoder, jwtGenerator)

    val userId = UUID.randomUUID()
    val email = "test@test.com"
    val password = "test password"
    val name = "test name"
    val profileUrl = "test url"

    Given("loginDto가 주어질때") {
        val loginDto = LoginDto(email, password)
        val user = User(userId, email, password, name, Collections.singletonList(UserRole.ROLE_USER), profileUrl)
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