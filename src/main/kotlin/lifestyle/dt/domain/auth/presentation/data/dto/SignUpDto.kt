package lifestyle.dt.domain.auth.presentation.data.dto

data class SignUpDto(
    val email: String,
    val password: String,
    val name: String
)