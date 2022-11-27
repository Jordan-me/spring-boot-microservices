package serviceusers

import org.apache.commons.validator.routines.EmailValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.text.ParseException
import java.text.SimpleDateFormat
import javax.swing.JOptionPane




@Service
class UserServiceJPA(
    @Autowired val crud:UserCrud,
    @Autowired val converter:UserConverter
) :UserService {
    @Transactional
    override fun create(user: UserBoundary): UserBoundary {
//      Password need to contain at leas one digit and 8 characters
        isPasswordValid(user.password)
//      Email need to be valid one
        isEmailValid(user.email)
//      check if the full name is contained non-empty strings as first and last name
        if(user.name?.firstName.isNullOrEmpty() || user.name?.lastName.isNullOrEmpty()){
            throw Exception("Please fill your first & last name")
        }
        if(this.crud.existsById(user.email!!)){
            throw Exception("Please try other email")
        }
        isBirthdateValid(user.birthDate)
        isRoleValid(user.userRoles)
        var userBoundary = this.converter.toBoundary(this.crud.save(this.converter.toEntity(user)))
        userBoundary.password = null
        return userBoundary
    }

    private fun isRoleValid(userRoles: List<String>?) {
        if (userRoles != null) {
            for(role in userRoles) {
//                return MutableList
            }
        }
    }

    private fun isBirthdateValid(birthDate: String?) {
        var format = "dd-MM-yyyy"
        var sdf = SimpleDateFormat(format)
        try {
            sdf.parse(birthDate)
        } catch (e: ParseException) {
            throw Exception("Please fill your birthdate on next format dd-MM-yyyy")
        } // valid will still be false
    }

    private fun isEmailValid(email: String?) {
        if(!EmailValidator.getInstance().isValid(email)){
            throw Exception("Invalid Email- You need to enter a valid email")
        }
    }

    private fun isPasswordValid(password: String?) {
        val minLength = 8
        if(password.isNullOrEmpty() || password.length < minLength || !password.matches(Regex(".*\\d+.*"))){
            throw Exception("Invalid Password - You need to enter at least $minLength characters and at least one digit")
        }
    }
}