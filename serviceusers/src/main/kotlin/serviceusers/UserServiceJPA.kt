package serviceusers

import org.apache.commons.validator.routines.EmailValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.stream.Collectors


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
//      Get valid list for roles, if everything is null or empty string default role will be "player"
        user.userRoles = isRoleValid(user.userRoles)
        var userBoundary = this.converter.toBoundary(this.crud.save(this.converter.toEntity(user)))
        userBoundary.password = null
        return userBoundary
    }

    @Transactional(readOnly = true)
    override fun getSpecificUser(email: String): Optional<UserBoundary> {
        return this.crud
            .findById(email)
            .map {
                var userBoundary = this.converter.toBoundary(it)
                userBoundary.password = null
                return@map userBoundary
            }
    }
    @Transactional(readOnly = true)
    override fun login(email: String, password: String): Optional<UserBoundary> {
        return this.crud
            .findByEmailAndPassword(email,password)
            .map {
                var userBoundary = this.converter.toBoundary(it)
                userBoundary.password = null
                return@map userBoundary
            }
    }
    @Transactional
    override fun deleteAll() {
        this.crud
            .deleteAll()
    }

    @Transactional(readOnly = true)
    override fun search(
        criteriaType: String,
        criteriaValue: String,
        size: Int,
        page: Int,
        sortAttribute: String,
        sortOrder: String
    ): List<UserBoundary> {
        when (criteriaType) {
            "byEmailDomain" -> {
                return this.crud
                    .findAllByEmailLike("%@${criteriaValue}", PageRequest.of(page, size,
                        getSortOrder(sortOrder) as Sort.Direction,getSortAttribute(sortAttribute) , "email"))
                    .stream()
                    .map {
                        var userBoundary = this.converter.toBoundary(it)
                        userBoundary.password = null
                        return@map userBoundary
                    }
                    .collect(Collectors.toList())
            }
            "byBirthYear" -> {
                try {
                    val birthYear = Integer.parseInt(criteriaValue)
                } catch (nfe: NumberFormatException) {
                    // not a valid int
                    throw InputException("$criteriaValue is not valid - enter Integer")
                }
                val startDate = SimpleDateFormat("dd-MM-yyyy").parse("01-01-$criteriaValue")
                val finalDate = SimpleDateFormat("dd-MM-yyyy").parse("31-12-$criteriaValue")
                return this.crud
                    .findAllByBirthDateBetween(startDate,finalDate, PageRequest.of(page, size,
                        getSortOrder(sortOrder) as Sort.Direction,getSortAttribute(sortAttribute) , "email"))
                    .stream()
                    .map {
                        var userBoundary = this.converter.toBoundary(it)
                        userBoundary.password = null
                        return@map userBoundary
                    }
                    .collect(Collectors.toList())
            }
            "byRole" -> {
                return this.crud
                    .findAllByUserRolesLike("%${criteriaValue}%", PageRequest.of(page, size,
                        getSortOrder(sortOrder) as Sort.Direction,getSortAttribute(sortAttribute) , "email"))
                    .stream()
                    .map {
                        var userBoundary = this.converter.toBoundary(it)
                        userBoundary.password = null
                        return@map userBoundary }
                    .collect(Collectors.toList())
            }
        }
        if(criteriaType != "")
            throw InputException("$criteriaType is not valid - either byEmailDomain or byBirthYear or byRole or empty string")
        return this.crud
            .findAll(PageRequest.of(page, size, getSortOrder(sortOrder) as Sort.Direction,getSortAttribute(sortAttribute) , "email"))
            .stream()
            .map {
                var userBoundary = this.converter.toBoundary(it)
                userBoundary.password = null
                return@map userBoundary
            }
            .collect(Collectors.toList())
    }
    @Transactional
    override fun updateUser(email: String, user: UserBoundary) {
        val updateUser = this.getSpecificUser(email)
                            .orElseThrow { UserNotFoundException("could not find user by email $email") }
        if(user.password != null){
            updateUser.password = user.password
            isPasswordValid(updateUser.password)
        }
        if(user.name != null){
            updateUser.name = user.name
            if(updateUser.name?.firstName.isNullOrEmpty() || updateUser.name?.lastName.isNullOrEmpty()){
                throw Exception("Please fill your first & last name")
            }
        }
        if(user.birthDate != null){
            updateUser.birthDate = user.birthDate
            isBirthdateValid(updateUser.birthDate)
        }
        if(user.userRoles != null){
            updateUser.userRoles = isRoleValid(user.userRoles)
        }
        this.converter.toBoundary(this.crud.save(this.converter.toEntity(updateUser)))
    }

    private fun isRoleValid(userRoles: List<String>?): MutableList<String> {
        var values = mutableListOf<String>()
        if (userRoles != null){
            values =  userRoles.toMutableList()
            values.removeIf { it == null || it == "" }
        }
        if( values == null || values.size == 0){
            values?.add("player")
        }
        return values

    }

    private fun isBirthdateValid(birthDate: String?) {
        var format = "dd-MM-yyyy"
        var sdf = DateTimeFormatter.ofPattern(format)
//        var sdf = SimpleDateFormat(format)
//        sdf.isLenient = false

        try {
            sdf.parse(birthDate)
        } catch (e: ParseException ) {
            throw Exception("Please fill your birthdate on next format dd-MM-yyyy")
        } catch (e: Exception ){
            throw Exception("Please fill your birthdate on next format dd-MM-yyyy")
        }// valid will still be false
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
    private fun getSortOrder(sortOrder: String): Any {
        if (sortOrder != "DESC" && sortOrder!= "ASC")
            throw InputException("$sortOrder is not valid - either ASC or DESC")
        if(sortOrder == "DESC"){
            return  Sort.Direction.DESC
        }
        return Sort.Direction.ASC
    }
    private fun getSortAttribute(sortAttribute: String): String? {
        if (sortAttribute != "firstName" && sortAttribute != "lastName" && sortAttribute != "birthDate" && sortAttribute != "email")
            throw InputException("$sortAttribute is not valid")
        return sortAttribute
    }
}