package lifestyle.dt.domain.auth.presentation

import lifestyle.dt.domain.auth.presentation.data.request.LoginRequest
import lifestyle.dt.domain.auth.presentation.data.request.SignUpRequest
import lifestyle.dt.domain.auth.presentation.data.response.TokenResponse
import lifestyle.dt.domain.auth.service.LoginService
import lifestyle.dt.domain.auth.service.SignUpService
import lifestyle.dt.domain.user.util.UserConverter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val userConverter: UserConverter,
    private val signUpService: SignUpService,
    private val loginService: LoginService
) {

    @PostMapping("/signup")
    fun signUp(
        @RequestPart(value = "dto") request: SignUpRequest,
        @RequestPart(required = false) file: MultipartFile
    ): ResponseEntity<Void> =
        userConverter.toDto(request)
            .let { signUpService.execute(it, file) }
            .let { ResponseEntity.status(HttpStatus.CREATED).build() }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<TokenResponse> =
        userConverter.toDto(request)
            .let { loginService.execute(it) }
            .let { userConverter.toResponse(it) }
            .let { ResponseEntity.ok(it) }



}