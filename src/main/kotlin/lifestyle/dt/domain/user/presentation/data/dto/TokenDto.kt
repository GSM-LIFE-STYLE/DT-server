package lifestyle.dt.domain.user.presentation.data.dto

import java.time.ZonedDateTime

data class TokenDto(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiredAt: ZonedDateTime,
    val refreshTokenExpiredAt: ZonedDateTime
)