package lifestyle.dt.global.security.jwt.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "jwt.time")
class JwtExpTimeProperties(
    val accessExp: Int,
    val refreshExp: Int
)