package serviceusers

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

@Component
class UserConverter {
    fun toEntity(boundary : UserBoundary): UserEntity{
        var entity = UserEntity()
        entity.email = boundary.email!!
        entity.password = boundary.password
        entity.firstName = boundary.name!!.firstName
        entity.lastName = boundary.name!!.lastName
        entity.birthDate = SimpleDateFormat("dd-MM-yyyy").parse(boundary.birthDate)
        entity.userRoles = ObjectMapper().writeValueAsString(boundary.userRoles)
        return entity

    }

    fun toBoundary(entity: UserEntity): UserBoundary{
        var boudary = UserBoundary()

        boudary.email = entity.email
        boudary.password = entity.password
        boudary.name = Name()
        boudary.name!!.firstName = entity.firstName
        boudary.name!!.lastName = entity.lastName
        val printFormat = SimpleDateFormat("dd-MM-yyyy")
        boudary.birthDate = printFormat.format(entity.birthDate)
//        val calendar = GregorianCalendar()
//        val mFormat = DecimalFormat("00");
//        calendar.time = entity.birthDate
//        boudary.birthDate = "${mFormat.format(calendar.get(Calendar.DAY_OF_MONTH))}-${mFormat.format(calendar.get(Calendar.MONTH))}-${calendar.get(Calendar.YEAR)}"
//            entity.birthDate.toString()
        boudary.userRoles = ObjectMapper().readValue(entity.userRoles, List::class.java) as List<String>
        return boudary
    }


}