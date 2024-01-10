package registered.project.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import registered.project.api.dtos.*
import registered.project.api.entities.User
import registered.project.api.service.AdmService
import registered.project.api.service.UserService
import registered.project.api.service.auth.AuthorizationService

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

        return authorizationService.registerUser(userUpdate)

    }

    @PutMapping("/UpdateAdm")
    fun updateAdm(@RequestBody admUpdate: RegisterAdmDTO): ResponseEntity<Any> {

        return authorizationService.registerAdm(admUpdate)

    }

    @DeleteMapping("/delete/{id}")
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




}