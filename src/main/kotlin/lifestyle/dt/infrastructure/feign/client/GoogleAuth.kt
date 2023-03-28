package lifestyle.dt.infrastructure.feign.client

import org.springframework.cloud.openfeign.FeignClient

@FeignClient(name = "GoogleAuthClient", url = "https://oauth2.googleapis.com/token")
class GoogleAuth {
}