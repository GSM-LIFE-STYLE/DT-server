package lifestyle.dt.infrastructure.feign.client

import lifestyle.dt.infrastructure.feign.dto.response.GoogleInfoResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "googleInfoFeign", url = "https://www.googleapis.com/oauth2/v1/userinfo")
interface GoogleInfo {

    @GetMapping("?alt=json&access_token={ACCESS_TOKEN}")
    fun googleInfo(@PathVariable("ACCESS_TOKEN") accessToken: String): GoogleInfoResponse
}