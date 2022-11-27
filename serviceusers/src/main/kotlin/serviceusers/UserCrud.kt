package serviceusers

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.domain.Pageable
import java.util.Optional

interface UserCrud : JpaRepository<UserEntity, String> {
    fun findByEmailAndPassword (
        @Param("email") email: String,
        @Param("password") password: String,
//        pageable:Pageable
    ):Optional<UserEntity>
}
