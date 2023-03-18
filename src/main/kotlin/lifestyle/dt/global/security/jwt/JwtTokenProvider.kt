package lifestyle.dt.global.security.jwt

import lifestyle.dt.global.security.jwt.properties.JwtProperties
import org.springframework.stereotype.Component

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties,

) {
}