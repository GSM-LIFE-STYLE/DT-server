package lifestyle.dt.global.util

import lifestyle.dt.domain.user.domain.User
import lifestyle.dt.domain.user.domain.repository.UserRepository
import lifestyle.dt.domain.user.exception.UserNotFoundException
import lifestyle.dt.global.security.auth.AuthDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class UserUtil(
    private val userRepository: UserRepository
) {

    fun fetchCurrentUser(): User {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val email = if (principal is UserDetails) {
            (principal as AuthDetails).username
        } else {
            principal.toString()
        }
        return fetchUserByEmail(email)
    }

    fun fetchUserByEmail(email: String): User =
        userRepository.findByEmail(email) ?: throw UserNotFoundException()
}