package registered.project.api.dtos

interface UserDTO {
    fun getId(): Long?
    fun getName(): String?
    fun getNumCards(): Int?
}