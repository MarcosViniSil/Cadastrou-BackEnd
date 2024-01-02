package registered.project.api.entities

import jakarta.persistence.*
import registered.project.api.enums.UserRole

@MappedSuperclass
open class Person(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Long? = null,
     var name: String? = null,
     var email: String? = null,
     var password: String? = null,
    @Enumerated(EnumType.STRING)
     var role: UserRole? = null


) {

}