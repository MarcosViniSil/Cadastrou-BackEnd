package registered.project.api.service.validation

import org.springframework.stereotype.Service
import java.sql.Date
@Service
class ValidationCard {


    fun validateCard(name:String,description:String,dateFinish:Date):Boolean{
        val millis = System.currentTimeMillis()
        val date = Date(millis)
        if(name.length>2 && description.length>=2 && dateFinish.after(date)){
            return true
        }else{
            //TODO exceptions invalid data
            return false
        }
    }
}