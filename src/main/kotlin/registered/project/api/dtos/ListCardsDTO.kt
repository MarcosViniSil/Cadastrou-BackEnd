package registered.project.api.dtos

import registered.project.api.entities.Card

class ListCardsDTO(
     var card:MutableList<Card>? =null
) {
}