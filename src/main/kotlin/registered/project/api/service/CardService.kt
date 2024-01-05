package registered.project.api.service

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import registered.project.api.dtos.AddCardDTO
import registered.project.api.entities.Card
import registered.project.api.entities.User
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
) {

    fun addCardUser(addCardDTO: AddCardDTO): ResponseEntity<Any> {
        val responseToken:String = this.authorizationService.verifyToken(addCardDTO.token)
        if(responseToken!="INVALID TOKEN"){
            if(validationCard.validateCard(addCardDTO.name,addCardDTO.description,addCardDTO.dateFinish)){
                val user: User? = userRepository.findByEmailCustom(responseToken)
                if(user!=null){
                    val card:Card = this.createCard(addCardDTO)
                    var listCards:MutableList<Card>? = user.cards
                    if(listCards==null){
                        listCards = mutableListOf(card)
                    }else{
                        listCards.add(card)
                    }
                    user.cards=listCards
                    user.updatedAt= Date(System.currentTimeMillis())
                    user.cardsNumbers=user.cardsNumbers+1
                    cardRepository.save(card)
                    userRepository.save(user)

                }

            }
        }else{
            return ResponseEntity.badRequest().build()
        }
        return ResponseEntity.ok().build()
    }
  private  fun createCard(addCardDTO: AddCardDTO): Card {
        return Card(
            name = addCardDTO.name, description = addCardDTO.description,
            dateFinish = addCardDTO.dateFinish, colorNumber = generateCodeCard(), frequency = addCardDTO.frequency
        )
    }

   private fun generateCodeCard():Int{
        return (1..6).random()
    }
}