package lifestyle.dt.infrastructure.feign.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Import

@EnableFeignClients(basePackages = ["lifestyle.dt.infrastructure.feign"])
@Import()
class FeignConfig {
}