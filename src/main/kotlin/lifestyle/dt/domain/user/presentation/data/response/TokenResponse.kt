package lifestyle.dt.domain.user.presentation.data.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.ZonedDateTime

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH-mm-ss")
    val accessTokenExpiredAt: ZonedDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH-mm-ss")
    val refreshTokenExpiredAt: ZonedDateTime
)
