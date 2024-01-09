package registered.project.api.projections

import org.springframework.http.ResponseEntity
import registered.project.api.dtos.AddCardDTO
import registered.project.api.dtos.ListCardsDTO

interface CardProjection {

    fun addCardUser(addCardDTO: AddCardDTO): ResponseEntity<Any>

    fun listCardsUser(offset:Int): ListCardsDTO?
}