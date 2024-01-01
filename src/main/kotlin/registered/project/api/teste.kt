package registered.project.api

import jakarta.persistence.*

@Entity(name="testeDB")
class teste(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long?=null,
    var name:String?=null
) {
}