package registered.project.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import registered.project.api.dtos.AddCardDTO
import registered.project.api.dtos.ListCardsDTO
import registered.project.api.dtos.RegisterDTO
import registered.project.api.entities.Card
import registered.project.api.service.CardService

@RestController
@RequestMapping("/Card")
class CardController(
    private val cardService: CardService
) {

    @PostMapping("/Register")
    fun cardRegister(@RequestBody user2: AddCardDTO): ResponseEntity<Any> {
        return cardService.addCardUser(user2)
    }
    @GetMapping("/{token}/{offset}")
    fun listCardsUser(@PathVariable("token") token:String,@PathVariable("offset") offset:Int): ListCardsDTO? {
        return cardService.listCardsUser(token,offset)
    }
}