package registered.project.api.service

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import registered.project.api.dtos.UserDTO
import registered.project.api.dtos.UsersToDeleteDTO
import registered.project.api.entities.User
import registered.project.api.enums.UserRole
import registered.project.api.projections.AdmProjection
import registered.project.api.repositories.UserRepository
import registered.project.api.service.email.EmailService

@Service
class AdmService(
    private val userRepository: UserRepository,
    private val emailService: EmailService
):AdmProjection {

    override fun deleteUser(id: Long?) {
        if (id != null) {
            this.userRepository.deleteById(id)
        }
    }

    override fun listUsers(offset: Int): MutableList<UserDTO>? {
        if(offset>=0) {
            val pageable: Pageable = PageRequest.of(offset, 4)
            val users = userRepository.listUsersToAdm(pageable,UserRole.USER)
            return users
        }

        return null
    }

    override fun alertAdmToDeleteUser(user:User){
        val pageable: Pageable = PageRequest.of(0, 8)
        val adms:MutableList<User>? = userRepository.listToAdm(pageable,UserRole.ADMIN)
        adms?.forEach { e -> this.emailService.sendEmailToAdm(e,user) }
    }

    override fun listUsersToDelete(offset:Int):MutableList<UsersToDeleteDTO>?{
        val pageable: Pageable = PageRequest.of(offset, 4)
        return userRepository.listUsersToDelete(pageable,1)
    }



}