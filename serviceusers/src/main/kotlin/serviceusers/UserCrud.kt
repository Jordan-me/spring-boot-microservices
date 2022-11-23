package serviceusers

import org.springframework.data.jpa.repository.JpaRepository

interface UserCrud : JpaRepository<UserEntity, String> {

}
