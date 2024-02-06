package registered.project.api.repositories

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import registered.project.api.dtos.UserDTO
import registered.project.api.dtos.UserProfileDTO
import registered.project.api.dtos.UsersToDeleteDTO
import registered.project.api.entities.User
import registered.project.api.enums.UserRole

@Repository
interface UserRepository:JpaRepository<User,Long> {
    @Query("SELECT u FROM tb_user u WHERE u.email = :email")
    fun findByEmailCustom(email: String): User?

    @Query("SELECT u.id as id, u.nameUser as name, u.cardsNumbers as numCards FROM tb_user u WHERE u.role = :role")
    fun listUsersToAdm(pageable: Pageable,role:UserRole): MutableList<UserDTO>?

    @Query("SELECT u.nameUser as name, u.email as email, u.cardsNumbers as numCards, u.createdAt as createdAt FROM tb_user u WHERE u.email = :email")
    fun getProfileUser(email:String): UserProfileDTO?

    @Query("SELECT u FROM tb_user u WHERE u.role = :role")
    fun listToAdm(pageable: Pageable,role:UserRole): MutableList<User>?

    @Query("SELECT u.role as role FROM tb_user u WHERE u.email = :email")
    fun getRoleUser(email:String): UserRole?

    @Query(nativeQuery = true,value="SELECT id, name_user AS name, cards_numbers AS numCards, email FROM tb_user WHERE is_to_delete = :toDelete")
    fun listUsersToDelete(pageable: Pageable,toDelete:Int):MutableList<UsersToDeleteDTO>?
}