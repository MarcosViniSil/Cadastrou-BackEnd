package registered.project.api.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import registered.project.api.enums.FrequencyCard
import java.sql.Date

@Entity(name = "tb_card")
class Card(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Long? = null,
    private var name: String? = null,
    private var description: String? = null,
    private var dateFinish: Date? = null,
    private var colorNumber: Int? = null,
    @Enumerated(EnumType.STRING)
    private var frequency: FrequencyCard? = null,
    @ManyToOne
    @JsonBackReference
     var user:User?=null


) {
}