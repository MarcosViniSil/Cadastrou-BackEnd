package registered.project.api.dtos
import java.util.Date;
interface UserProfileDTO {

    fun getName(): String?
    fun getEmail(): String?
    fun getNumCards():Int?
    fun getCreatedAt():Date?
}