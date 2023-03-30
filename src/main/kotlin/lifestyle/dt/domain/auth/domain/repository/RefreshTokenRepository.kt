package lifestyle.dt.domain.auth.domain.repository

import lifestyle.dt.domain.auth.domain.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository: CrudRepository<RefreshToken, String>