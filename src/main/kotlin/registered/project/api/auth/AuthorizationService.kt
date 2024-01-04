package registered.project.api.auth

import org.springframework.beans.factory.annotation.Value
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
import registered.project.api.repositories.UserRepository
import registered.project.api.security.TokenService
import java.util.regex.Pattern



@Service
class AuthorizationService(
    private val context: ApplicationContext,
    private val userRepository: UserRepository,
    private val tokenService: TokenService,


) : UserDetailsService {

    private lateinit var authenticationManager: AuthenticationManager

    @Value("\${api-hash-adm}")
    private lateinit var hashAdm: String

    @Value("\${api-password-adm}")
    private lateinit var passwordAdm: String
    
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
    fun register(name:String,password:String,email:String,role:UserRole):ResponseEntity<Any>{
        val user = userRepository?.findByEmailCustom(email)
        if (user != null) {
            return ResponseEntity.badRequest().build()
        }
        val encryptedPassword = BCryptPasswordEncoder().encode(password)
        val newUser = User()
        newUser.testeN = name
        newUser.email = email
        newUser.password = encryptedPassword
        newUser.role = role
        newUser.createdAt = Date(System.currentTimeMillis())
        userRepository?.save(newUser)
        return ResponseEntity.ok().build()
    }
    fun registerUser(@RequestBody registerDto: RegisterDTO):ResponseEntity<Any> {
        if(this.validateRegister(registerDto.email,registerDto.name,registerDto.password)) {
           return this.register(registerDto.name,registerDto.password,registerDto.email,UserRole.USER)
        }else{
            //TODO Exception some data invalid
            return ResponseEntity.badRequest().build()
        }
    }
    fun registerAdm(@RequestBody registerAdmDTO: RegisterAdmDTO):ResponseEntity<Any>{
        if(this.validateRegisterAdm(registerAdmDTO)){
           return this.register(registerAdmDTO.name,registerAdmDTO.password,registerAdmDTO.email,UserRole.ADMIN)
        }else{
            //TODO some data invalid
        }
        return ResponseEntity.badRequest().build()
    }

    fun verifyToken(token:String):String{
        if(this.validateToken(token)) {
            return tokenService.validateToken(token)
        }else{
            return "INVALID TOKEN"
        }
    }

    private fun validateLogin(data: LoginDTO):Boolean{
        if((data.email.length>6 && this.isEmailValid(data.email)) && (data.password.length>=8 && data.password.length<=20)){
            return true
        }else{
            //TODO Exception email and/or password invalid
            return false
        }
    }

    private fun validateRegister(email:String,name:String,password:String):Boolean{
        if((email.length>6 && this.isEmailValid(email)) && (password.length>=8 && password.length<=20) && name.length>2){
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
    private fun validateRegisterAdm(registerAdmDTO: RegisterAdmDTO):Boolean{
        if((this.validateRegister(registerAdmDTO.email,registerAdmDTO.name,registerAdmDTO.password))
            && this.validateHashAndPasswordAdm(registerAdmDTO.hash,registerAdmDTO.passwordAdm)){
            return true
        }else{
            //TODO Exception email and/or password,name invalid
            return false
        }
    }
    fun validateHashAndPasswordAdm(hash:String,passwordAdm:String):Boolean{
        if((hash == this.hashAdm) && passwordAdm == this.passwordAdm){
            return true
        }else{
            return false
        }

    }
    fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$"
        val pattern = Pattern.compile(emailRegex)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }
}