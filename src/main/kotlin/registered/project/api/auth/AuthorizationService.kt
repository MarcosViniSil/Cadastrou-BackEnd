package registered.project.api.auth

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
import registered.project.api.dtos.RegisterDTO
import registered.project.api.dtos.TokenDTO
import registered.project.api.entities.User
import registered.project.api.enums.UserRole
import registered.project.api.repositories.UserRepository
import registered.project.api.security.TokenService



@Service
class AuthorizationService(
    private val context: ApplicationContext,
    private val userRepository: UserRepository,
    private val tokenService: TokenService,


) : UserDetailsService {

    private lateinit var authenticationManager: AuthenticationManager
    
    override fun loadUserByUsername(email: String): User? {
        return userRepository!!.findByEmailCustom(email)
    }

    fun login( @RequestBody data: LoginDTO): ResponseEntity<Any> {
        if(this.validateLogin(data)) {
            authenticationManager = context.getBean(AuthenticationManager::class.java)
            val usernamePassword = UsernamePasswordAuthenticationToken(data.email, data.password)
            val auth = authenticationManager!!.authenticate(usernamePassword)
            val token: String = tokenService!!.generatedToken(auth.principal as User)
            return ResponseEntity.ok<Any>(TokenDTO(token))
        }else{
            return ResponseEntity.badRequest().build()
        }
    }

    fun registerUser(@RequestBody registerDto: RegisterDTO): ResponseEntity<Any> {
        if(this.validateRegister(registerDto)) {
            val user = userRepository?.findByEmailCustom(registerDto.email)
            if (user != null) {
                return ResponseEntity.badRequest().build()
            }
            val encryptedPassword = BCryptPasswordEncoder().encode(registerDto.password)
            val newUser = User()
            newUser.testeN = registerDto.name
            newUser.email = registerDto.email
            newUser.password = encryptedPassword
            newUser.role = UserRole.USER
            newUser.createdAt = Date(System.currentTimeMillis())
            userRepository?.save(newUser)
            return ResponseEntity.ok().build()
        }else{
            return ResponseEntity.badRequest().build()
        }
    }

    fun verifyToken(token:String):String{
        if(this.validateToken(token)) {
            return tokenService.validateToken(token)
        }else{
            return "INVALID TOKEN"
        }
    }

    private fun validateLogin(data: LoginDTO):Boolean{
        if(data.email.length>6 && (data.password.length>=8 && data.password.length<=20)){
            return true
        }else{
            //TODO Exception email and/or password invalid
            return false
        }
    }

    private fun validateRegister(registerDto: RegisterDTO):Boolean{
        if(registerDto.email.length>6 && (registerDto.password.length>=8 && registerDto.password.length<=20) && registerDto.name.length>2){
            return true
        }else{
            //TODO Exception email and/or password,name invalid
            return false
        }
    }
    private fun validateToken(token:String?):Boolean{
        if(token!=null){
            return true
        }else{
            return false
        }
    }
}