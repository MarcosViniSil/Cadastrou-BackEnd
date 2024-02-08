package registered.project.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import registered.project.api.dtos.AddCardDTO
import registered.project.api.dtos.ListCardsDTO
import registered.project.api.service.CardService
@CrossOrigin(origins = arrayOf("http://localhost:4200","https://cadastrou.vercel.app/"))
@RestController
@RequestMapping("/Card")
class CardController(
    private val cardService: CardService
) {

    @PostMapping("/Register")
    fun cardRegister(@RequestBody user2: AddCardDTO): ResponseEntity<Any> {
        return cardService.addCardUser(user2)
    }

    @GetMapping("/{offset}")
    fun listCardsUser(@PathVariable("offset") offset:Int): ListCardsDTO? {
        return cardService.listCardsUser(offset)
    }

    @GetMapping("/Expired/{offset}")
    fun listCardsUserExpired(@PathVariable("offset") offset:Int): ListCardsDTO? {
        return cardService.listCardsUserExpired(offset)
    }

    @DeleteMapping("/Delete/{id}")
    fun deleteCardUser(@PathVariable("id") id:Long?){
        cardService.deleteCard(id)
    }
}