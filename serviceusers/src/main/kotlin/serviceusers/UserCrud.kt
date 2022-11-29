package serviceusers

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.domain.Pageable
import java.util.*

interface UserCrud : JpaRepository<UserEntity, String> {
    fun findByEmailAndPassword (
        @Param("email") email: String,
        @Param("password") password: String,
//        pageable:Pageable
    ):Optional<UserEntity>

    fun findAllByEmailLike(
        @Param("pattern") pattern: String,
        pageable:Pageable
    ):List<UserEntity>
    fun findAllByBirthDateBetween(
        @Param("start") start: Date,
        @Param("end") end: Date,
        pageable:Pageable
    ):List<UserEntity>

    fun findAllByUserRolesLike(
        @Param("pattern") pattern: String,
        pageable:Pageable
    ):List<UserEntity>
}
