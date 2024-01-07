package registered.project.api.service.validation

import org.springframework.stereotype.Service
import java.sql.Date

@Service
class ValidationCard {


    fun validateCard(name: String, description: String, dateFinish: Date): Boolean {
        val millis = System.currentTimeMillis()
        val date = Date(millis)
        if (name.length > 2 && description.length >= 2 && dateFinish.after(date)) {
            return true
        } else {
            //TODO exceptions invalid data
            return false
        }
    }

    fun validateTokenAndOffset(token: String, offset: Int): Boolean {
        return token != "INVALID TOKEN" && offset >= 0
    }

    fun validateCardDelete(token: String, idCard: Long?): Boolean {
        if (idCard != null) {
            if (token != "INVALID TOKEN" && idCard > 0) {
                return true
            }
        }
        return false
    }
}