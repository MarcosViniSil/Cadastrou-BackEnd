package registered.project.api.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import registered.project.api.dtos.LoginDTO
import registered.project.api.entities.User
import registered.project.api.enums.UserRole
import registered.project.api.repositories.UserRepository

@RestController
class teste(private var userRepository:UserRepository){
    @PostMapping("/user")
    fun cadUser(@RequestBody user2:LoginDTO):String{
        var user = User()
        user.name=user2.name
        user.email=user2.email
        user.password=user2.password
        user.role=UserRole.USER
        userRepository.save(user)
        return "sucesso"
    }
}