package registered.project.api.dtos
import registered.project.api.enums.FrequencyCard
import java.sql.Date

class AddCardDTO(
    var name:String,
    var description:String,
    var dateFinish:Date,
    var colorNumber:Int,
    var frequency: FrequencyCard
) {
}