package registered.project.api.service

import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import registered.project.api.dtos.CodeEmailDTO
import registered.project.api.dtos.RoleUserDTO
import registered.project.api.dtos.UserProfileDTO
import registered.project.api.entities.User
import registered.project.api.enums.UserRole
import registered.project.api.exceptions.CodeNotEqualsVerifyEmailException
import registered.project.api.exceptions.TokenInvalidException
import registered.project.api.exceptions.UserNotExistsException
import registered.project.api.repositories.UserRepository
import registered.project.api.service.auth.AuthorizationService
import registered.project.api.service.email.RememberUserCardsService
import registered.project.api.service.email.VerifyEmailService
import registered.project.api.service.header.RecoverToken

@Service
class UserService(
    private val userRepository: UserRepository,
    private val recoverToken: RecoverToken,
    private val authorizationService: AuthorizationService,
    private val admService: AdmService,
    private val validateEmail: VerifyEmailService,
    private val rememberUserCardsService: RememberUserCardsService
) {

    fun requestDeleteAccount(): ResponseEntity<Any> {
        val token: String = this.recoverToken.getToken()
        val responseToken: String = this.authorizationService.verifyToken(token)
        if (responseToken != "INVALID TOKEN") {
            val user: User? = userRepository.findByEmailCustom(responseToken)
            if (user != null) {
                user.isToDelete = true
                userRepository.save(user)
                this.admService.alertAdmToDeleteUser(user)
                return ResponseEntity.ok().build()
            } else {
                throw UserNotExistsException("user not exists")
            }


        }
        return ResponseEntity.badRequest().build()
    }

    fun verifyEmail(email: String): CodeEmailDTO? {
        return validateEmail.sendCodeEmail(email)
    }

    fun validateCode(codeUser: String, codeToken: String): ResponseEntity<Any> {
        val code = validateEmail.decrypt(codeToken)
        if (code == codeUser) {
            return ResponseEntity.ok().build()
        } else {
            throw CodeNotEqualsVerifyEmailException("codes not equals")
        }
    }

    fun alertUsers() {
        rememberUserCardsService.alertUsers()
    }

    fun getRoleUserByEmail(): RoleUserDTO? {
        val token: String = this.recoverToken.getToken()
        val responseToken: String = this.authorizationService.verifyToken(token)
        if (responseToken != "INVALID TOKEN") {
            val roleUser: UserRole? = this.userRepository.getRoleUser(responseToken)
            if (roleUser != null) {
                val role: RoleUserDTO = RoleUserDTO(role = roleUser)
                return role
            }
        } else {
            throw TokenInvalidException("token invalid")
        }
        return null
    }

    fun getProfileUser(): UserProfileDTO? {
        val token: String = this.recoverToken.getToken()
        val responseToken: String = this.authorizationService.verifyToken(token)
        if (responseToken != "INVALID TOKEN") {
            val dataProfile: UserProfileDTO? = this.userRepository.getProfileUser(responseToken)
            if (dataProfile != null) {
                return dataProfile
            }
        } else {
            throw TokenInvalidException("token invalid")
        }
        return null
    }

    fun updatePassword(newPassword:String):ResponseEntity<Any> {
        val token: String = this.recoverToken.getToken()
        val responseToken: String = this.authorizationService.verifyToken(token)
        if (responseToken != "INVALID TOKEN") {
            val user:User? = this.userRepository.findByEmailCustom(responseToken)
           if(user!=null){
               val encryptedPassword = BCryptPasswordEncoder().encode(newPassword)
               user.password=encryptedPassword
               this.userRepository.save(user)
               return ResponseEntity.ok().build()
           }
        }else {
            throw TokenInvalidException("token invalid")
        }
        return ResponseEntity.badRequest().build()
    }


}