package registered.project.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import registered.project.api.dtos.AddCardDTO
import registered.project.api.dtos.RegisterDTO
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
}