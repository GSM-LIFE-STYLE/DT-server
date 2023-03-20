package lifestyle.dt.domain.user.presentation

import lifestyle.dt.domain.user.presentation.data.request.SignUpRequest
import lifestyle.dt.domain.user.service.SignUpService
import lifestyle.dt.domain.user.util.UserConverter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/auth")
class UserAuthController(
    private val userConverter: UserConverter,
    private val signUpService: SignUpService
) {

    @PostMapping("/signup")
    fun signUp(
        @RequestPart(value = "dto") request: SignUpRequest,
        @RequestPart(required = false) file: MultipartFile
    ): ResponseEntity<Void> =
        userConverter.toDto(request)
            .let { signUpService.execute(it, file) }
            .let { ResponseEntity.status(HttpStatus.CREATED).build() }

}