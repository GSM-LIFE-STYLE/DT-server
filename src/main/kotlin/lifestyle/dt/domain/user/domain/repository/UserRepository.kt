package lifestyle.dt.domain.user.domain.repository

import lifestyle.dt.domain.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {

    fun findByEmail(email: String?): User?
    fun existsByEmail(email: String): Boolean

}