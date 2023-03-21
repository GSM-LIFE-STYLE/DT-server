package lifestyle.dt.domain.user.domain.repository

import lifestyle.dt.domain.user.domain.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository: CrudRepository<RefreshToken, String>