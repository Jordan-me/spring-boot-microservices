package serviceusers

import java.util.*

class UserBoundary {
    var email: String? = null
    var password: String? = null
    var name:Name? = null
    var birthDate: String? = null
    var userRoles: List<String>? = null

    override fun toString(): String {
        return "UserBoundary(email=$email," +
                " password=$password, " +
                "fullName=$name," +
                " birthDate=$birthDate, " +
                "userRoles=$userRoles)"
    }


}
