package registered.project.api.service.validation

import org.springframework.stereotype.Service
import registered.project.api.exceptions.*
import java.sql.Date

@Service
class ValidationCard {


    fun validateCard(name: String, description: String, dateFinish: Date): Boolean {
        val millis = System.currentTimeMillis()
        val date = Date(millis)
        if (name.length > 2) {
            if (description.length >= 2) {
                if (dateFinish.after(date)) {
                    return true
                } else {
                    throw DateCardInvalidException("date must be after date actual")
                }
            } else {
                throw LengthDescriptionInvalidException("length description invalid")
            }

        } else {
            throw LengthNameInvalidException("length name invalid")
        }
    }

    fun validateOffset(offset: Int): Boolean {
       if(offset >= 0){
           return true
       }else{
           throw OffSetInvalidException("offset Invalid")
       }
    }

    fun validateCardDelete(idCard: Long?): Boolean {
        if (idCard != null && idCard > 0) {
                return true
        }else{
            throw IdCardInvalidException("id cannot be null and must be bigger then 0")
        }
    }
}