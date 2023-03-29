package lifestyle.dt.domain.user.domain

import lifestyle.dt.domain.auth.presentation.data.enums.UserRole
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
class User(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    val id: UUID,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false, length = 60)
    var encodePassword: String,

    @Column(nullable = false, length = 10)
    val name: String,

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "UserRole", joinColumns = [JoinColumn(name = "id")])
    var roles: MutableList<UserRole> = mutableListOf(),

    @Column(nullable = false, columnDefinition = "TEXT")
    var profileUrl: String?,
)