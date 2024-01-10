package registered.project.api.repositories

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import registered.project.api.dtos.UserDTO
import registered.project.api.dtos.UsersToDeleteDTO
import registered.project.api.entities.User
import registered.project.api.enums.UserRole

@Repository
interface UserRepository:JpaRepository<User,Long> {
    @Query("SELECT u FROM tb_user u WHERE u.email = :email")
    fun findByEmailCustom(email: String): User?


    @Query("SELECT u.id as id, u.nameUser as name, u.cardsNumbers as numCards FROM tb_user u WHERE u.role = :role")
    fun listUsersToAdm(pageable: Pageable,role:UserRole): MutableList<UserDTO>?

    @Query("SELECT u FROM tb_user u WHERE u.role = :role")
    fun listToAdm(pageable: Pageable,role:UserRole): MutableList<User>?


    @Query(nativeQuery = true,value="SELECT id, nameUser AS name, cardsNumbers AS numCards, email FROM tb_user WHERE isToDelete = :toDelete")
    fun listUsersToDelete(pageable: Pageable,toDelete:Int):MutableList<UsersToDeleteDTO>?
}