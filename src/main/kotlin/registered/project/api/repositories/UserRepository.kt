package registered.project.api.repositories

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository
import registered.project.api.entities.Card
import registered.project.api.entities.User
@Repository
interface UserRepository:JpaRepository<User,Long> {
    @Query("SELECT u FROM tb_user u WHERE u.email = :email")
    fun findByEmailCustom(email: String): User?

    @Query("SELECT cards FROM tb_user u WHERE u.email = :email")
    fun listCards(email:String,pageable: Pageable):MutableList<Card>?
}