package lifestyle.dt.domain.user

import lifestyle.dt.global.BaseIdEntity
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class User(
    @Column(nullable = false)
    val email: String,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = true)
    val profileImageUrl: String

) : BaseIdEntity() {
}