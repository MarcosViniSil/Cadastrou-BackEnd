package registered.project.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import registered.project.api.auth.AuthorizationService

import registered.project.api.dtos.LoginDTO
import registered.project.api.dtos.RegisterDTO
import registered.project.api.repositories.UserRepository


@RestController
class testToken(
    private val authorizationService: AuthorizationService,
    private val userRepository: UserRepository,
) {
    @PostMapping("/Cuser")
    fun cadUser(@RequestBody user2: RegisterDTO):  ResponseEntity<Any> {
        val user=userRepository?.findByEmailCustom(user2.email)
        if (user!=null){
            return ResponseEntity.badRequest().build()
        }
        return authorizationService.registerUser(user2)

    }
    @PostMapping("/Luser")
    fun loginUser(@RequestBody user2: LoginDTO): ResponseEntity<Any> {

        return authorizationService.login(user2)

    }
}