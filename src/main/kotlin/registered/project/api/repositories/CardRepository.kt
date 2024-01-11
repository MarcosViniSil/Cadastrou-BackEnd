package registered.project.api.repositories


import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import registered.project.api.entities.Card

@Repository
interface CardRepository : JpaRepository<Card, Long> {

    @Query("SELECT c FROM tb_card c WHERE c.user.id = :userId AND c.dateFinish >= CURRENT_DATE ORDER BY dateFinish ASC")
    fun listCardsUser(userId: Long, pageable: Pageable): MutableList<Card>?

    @Query("SELECT c FROM tb_card c WHERE c.user.id = :userId AND c.dateFinish < CURRENT_DATE ORDER BY dateFinish ASC")
    fun listCardsUserExpired(userId: Long, pageable: Pageable): MutableList<Card>?

    fun findByIdAndUser_Id(cardId: Long?, userId: Long?):Card

}

