package lifestyle.dt.infrastructure.feign.client

import lifestyle.dt.infrastructure.feign.dto.request.GoogleCodeRequest
import lifestyle.dt.infrastructure.feign.dto.response.GoogleTokenResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "googleFeignClient", url = "https://oauth2.googleapis.com/token")
interface GoogleAuth {

    @PostMapping
    fun googleAuth(
        @RequestParam("grant_type") grantType: String = "authorization_code",
        @RequestParam("client_id") clientId: String,
        @RequestParam("client_secret") clientSecret: String,
        @RequestParam code: String
    ): GoogleTokenResponse

}