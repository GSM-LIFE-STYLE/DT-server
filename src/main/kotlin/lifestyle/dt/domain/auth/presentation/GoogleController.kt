package lifestyle.dt.domain.auth.presentation

import lifestyle.dt.domain.auth.presentation.data.response.TokenResponse
import lifestyle.dt.domain.auth.service.GoogleAuthService
import lifestyle.dt.domain.user.util.UserConverter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GoogleController(
    private val googleAuthService: GoogleAuthService,
    private val userConverter: UserConverter
) {

    @GetMapping("/receive_code")
    fun googleAuthLogin(@RequestParam("code") code: String): ResponseEntity<TokenResponse> =
        googleAuthService.execute(code)
            .let { ResponseEntity.ok(userConverter.toResponse(it)) }
}