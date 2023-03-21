package lifestyle.dt.domain.user.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.UUID
import java.util.concurrent.TimeUnit

@RedisHash("refresh_token")
data class RefreshToken(
    @Id
    val refreshToken: String,
    val userIdx: UUID,
    @TimeToLive(unit = TimeUnit.SECONDS)
    val expiredAt: Int
)
