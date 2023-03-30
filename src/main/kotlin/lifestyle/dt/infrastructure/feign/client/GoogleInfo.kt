package lifestyle.dt.infrastructure.feign.client

import lifestyle.dt.infrastructure.feign.dto.response.GoogleInfoResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "googleInfoFeign", url = "https://www.googleapis.com/oauth2/v1/userinfo")
interface GoogleInfo {

    @GetMapping
    fun googleInfo(
        @RequestParam("access_token") accessToken: String,
        @RequestParam("alt") alt: String = "json"
    ): GoogleInfoResponse
}