package registered.project.api.service.validation

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import registered.project.api.dtos.LoginDTO
import registered.project.api.dtos.RegisterAdmDTO
import registered.project.api.exceptions.EmailInvalidException
import registered.project.api.exceptions.InvalidHashAndPasswordAdm
import registered.project.api.exceptions.LengthNameInvalidException
import registered.project.api.exceptions.PasswordInvalidException
import java.util.regex.Pattern

@Service
class ValidationAuth {
    @Value("\${api-hash-adm}")
    private lateinit var hashAdm: String

    @Value("\${api-password-adm}")
    private lateinit var passwordAdm: String

    fun validateLogin(data: LoginDTO): Boolean {
        if (this.isEmailValid(data.email)) {
            if (data.password.length >= 8 && data.password.length <= 20) {
                return true
            } else {
                throw PasswordInvalidException("password must be at least 8 characters and max 20")
            }
        } else {
            throw EmailInvalidException("email invalid")
        }
    }

    fun validateRegister(email: String, name: String, password: String): Boolean {
        if (this.isEmailValid(email)) {
            if (password.length >= 8 && password.length <= 20) {
                if (name.length > 2) {
                    return true
                } else {
                    throw LengthNameInvalidException("name must be at least 2 characters")
                }
            } else {
                throw PasswordInvalidException("password must be at least 8 characters and max 20")
            }

        } else {
            throw EmailInvalidException("email invalid")
        }
    }

    fun validateToken(token: String?): Boolean {
        if (token != null) {
            return true
        } else {
            return false
        }
    }

    fun validateRegisterAdm(registerAdmDTO: RegisterAdmDTO): Boolean {
        if ((this.validateRegister(registerAdmDTO.email, registerAdmDTO.name, registerAdmDTO.password))
            && this.validateHashAndPasswordAdm(registerAdmDTO.hash, registerAdmDTO.passwordAdm)
        ) {
            return true
        } else {
            return false
        }
    }

    private fun validateHashAndPasswordAdm(hash: String, passwordAdm: String): Boolean {
        if ((hash == this.hashAdm) && passwordAdm == this.passwordAdm) {
            return true
        } else {
            throw InvalidHashAndPasswordAdm("Invalid Register ADM")
        }

    }

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$"
        val pattern = Pattern.compile(emailRegex)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }
}