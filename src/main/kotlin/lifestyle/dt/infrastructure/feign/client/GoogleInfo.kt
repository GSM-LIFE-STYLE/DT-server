package lifestyle.dt.infrastructure.feign.client

import org.springframework.cloud.openfeign.FeignClient

@FeignClient(name = "GoogleInfoClient", url = "https://www.googleapis.com/oauth2/v1/userinfo")
class GoogleInfo {
}