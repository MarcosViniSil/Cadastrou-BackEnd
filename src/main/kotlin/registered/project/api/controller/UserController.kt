package registered.project.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import registered.project.api.dtos.*
import registered.project.api.entities.User
import registered.project.api.service.AdmService
import registered.project.api.service.UserService
import registered.project.api.service.auth.AuthorizationService
@CrossOrigin(origins = arrayOf("http://localhost:4200","https://cadastrou.vercel.app/"))
@RestController
@RequestMapping("/User")
class UserController(
    private val authorizationService: AuthorizationService,
    private val admService: AdmService,
    private val userService: UserService

) {
    @PostMapping("/Register")
    fun cadUser(@RequestBody user2: RegisterDTO): ResponseEntity<Any> {
        return authorizationService.registerUser(user2)

    }

    @PostMapping("/Login")
    fun loginUser(@RequestBody user2: LoginDTO): ResponseEntity<Any> {

        return authorizationService.login(user2)

    }

    @PostMapping("/AdmRegister")
    fun cadAdm(@RequestBody user2: RegisterAdmDTO): ResponseEntity<Any> {

        return authorizationService.registerAdm(user2)

    }

    @PutMapping("/UpdateUser")
    fun updateUser(@RequestBody userUpdate: RegisterDTO): ResponseEntity<Any> {

        return authorizationService.updaterUser(userUpdate)

    }

    @PutMapping("/UpdateAdm")
    fun updateAdm(@RequestBody admUpdate: RegisterAdmDTO): ResponseEntity<Any> {

        return authorizationService.updateAdm(admUpdate)

    }

    @DeleteMapping("/Delete/{id}")
    fun deleteUser(@PathVariable("id") id: Long?) {
        admService.deleteUser(id)
    }

    @GetMapping("/List/{offset}")
    fun listUsersToAdm(@PathVariable("offset") offset: Int): MutableList<UserDTO>? {
        return admService.listUsers(offset)
    }

    @GetMapping("/List/Delete/{offset}")
    fun listUsersToDelete(@PathVariable("offset") offset:Int): MutableList<UsersToDeleteDTO>? {
        return admService.listUsersToDelete(offset)
    }

    @GetMapping("/Request/Delete")
    fun requestDeleteAccount(): ResponseEntity<Any> {
        return userService.requestDeleteAccount()
    }

    @GetMapping("/Validate/Email/{email}")
   fun validateEmail(@PathVariable("email") email:String):CodeEmailDTO?{
        return userService.verifyEmail(email)
   }
    @PostMapping("/Validate/Code")
    fun verifyCode(@RequestBody codes:VerifyEmailDTO):ResponseEntity<Any>{
        return userService.validateCode(codes.codeUser,codes.codeToken)
    }

    @GetMapping("/role")
    fun getRole():RoleUserDTO?{
        return this.userService.getRoleUserByEmail()
    }
    @GetMapping("/profile")
    fun getProfile():UserProfileDTO?{
        return this.userService.getProfileUser()
    }
    @PostMapping("/Update/Password")
    fun updatePassword(@RequestBody newPassword:NewPasswordDTO):ResponseEntity<Any>{
        return userService.updatePassword(newPassword.password)
    }

}