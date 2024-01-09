package registered.project.api.service

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import registered.project.api.dtos.UserDTO
import registered.project.api.dtos.UsersToDeleteDTO
import registered.project.api.entities.User
import registered.project.api.enums.UserRole
import registered.project.api.repositories.UserRepository
import registered.project.api.service.auth.AuthorizationService
import registered.project.api.service.email.EmailService

@Service
class AdmService(
    private val userRepository: UserRepository,
    private val authorizationService: AuthorizationService,
    private val emailService: EmailService
) {

    private fun findUser(token: String): String? {
        val responseToken: String = this.authorizationService.verifyToken(token)
        if (responseToken != "INVALID TOKEN") {
            return responseToken

        }
        return null
    }

    fun deleteUser(id: Long?) {
        if (id != null) {
            this.userRepository.deleteById(id)


        }
    }

    fun listUsers(offset: Int): MutableList<UserDTO>? {
        if(offset>=0) {
            val pageable: Pageable = PageRequest.of(offset, 4)
            val users = userRepository.listUsersToAdm(pageable,UserRole.ADMIN)
            return users
        }

        return null
    }

    fun alertAdmToDeleteUser(user:User){
        val pageable: Pageable = PageRequest.of(0, 2)
        val adms:MutableList<User>? = userRepository.listToAdm(pageable,UserRole.ADMIN)
        adms?.forEach { e -> this.emailService.sendEmailToAdm(e,user) }
    }

    fun listUsersToDelete():MutableList<UsersToDeleteDTO>?{
        return userRepository.listUsersToDelete(1)
    }



}