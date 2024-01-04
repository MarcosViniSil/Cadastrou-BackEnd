package registered.project.api.auth

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        authenticationManager = context.getBean(AuthenticationManager::class.java)
        val usernamePassword = UsernamePasswordAuthenticationToken(data.email, data.password)
        val auth = authenticationManager!!.authenticate(usernamePassword)
        val token: String = tokenService!!.generatedToken(auth.principal as User)
        return ResponseEntity.ok<Any>(TokenDTO(token))
    }

    fun registerUser(@RequestBody registerDto: RegisterDTO): ResponseEntity<Any> {
        print(registerDto)
        val user=userRepository?.findByEmailCustom(registerDto.email)
        if (user!=null){
            return ResponseEntity.badRequest().build()
        }
        val encryptedPassword = BCryptPasswordEncoder().encode(registerDto.password)
        val newUser = User()
        newUser.testeN=registerDto.name
        newUser.email=registerDto.email
        newUser.password=encryptedPassword
        newUser.role=UserRole.USER
        newUser.createdAt=Date(System.currentTimeMillis())
        userRepository?.save(newUser)
        return ResponseEntity.ok().build()
    }
}