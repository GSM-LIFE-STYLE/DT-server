package lifestyle.dt.domain.auth.service

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import lifestyle.dt.common.AnyValueObjectGenerator.anyValueObject
import lifestyle.dt.domain.auth.presentation.data.dto.TokenDto
import lifestyle.dt.domain.auth.service.impl.GoogleAuthServiceImpl
import lifestyle.dt.domain.user.domain.User
import lifestyle.dt.domain.user.domain.repository.UserRepository
import lifestyle.dt.domain.user.util.UserConverter
import lifestyle.dt.infrastructure.feign.properties.GoogleAuthProperties
import lifestyle.dt.global.security.jwt.JwtGenerator
import lifestyle.dt.infrastructure.feign.client.GoogleAuth
import lifestyle.dt.infrastructure.feign.client.GoogleInfo
import lifestyle.dt.infrastructure.feign.dto.response.GoogleInfoResponse
import lifestyle.dt.infrastructure.feign.dto.response.GoogleTokenResponse
import java.time.ZonedDateTime


class GoogleAuthServiceTest: DescribeSpec({
    val userRepository = mockk<UserRepository>()
    val userConverter = mockk<UserConverter>()
    val googleAuthProperties = anyValueObject<GoogleAuthProperties>()
    val googleAuth = mockk<GoogleAuth>()
    val googleInfo = mockk<GoogleInfo>()
    val jwtGenerator = mockk<JwtGenerator>()

    val googleAuthService = GoogleAuthServiceImpl(
        googleAuth = googleAuth,
        googleInfo = googleInfo,
        googleAuthProperties = googleAuthProperties,
        userRepository = userRepository,
        jwtGenerator = jwtGenerator,
        userConverter = userConverter
    )

    describe("user google auth"){

        val code = "code"
        val googleAccessToken = "google_access_token"
        val email = "s22043@gsm.hs.kr"
        val name = "테스트"
        val picture = "image"
        val user = anyValueObject<User>("email" to email)
        val tokenDto = TokenDto(
            accessToken = "access_token",
            refreshToken = "refresh_token",
            accessTokenExpiredAt = ZonedDateTime.now(),
            refreshTokenExpiredAt = ZonedDateTime.now()
        )

        context("가입된 유저의 Oauth 코드가 주어진다면"){
            every { googleAuth.queryAccessToken(code = code, grantType = any(), clientId = any(), clientSecret = any(), redirectUri = any()) } returns GoogleTokenResponse(
                googleAccessToken
            )
            every { googleInfo.googleInfo(googleAccessToken, any()) } returns GoogleInfoResponse(email, name, picture)
            every { userRepository.findByEmail(email) } returns user
            every { jwtGenerator.generate(user.id) } returns tokenDto

            it("토큰을 반환한다."){
                val response = googleAuthService.execute(code)
                response shouldBe tokenDto
            }
        }
    }
})