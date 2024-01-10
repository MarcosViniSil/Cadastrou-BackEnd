package registered.project.api.service.auth

import java.sql.Date;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import registered.project.api.dtos.LoginDTO
import registered.project.api.dtos.RegisterAdmDTO
import registered.project.api.dtos.RegisterDTO
import registered.project.api.dtos.TokenDTO
import registered.project.api.entities.User
import registered.project.api.enums.UserRole
import registered.project.api.projections.UserProjection
import registered.project.api.repositories.UserRepository
import registered.project.api.security.TokenService
import registered.project.api.service.validation.ValidationAuth

@Service
class AuthorizationService(
    private val context: ApplicationContext,
    private val userRepository: UserRepository,
    private val tokenService: TokenService,
    private var validationAuth: ValidationAuth


) : UserDetailsService, UserProjection {

    private lateinit var authenticationManager: AuthenticationManager

    override fun loadUserByUsername(email: String): User? {
        return userRepository!!.findByEmailCustom(email)
    }

    override fun login(@RequestBody data: LoginDTO): ResponseEntity<Any> {
        if (validationAuth.validateLogin(data)) {
            authenticationManager = context.getBean(AuthenticationManager::class.java)
            val usernamePassword = UsernamePasswordAuthenticationToken(data.email, data.password)
            val auth = authenticationManager!!.authenticate(usernamePassword)
            val token: String = tokenService!!.generatedToken(auth.principal as User)
            return ResponseEntity.ok<Any>(TokenDTO(token))
        } else {
            return ResponseEntity.badRequest().build()
        }
    }

    override fun register(name: String, password: String, email: String, role: UserRole): ResponseEntity<Any> {
        val user = userRepository?.findByEmailCustom(email)
        val encryptedPassword = BCryptPasswordEncoder().encode(password)
        if (user != null) {
            user.nameUser = name
            user.email = email
            user.password = encryptedPassword
            user.role = role
            user.updatedAt = Date(System.currentTimeMillis())
            userRepository?.save(user)
            return ResponseEntity.ok().build()
        } else {
            val newUser = User()
            newUser.nameUser = name
            newUser.email = email
            newUser.password = encryptedPassword
            newUser.role = role
            newUser.createdAt = Date(System.currentTimeMillis())
            userRepository?.save(newUser)
            return ResponseEntity.ok().build()
        }

    }

    override fun registerUser(@RequestBody registerDto: RegisterDTO): ResponseEntity<Any> {
        if (validationAuth.validateRegister(registerDto.email, registerDto.name, registerDto.password)) {
            return this.register(registerDto.name, registerDto.password, registerDto.email, UserRole.USER)
        } else {
            //TODO Exception some data invalid
            return ResponseEntity.badRequest().build()
        }
    }

    override fun registerAdm(@RequestBody registerAdmDTO: RegisterAdmDTO): ResponseEntity<Any> {
        if (validationAuth.validateRegisterAdm(registerAdmDTO)) {
            return this.register(registerAdmDTO.name, registerAdmDTO.password, registerAdmDTO.email, UserRole.ADMIN)
        } else {
            //TODO some data invalid
        }
        return ResponseEntity.badRequest().build()
    }

    fun verifyToken(token: String): String {
        if (validationAuth.validateToken(token)) {
            return tokenService.validateToken(token)
        } else {
            return "INVALID TOKEN"
        }
    }

}