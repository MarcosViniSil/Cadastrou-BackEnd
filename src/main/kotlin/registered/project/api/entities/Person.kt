package registered.project.api.entities

import jakarta.persistence.*
import registered.project.api.enums.UserRole

@MappedSuperclass
open class Person(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private var id:Long?=null,
   private var name:String?=null,
   private var email:String?=null,
   private var password:String?=null,
   @Enumerated(EnumType.STRING)
   private var role:UserRole?=null


) {

}