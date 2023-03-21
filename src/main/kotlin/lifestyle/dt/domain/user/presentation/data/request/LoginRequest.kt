package lifestyle.dt.domain.user.presentation.data.request

data class LoginRequest(
    val email: String,
    val password: String
)