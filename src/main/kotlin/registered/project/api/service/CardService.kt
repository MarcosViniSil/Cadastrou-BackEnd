package registered.project.api.service

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import registered.project.api.dtos.AddCardDTO
import registered.project.api.dtos.ListCardsDTO
import registered.project.api.entities.Card
import registered.project.api.entities.User
import registered.project.api.exceptions.TokenInvalidException
import registered.project.api.exceptions.UserNotExistsException
import registered.project.api.projections.CardProjection
import registered.project.api.repositories.CardRepository
import registered.project.api.repositories.UserRepository
import registered.project.api.service.auth.AuthorizationService
import registered.project.api.service.header.RecoverToken
import registered.project.api.service.validation.ValidationCard
import java.sql.Date

@Service
class CardService(
    private val userRepository: UserRepository,
    private val authorizationService: AuthorizationService,
    private val validationCard: ValidationCard,
    private val cardRepository: CardRepository,
    private val recoverToken: RecoverToken
) : CardProjection {

    private fun findUserByEmail(): User? {
        val email = this.responseToken()
        if (email != null) {
            val user: User? = userRepository.findByEmailCustom(email)
            if (user != null) {
                return user
            }

        }
        return null
    }

    private fun findEmail(token: String): String? {
        val responseToken: String = this.authorizationService.verifyToken(token)
        if (responseToken != "INVALID TOKEN") {
            return responseToken
        }else{
            throw TokenInvalidException("Token invalid")
        }
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

    private fun responseToken(): String? {
        val token: String = this.recoverToken.getToken()
        return this.findEmail(token)
    }

    override fun addCardUser(addCardDTO: AddCardDTO): ResponseEntity<Any> {
        if (validationCard.validateCard(addCardDTO.name, addCardDTO.description, addCardDTO.dateFinish)) {
            val user: User? = this.findUserByEmail()
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
                return ResponseEntity.ok().build()
            } else {
                throw UserNotExistsException("User not exists, impossible add card")
            }

        } else {
            return ResponseEntity.badRequest().build()
        }

    }

    override fun listCardsUserExpiredOrNot(offset: Int, expired: Boolean): ListCardsDTO? {
        if (validationCard.validateOffset(offset)) {
            val user: User? = this.findUserByEmail()
            if (user != null) {

                val listCards: MutableList<Card>?
                val pageable: Pageable = PageRequest.of(offset, 4)

                if (!expired) {
                    listCards = cardRepository.listCardsUser(user.id!!, pageable)
                } else {
                    listCards = cardRepository.listCardsUserExpired(user.id!!, pageable)
                }
                val cards: ListCardsDTO = ListCardsDTO(card = listCards)

                return cards

            }else{
                throw UserNotExistsException("User not exists, impossible list card")
            }
        }
        return null

    }

    override fun listCardsUser(offset: Int): ListCardsDTO? {
        return this.listCardsUserExpiredOrNot(offset, false)
    }

    override fun listCardsUserExpired(offset: Int): ListCardsDTO? {
        return this.listCardsUserExpiredOrNot(offset, true)
    }

    override fun deleteCard(idCard: Long?) {
        if (validationCard.validateCardDelete(idCard)) {
            val user: User? = this.findUserByEmail()
            if (user != null) {
                val cardToDelete: Card = cardRepository.findByIdAndUser_Id(idCard, user.id)

                user.cards?.remove(cardToDelete)
                user.cardsNumbers = user.cardsNumbers?.minus(1)
                user.updatedAt = Date(System.currentTimeMillis())

                userRepository.save(user)
                cardRepository.deleteById(idCard!!)
            }else{
                throw UserNotExistsException("User not exists, impossible add card")
            }

        }

    }


}