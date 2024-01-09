package registered.project.api.service

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import registered.project.api.entities.User
import registered.project.api.enums.UserRole
import registered.project.api.repositories.UserRepository
import registered.project.api.service.auth.AuthorizationService
import registered.project.api.service.email.EmailService

@Service
class UserService(
    private val userRepository: UserRepository,
    private val recoverToken: RecoverToken,
    private val authorizationService: AuthorizationService,
    private val admService: AdmService
) {

    private fun findUser(token: String): String? {
        val responseToken: String = this.authorizationService.verifyToken(token)
        if (responseToken != "INVALID TOKEN") {
            return responseToken

        }
        return null
    }

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
                }


        }
        return ResponseEntity.badRequest().build()
    }


}