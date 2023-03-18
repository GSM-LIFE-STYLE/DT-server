package lifestyle.dt.domain.user.domain

import lifestyle.dt.domain.user.presentation.data.enums.UserRole
import org.hibernate.annotations.GenericGenerator
import java.util.UUID
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
class User(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    val id: UUID,

    @Column(unique = true)
    val email: String,

    @field:Size(max = 60)
    var password: String,

    @field:Size(max  = 30)
    val name: String,

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "UserRole", joinColumns = [JoinColumn(name = "id")])
    var roles: MutableList<UserRole> = mutableListOf(),

    @Column(nullable = true, columnDefinition = "TEXT")
    val profileUrl: String?,
) {


}