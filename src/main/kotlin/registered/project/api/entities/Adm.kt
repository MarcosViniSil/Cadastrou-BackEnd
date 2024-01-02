package registered.project.api.entities

import jakarta.persistence.*

@Entity(name="tb_adm")
class Adm(
    @OneToMany
    var usersToDelete:MutableList<Card>? =null,

    @OneToMany
    var usersView:MutableList<Card>? =null
):Person() {
}