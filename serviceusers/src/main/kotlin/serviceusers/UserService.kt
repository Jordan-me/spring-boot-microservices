package serviceusers

import java.util.*

interface UserService {
    fun create(user: UserBoundary): UserBoundary
    fun getSpecificUser(email: String): Optional<UserBoundary>
    fun login(email: String, password: String): Optional<UserBoundary>
    fun deleteAll()
}
