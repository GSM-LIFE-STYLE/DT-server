package lifestyle.dt.infrastructure.feign.client

import lifestyle.dt.infrastructure.feign.dto.request.GoogleCodeRequest
import lifestyle.dt.infrastructure.feign.dto.response.GoogleTokenResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(name = "googleFeignClient", url = "https://oauth2.googleapis.com/token")
interface GoogleAuth {

    @PostMapping
    fun googleAuth(googleCodeRequest: GoogleCodeRequest): GoogleTokenResponse

}