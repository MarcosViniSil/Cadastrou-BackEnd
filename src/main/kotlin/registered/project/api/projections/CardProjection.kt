package registered.project.api.projections

import org.springframework.http.ResponseEntity
import registered.project.api.dtos.AddCardDTO
import registered.project.api.dtos.ListCardsDTO

interface CardProjection {

    fun addCardUser(addCardDTO: AddCardDTO): ResponseEntity<Any>
    fun listCardsUserExpiredOrNot(offset: Int, expired: Boolean): ListCardsDTO?
    fun listCardsUser(offset:Int): ListCardsDTO?
    fun listCardsUserExpired(offset: Int):ListCardsDTO?
    fun deleteCard(idCard: Long?)
}