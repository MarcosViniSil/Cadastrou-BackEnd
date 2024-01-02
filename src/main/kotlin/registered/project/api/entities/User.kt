package registered.project.api.entities

import jakarta.persistence.*


@Entity(name="tb_user")
class User(
    @OneToMany
    var cards:MutableList<Card>? =null

): Person() {

}