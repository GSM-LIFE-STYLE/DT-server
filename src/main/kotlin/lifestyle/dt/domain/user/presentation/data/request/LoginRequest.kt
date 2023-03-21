package lifestyle.dt.domain.user.presentation.data.request

import javax.validation.constraints.Size

data class LoginRequest(
    @field:Size(min = 1, max = 20)
    val email: String,
    @field:Size(min = 1, max = 30)
    val password: String
)