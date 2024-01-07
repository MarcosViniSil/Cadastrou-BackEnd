package registered.project.api.service

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import registered.project.api.dtos.AddCardDTO
import registered.project.api.dtos.ListCardsDTO
import registered.project.api.entities.Card
import registered.project.api.entities.User
import registered.project.api.projections.CardProjection
import registered.project.api.repositories.CardRepository
import registered.project.api.repositories.UserRepository
import registered.project.api.service.auth.AuthorizationService
import registered.project.api.service.validation.ValidationCard
import java.sql.Date

@Service
class CardService(
    private val userRepository: UserRepository,
    private val authorizationService: AuthorizationService,
    private val validationCard: ValidationCard,
    private val cardRepository: CardRepository
) : CardProjection {

    private fun findUser(token: String): String? {
        val responseToken: String = this.authorizationService.verifyToken(token)
        if (responseToken != "INVALID TOKEN") {
            return responseToken

        }
        return null
    }

    private fun createCard(addCardDTO: AddCardDTO): Card {
        return Card(
            name = addCardDTO.name, description = addCardDTO.description,
            dateFinish = addCardDTO.dateFinish, colorNumber = generateCodeCard(), frequency = addCardDTO.frequency
        )
    }

    private fun generateCodeCard(): Int {
        return (1..6).random()
    }

    override fun addCardUser(addCardDTO: AddCardDTO): ResponseEntity<Any> {
        val responseToken: String = this.authorizationService.verifyToken(addCardDTO.token)
        if (responseToken != "INVALID TOKEN") {
            if (validationCard.validateCard(addCardDTO.name, addCardDTO.description, addCardDTO.dateFinish)) {
                val user: User? = userRepository.findByEmailCustom(responseToken)
                if (user != null) {
                    val card: Card = this.createCard(addCardDTO)
                    var listCards: MutableList<Card>? = user.cards
                    if (listCards == null) {
                        listCards = mutableListOf(card)
                    } else {
                        listCards.add(card)
                    }
                    if (user.cardsNumbers == null) {
                        user.cardsNumbers = 1
                    } else {
                        user.cardsNumbers = user.cardsNumbers!!.plus(1)
                    }
                    card.user = user
                    user.cards = listCards
                    user.updatedAt = Date(System.currentTimeMillis())
                    cardRepository.save(card)
                    userRepository.save(user)

                }

            }
        } else {
            return ResponseEntity.badRequest().build()
        }
        return ResponseEntity.ok().build()
    }

    override fun listCardsUser(token: String, offset: Int): ListCardsDTO? {
        val email: String? = this.findUser(token)
        if (email != null) {
            var idUser: Long? = userRepository.findId(email)
            if (idUser != null) {
                val pageable: Pageable = PageRequest.of(offset, 4)
                val listCards: MutableList<Card>? = cardRepository.listCardsUser(idUser, pageable)
                val cards: ListCardsDTO = ListCardsDTO(card = listCards)


                return cards

            }

        }
        return null
    }

    fun deleteCard(token: String, idCard: Long?) {
        val email: String? = this.findUser(token)
        if (email != null && idCard != null) {
            val user: User? = userRepository.findByEmailCustom(email)
            if (user != null) {
                val cardToDelete: Card = cardRepository.findByIdAndUser_Id(idCard, user.id)

                user.cards?.remove(cardToDelete)
                cardRepository.delete(cardToDelete)
                userRepository.save(user)

            }
        }

    }
}