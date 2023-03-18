package lifestyle.dt.domain.user.presentation.data.enums

import org.springframework.security.core.GrantedAuthority

enum class UserRole: GrantedAuthority {
    ROLE_ADMIN, ROLE_USER;
    override fun getAuthority(): String = name
}