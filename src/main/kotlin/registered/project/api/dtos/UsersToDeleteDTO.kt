package registered.project.api.dtos

interface UsersToDeleteDTO {

    fun getId(): Long?
    fun getName(): String?
    fun getNumCards(): Int?

    fun getEmail(): String?
}