package registered.project.api.service.validation

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import registered.project.api.dtos.LoginDTO
import registered.project.api.dtos.RegisterAdmDTO
import java.util.regex.Pattern

@Service
class ValidationAuth {
    @Value("\${api-hash-adm}")
    private lateinit var hashAdm: String

    @Value("\${api-password-adm}")
    private lateinit var passwordAdm: String

     fun validateLogin(data: LoginDTO):Boolean{
        if((data.email.length>6 && this.isEmailValid(data.email)) && (data.password.length>=8 && data.password.length<=20)){
            return true
        }else{
            //TODO Exception email and/or password invalid
            return false
        }
    }
     fun validateRegister(email:String,name:String,password:String):Boolean{
        if((email.length>6 && this.isEmailValid(email)) && (password.length>=8 && password.length<=20) && name.length>2){
            return true
        }else{
            //TODO Exception email and/or password,name invalid
            return false
        }
    }
     fun validateToken(token:String?):Boolean{
        if(token!=null){
            return true
        }else{
            return false
        }
    }
     fun validateRegisterAdm(registerAdmDTO: RegisterAdmDTO):Boolean{
        if((this.validateRegister(registerAdmDTO.email,registerAdmDTO.name,registerAdmDTO.password))
            && this.validateHashAndPasswordAdm(registerAdmDTO.hash,registerAdmDTO.passwordAdm)){
            return true
        }else{
            //TODO Exception email and/or password,name invalid
            return false
        }
    }
     private fun validateHashAndPasswordAdm(hash:String,passwordAdm:String):Boolean{
        if((hash == this.hashAdm) && passwordAdm == this.passwordAdm){
            return true
        }else{
            return false
        }

    }
     private  fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$"
        val pattern = Pattern.compile(emailRegex)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }
}