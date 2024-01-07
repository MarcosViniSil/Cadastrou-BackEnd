package registered.project.api.projections

import org.springframework.http.ResponseEntity
import registered.project.api.dtos.LoginDTO
import registered.project.api.dtos.RegisterAdmDTO
import registered.project.api.dtos.RegisterDTO
import registered.project.api.enums.UserRole

interface UserProjection {

    fun login( data: LoginDTO): ResponseEntity<Any>

    fun register(name:String,password:String,email:String,role: UserRole):ResponseEntity<Any>

    fun registerUser(registerDto: RegisterDTO):ResponseEntity<Any>

    fun registerAdm( registerAdmDTO: RegisterAdmDTO):ResponseEntity<Any>
}