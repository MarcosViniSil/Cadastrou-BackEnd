package registered.project.api.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import registered.project.api.enums.FrequencyCard
import java.sql.Date

@Entity(name = "tb_card")
class Card(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String? = null,
    var description: String? = null,
    var dateFinish: Date? = null,
    var colorNumber: Int? = null,
    @Enumerated(EnumType.STRING)
    var frequency: FrequencyCard? = null,
    @ManyToOne
    @JsonBackReference
    var user: User? = null


) {
}