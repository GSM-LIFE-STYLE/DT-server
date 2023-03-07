package lifestyle.dt.domain.user.repository

import lifestyle.dt.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<Long, User> {
}