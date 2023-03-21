package lifestyle.dt.domain.user.presentation.data.request

data class SignUpRequest(
    val email: String,
    val password: String,
    val name: String
)