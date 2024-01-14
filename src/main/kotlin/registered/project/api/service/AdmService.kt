package registered.project.api.service

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import registered.project.api.dtos.UserDTO
import registered.project.api.dtos.UsersToDeleteDTO
import registered.project.api.entities.User
import registered.project.api.enums.UserRole
import registered.project.api.exceptions.IdCardInvalidException
import registered.project.api.exceptions.OffSetInvalidException
import registered.project.api.exceptions.UserNotExistsException
import registered.project.api.projections.AdmProjection
import registered.project.api.repositories.UserRepository
import registered.project.api.service.email.EmailService

@Service
class AdmService(
    private val userRepository: UserRepository,
    private val emailService: EmailService
) : AdmProjection {

    override fun deleteUser(id: Long?) {
        if (id != null && id > 0) {
            this.userRepository.deleteById(id)
        } else {
            throw IdCardInvalidException("id cannot be null and must be bigger then 0")
        }
    }

    override fun listUsers(offset: Int): MutableList<UserDTO>? {
        if (offset >= 0) {
            val pageable: Pageable = PageRequest.of(offset, 4)
            val users = userRepository.listUsersToAdm(pageable, UserRole.USER)
            return users
        } else {
            throw OffSetInvalidException("offset invalid")
        }
    }

    override fun alertAdmToDeleteUser(user: User) {
        val pageable: Pageable = PageRequest.of(0, 10)
        val adms: MutableList<User>? = userRepository.listToAdm(pageable, UserRole.ADMIN)
        if (adms != null) {
            adms.forEach { e -> this.emailService.sendEmailToAdm(e, user) }
        }

    }

    override fun listUsersToDelete(offset: Int): MutableList<UsersToDeleteDTO>? {
        val pageable: Pageable = PageRequest.of(offset, 4)
        val listusers= userRepository.listUsersToDelete(pageable, 1)
        if(listusers!=null){
            return listusers
        }else{
            throw UserNotExistsException("users invalid")
        }
    }


}