package serviceusers

import java.util.*

interface UserService {
    fun create(user: UserBoundary): UserBoundary
    fun getSpecificUser(email: String): Optional<UserBoundary>
    fun login(email: String, password: String): Optional<UserBoundary>
    fun deleteAll()
    fun search(criteriaType: String, criteriaValue: String, size: Int, page: Int, sortAttribute: String, sortOrder: String): List<UserBoundary>
}
