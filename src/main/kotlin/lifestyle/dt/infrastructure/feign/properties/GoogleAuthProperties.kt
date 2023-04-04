package lifestyle.dt.infrastructure.feign.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("auth.google")
data class GoogleAuthProperties(
    val baseUrl: String,
    val clientId: String,
    val clientSecret: String,
    val redirectUri: String
)