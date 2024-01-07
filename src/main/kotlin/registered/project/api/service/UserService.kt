package registered.project.api.service

import org.springframework.stereotype.Service
import registered.project.api.repositories.UserRepository
import registered.project.api.service.auth.AuthorizationService

@Service
class UserService(
    private val userRepository: UserRepository,
    private val authorizationService: AuthorizationService
) {

    private fun findUser(token: String): String? {
        val responseToken: String = this.authorizationService.verifyToken(token)
        if (responseToken != "INVALID TOKEN") {
            return responseToken

        }
        return null
    }

    fun deleteUser(token:String){
        val userEmail:String? = this.findUser(token)
        if(userEmail!=null){
            if(userEmail!="INVALID TOKEN"){
                val idUser:Long? = this.userRepository.findId(userEmail)
                if(idUser!=null){
                    this.userRepository.deleteById(idUser)
                }
            }
        }
    }
}