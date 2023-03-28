package lifestyle.dt.infrastructure.feign.config

import feign.codec.ErrorDecoder
import lifestyle.dt.infrastructure.feign.error.FeignClientErrorDecoder
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import


@EnableFeignClients(basePackages = ["lifestyle.dt.infrastructure.feign"])
@Import(FeignClientErrorDecoder::class)
@Configuration
class FeignConfig {

    @Bean
    @ConditionalOnMissingBean(value = [ErrorDecoder::class])
    fun commonFeignErrorDecoder(): FeignClientErrorDecoder? = FeignClientErrorDecoder()

}