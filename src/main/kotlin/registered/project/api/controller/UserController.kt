package registered.project.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import registered.project.api.dtos.LoginDTO
import registered.project.api.dtos.RegisterAdmDTO
import registered.project.api.dtos.RegisterDTO
import registered.project.api.service.auth.AuthorizationService

@RestController
@RequestMapping("/User")
class UserController(
    private val authorizationService: AuthorizationService
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
}