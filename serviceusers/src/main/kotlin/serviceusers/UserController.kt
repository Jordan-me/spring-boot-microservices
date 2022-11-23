package serviceusers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
class UserController (
    @Autowired val userService: UserService
    ){

    @RequestMapping(
        path = ["/users"],
        method = [RequestMethod.POST],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun create(@RequestBody user:UserBoundary):UserBoundary{
        return this.userService.create(user)
    }

    @RequestMapping(
        path = ["/users/byEmail/{email}"],
        method = [RequestMethod.GET],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getUsersByEmail(@PathVariable email: String): UserBoundary {
      TODO("Return existing user with that email on db --> if there is none return ERROR CODE: 404 or 401." +
              " please do not expose the password. return all user's detail except the password.")
        return UserBoundary()
    }

    @RequestMapping(
        path = ["/users/login/{email}"],
        method = [RequestMethod.GET],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun login(@PathVariable email: String, @RequestParam(name = "password") password:String): UserBoundary {
        TODO("Return existing user with that email on db --> if there is none or the email and password " +
                "are not match return ERROR CODE: 404 or 401." +
                "please do not expose the password. return all user's detail except the password.")
        return UserBoundary()
    }

    @RequestMapping(
        path = ["/users/{email}"],
        method = [RequestMethod.PUT],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun update(@PathVariable email: String, @RequestBody user:UserBoundary) {
        TODO("Update existing user with that email on db --> if there is none\n" +
                "return ERROR CODE: 404 or 401.\n" +
                "please do not allow to change the email attribute .")

    }

    @RequestMapping(
        path = ["/users"],
        method = [RequestMethod.DELETE]
    )
    fun deleteAllUsers() {
        TODO("DELETE all users on db")
    }

    @RequestMapping(
        path = ["/users/search"],
        method = [RequestMethod.GET],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun searchUserByAttribute(
        @RequestParam(name = "size") size:Int,
        @RequestParam(name = "page") page:Int,
        @RequestParam(name = "sortBy") sortAttribute:String,
        @RequestParam(name = "sortOrder") sortOrder:String
                        ): List<UserBoundary> {
        TODO("returns an array of all the users already saved in the service and enables pagination." +
                "Please note that this action also does not reveal user passwords" +
                "sortBy = email/firstName/lastName/birthdate, default value = email" +
                "sortOrder = ASC/DESC, default value = ASC." +
                "return empty array if there is not compatible result")

    }

//    @RequestMapping(
//        path = ["/users/search"],
//        method = [RequestMethod.GET],
//        consumes = [MediaType.APPLICATION_JSON_VALUE],
//        produces = [MediaType.APPLICATION_JSON_VALUE]
//    )
//    fun searchUserByEmailDomain(
//        @RequestParam(name = "criteriaType") byEmailDomain:String,
//        @RequestParam(name = "criteriaValue") domainValue:String,
//        @RequestParam(name = "size") size:Int,
//        @RequestParam(name = "page") page:Int,
//        @RequestParam(name = "sortBy") sortAttribute:String,
//        @RequestParam(name = "sortOrder") sortOrder:String
//    ): List<UserBoundary> {
//        TODO("returns an array of users whose email address belongs to a certain domain (DOMAIN)" +
//                " passed as a value parameter and enables pagination. " +
//                "If there are no suitable users to return, the operation will return an empty array.\n" +
//                "Please note that this action also does not reveal user passwords")
//    }
//    @RequestMapping(
//        path = ["/users/search"],
//        method = [RequestMethod.GET],
//        consumes = [MediaType.APPLICATION_JSON_VALUE],
//        produces = [MediaType.APPLICATION_JSON_VALUE]
//    )
//    fun searchUserByBirthYear(
//        @RequestParam(name = "criteriaType") byBirthYear:String,
//        @RequestParam(name = "criteriaValue") birthYearValue:String,
//        @RequestParam(name = "size") size:Int,
//        @RequestParam(name = "page") page:Int,
//        @RequestParam(name = "sortBy") sortAttribute:String,
//        @RequestParam(name = "sortOrder") sortOrder:String
//    ): List<UserBoundary> {
//        TODO("returns an array of users whose BirthYear belongs to a certain BirthYear" +
//                " passed as a value parameter and enables pagination. " +
//                "If there are no suitable users to return, the operation will return an empty array.\n" +
//                "Please note that this action also does not reveal user passwords")
//    }
//    @RequestMapping(
//        path = ["/users/search"],
//        method = [RequestMethod.GET],
//        consumes = [MediaType.APPLICATION_JSON_VALUE],
//        produces = [MediaType.APPLICATION_JSON_VALUE]
//    )
//    fun searchUserByRole(
//        @RequestParam(name = "criteriaType") byRole:String,
//        @RequestParam(name = "criteriaValue") roleValue:String,
//        @RequestParam(name = "size") size:Int,
//        @RequestParam(name = "page") page:Int,
//        @RequestParam(name = "sortBy") sortAttribute:String,
//        @RequestParam(name = "sortOrder") sortOrder:String
//    ): List<UserBoundary> {
//        TODO("returns an array of users whose Role belongs to a certain Role" +
//                " passed as a value parameter and enables pagination. " +
//                "If there are no suitable users to return, the operation will return an empty array.\n" +
//                "Please note that this action also does not reveal user passwords")
//    }
}