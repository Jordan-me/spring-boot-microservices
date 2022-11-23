package serviceusers

interface UserService {
    fun create(user: UserBoundary): UserBoundary
}
