package serviceusers

import org.jetbrains.annotations.NotNull
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.Size

@Entity
@Table(name="USERS")
class UserEntity() {
    @Id @NotNull @Email @Size(max = 100) @Column(unique = true)
    var email: String? = null
    var password: String? = null
    var firstName: String? = null
    var lastName: String? = null
    @Temporeal(TemporalType.Date) var birthDate: Date? = null
    var userRoles: String? = null
}
