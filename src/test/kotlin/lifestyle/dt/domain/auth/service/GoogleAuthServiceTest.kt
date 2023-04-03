package lifestyle.dt.domain.auth.service

import io.kotest.core.spec.style.DescribeSpec
import io.mockk.mockk
import lifestyle.dt.domain.user.domain.repository.UserRepository
import lifestyle.dt.global.security.jwt.JwtGenerator
import lifestyle.dt.infrastructure.feign.client.GoogleAuth
import lifestyle.dt.infrastructure.feign.client.GoogleInfo


class GoogleAuthServiceTest: DescribeSpec({
    val userRepository = mockk<UserRepository>()
    val googleAuth = mockk<GoogleAuth>()
    val googleEmail = mockk<GoogleInfo>()
    val jwtGenerator = mockk<JwtGenerator>()
})