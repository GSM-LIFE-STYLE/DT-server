package lifestyle.dt.global.util

import lifestyle.dt.domain.user.domain.User
import lifestyle.dt.domain.user.domain.repository.UserRepository
import lifestyle.dt.domain.auth.exception.UserNotFoundException
import lifestyle.dt.global.security.auth.AuthDetails
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserUtil(
    private val userRepository: UserRepository
) {

    fun fetchCurrentUser(): User {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val uuid = if (principal is UserDetails) {
            (principal as AuthDetails).username
        } else {
            principal.toString()
        }
        return fetchUserByEmail(uuid)
    }

    fun fetchUserByEmail(uuid: String): User =
        userRepository.findByIdOrNull(UUID.fromString(uuid)) ?: throw UserNotFoundException()
}