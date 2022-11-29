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
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getUsersByEmail(@PathVariable email: String): UserBoundary {
//      Return existing user with that email on db --> if there is none return ERROR CODE: 404
//       please do not expose the password. return all user's detail except the password.
        return this.userService
            .getSpecificUser(email)
            .orElseThrow { UserNotFoundException("could not find user by email $email") }
    }

    @RequestMapping(
        path = ["/users/login/{email}"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun login(@PathVariable email: String, @RequestParam(name = "password") password:String): UserBoundary {
//     Return existing user with that email on db --> if there is none or the email and password are not match return ERROR CODE: 404
//     please do not expose the password. return all user's detail except the password.
        return this.userService
            .login(
               email, password
            )
            .orElseThrow { UserNotFoundException("could not find user") }
    }

    @RequestMapping(
        path = ["/users/{email}"],
        method = [RequestMethod.PUT],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun update(@PathVariable email: String, @RequestBody user:UserBoundary) {
//        TODO Update existing user with that email on db --> if there is none\n
//             return ERROR CODE: 404 or 401. please do not allow to change the email attribute
        this.userService.updateUser(email,user)

    }

    @RequestMapping(
        path = ["/users"],
        method = [RequestMethod.DELETE]
    )
    fun deleteAllUsers() {
//     DELETE all users on db
        this.userService
            .deleteAll()
    }

    @RequestMapping(
        path = ["/users/search"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun searchUserByAttribute(
        @RequestParam(name = "criteriaType", required = false, defaultValue = "") criteriaType:String,
        @RequestParam(name = "criteriaValue", required = false, defaultValue = "") criteriaValue:String,
        @RequestParam(name = "size", required = false, defaultValue = "10") size:Int,
        @RequestParam(name = "page", required = false, defaultValue = "0") page:Int,
        @RequestParam(name = "sortBy", required = false, defaultValue = "email") sortAttribute:String,
        @RequestParam(name = "sortOrder", required = false, defaultValue = "ASC") sortOrder:String
                        ): List<UserBoundary> {
        return this.userService
            .search(
                criteriaType,
                criteriaValue,
                size,
                page,
                sortAttribute,
                sortOrder
            )

    }

}