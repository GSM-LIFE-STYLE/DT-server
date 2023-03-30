package lifestyle.dt.domain.auth.presentation

import lifestyle.dt.domain.auth.presentation.data.response.GoogleLoginLinkResponse
import lifestyle.dt.domain.auth.presentation.data.response.TokenResponse
import lifestyle.dt.domain.auth.service.GoogleAuthService
import lifestyle.dt.domain.auth.service.QueryGoogleAuthLinkService
import lifestyle.dt.domain.user.util.UserConverter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/google")
class GoogleController(
    private val googleAuthService: GoogleAuthService,
    private val googleQueryGoogleAuthLinkService: QueryGoogleAuthLinkService,
    private val userConverter: UserConverter
) {

    @GetMapping("/link")
    fun queryGoogleAuthLink(): ResponseEntity<GoogleLoginLinkResponse> =
        googleQueryGoogleAuthLinkService.execute()
            .let { ResponseEntity.ok(it) }


    @GetMapping("/sign")
    fun googleAuthLogin(@RequestParam("code") code: String): ResponseEntity<TokenResponse> =
        googleAuthService.execute(code)
            .let { ResponseEntity.ok(userConverter.toResponse(it)) }
}