package lifestyle.dt.domain.user.service.impl

import lifestyle.dt.domain.user.domain.repository.UserRepository
import lifestyle.dt.domain.user.exception.DuplicateEmailException
import lifestyle.dt.domain.user.presentation.data.dto.SignUpDto
import lifestyle.dt.domain.user.service.SignUpService
import lifestyle.dt.domain.user.util.UserConverter
import lifestyle.dt.global.annotation.service.TransactionalService
import lifestyle.dt.infrastructure.s3.service.S3Service
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@TransactionalService
class SignUpServiceImpl(
    private val userConverter: UserConverter,
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val s3Service: S3Service
): SignUpService {

    override fun execute(signUpDto: SignUpDto, file: MultipartFile): UUID {
        if (userRepository.existsByEmail(signUpDto.email)) {
            throw DuplicateEmailException()
        }
        val profileUrl = s3Service.uploadImage(file, "user/")
        val user = userConverter.toEntity(
            dto = signUpDto.copy(password = passwordEncoder.encode(signUpDto.password)),
            profileUrl = profileUrl
        )
        return userRepository.save(user).id
    }

}