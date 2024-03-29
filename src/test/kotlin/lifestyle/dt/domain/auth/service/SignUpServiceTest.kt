package lifestyle.dt.domain.auth.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import lifestyle.dt.common.AnyValueObjectGenerator.anyValueObject
import lifestyle.dt.domain.auth.exception.DuplicateEmailException
import lifestyle.dt.domain.auth.presentation.data.dto.SignUpDto
import lifestyle.dt.domain.auth.service.impl.SignUpServiceImpl
import lifestyle.dt.domain.user.domain.User
import lifestyle.dt.domain.user.domain.repository.UserRepository
import lifestyle.dt.domain.user.util.UserConverter
import lifestyle.dt.infrastructure.s3.service.S3Service
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.crypto.password.PasswordEncoder
import java.io.File
import java.io.FileInputStream

class SignUpServiceTest: BehaviorSpec({
    val userConverter = mockk<UserConverter>()
    val passwordEncoder = mockk<PasswordEncoder>()
    val userRepository = mockk<UserRepository>()
    val s3Service = mockk<S3Service>()
    val signUpService = SignUpServiceImpl(userConverter, passwordEncoder, userRepository, s3Service)

    Given("signUpDto가 주어였을때") {
        val email = "test@test.com"
        val password = "test password"
        val encodePassword = "encoded password"
        val name = "test name"
        val profileUrl = "test profileUrl"

        val fileName = "test_image"
        val contentType = "image/jpg"
        val filePath = "src/test/resources/image/test_image.jpg"
        val file = MockMultipartFile(
            fileName, "image/test_image.jpg", contentType, FileInputStream(File(filePath))
        )

        val signUpDto = SignUpDto(email, password, name)
        val user = anyValueObject<User>("email" to email)

        every { userRepository.existsByEmail(signUpDto.email) } returns false
        every { s3Service.uploadImage(file, "user/") } returns profileUrl
        every { passwordEncoder.encode(signUpDto.password) } returns encodePassword
        every { userConverter.toEntity(signUpDto.copy(password = encodePassword), profileUrl) } returns user
        every { userRepository.save(user) } returns user

        When("회원가입 요청을 하면") {
            val result = signUpService.execute(signUpDto, file)

            Then("비밀번호가 인코딩 되어야 한다.") {
                verify(exactly = 1) { passwordEncoder.encode(signUpDto.password) }
            }

            Then("계정이 생성이 되야한다.") {
                verify(exactly = 0) { userRepository.save(user) }
            }

            Then("userId와 result는 같아야 한다.") {
                user.id shouldBe result
            }

        }

        When("중복된 이메일로 요청을 하면") {
            every { userRepository.existsByEmail(signUpDto.email) } returns true

            Then("DuplicateEmailException이 터져야 한다.") {
                shouldThrow<DuplicateEmailException> {
                    signUpService.execute(signUpDto, file)
                }
            }
        }
    }
})