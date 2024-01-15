package registered.project.api.service.validation

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import registered.project.api.dtos.LoginDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertThrows
import org.springframework.beans.factory.annotation.Value
import registered.project.api.dtos.RegisterAdmDTO
import registered.project.api.exceptions.EmailInvalidException
import registered.project.api.exceptions.InvalidHashAndPasswordAdm
import registered.project.api.exceptions.LengthNameInvalidException
import registered.project.api.exceptions.PasswordInvalidException

@ExtendWith(MockKExtension::class)
class AuthTest {

    @InjectMockKs
    lateinit var validationAuth: ValidationAuth

    @Value("\${api-hash-adm}")
    private  var hashAdm: String="hashAdm"

    @Value("\${api-password-adm}")
    private  var passwordAdm: String="passwordAdm"

    @Test
    fun `Should return TRUE because the data to login is valid`() {

        var login: LoginDTO = LoginDTO(email = "testEmail@gmail.com", password = "12345678910")

        Assertions.assertEquals(validationAuth.validateLogin(login), true)
    }

    @Test
    fun `should return exception PasswordInvalidException because password has minus than 8 characters`() {
        val login: LoginDTO = LoginDTO(email = "testEmail@gmail.com", password = "1234")
        assertThrows(PasswordInvalidException::class.java) {
            validationAuth.validateLogin(login)
        }
    }

    @Test
    fun `should return exception EmailInvalidException because email is invalid`() {
        val login: LoginDTO = LoginDTO(email = "testEmailgmail", password = "1234")
        assertThrows(EmailInvalidException::class.java) {
            validationAuth.validateLogin(login)
        }
    }

    @Test
    fun `should return true because data register is valid`() {

        Assertions.assertEquals(
            validationAuth.validateRegister(
                email = "testEmail@gmail.com",
                name = "testName",
                password = "12345678910"
            ), true
        )
    }

    @Test
    fun `should return exception LengthNameInvalidException`() {
        assertThrows(LengthNameInvalidException::class.java) {
            validationAuth.validateRegister(email = "testEmail@gmail.com", name = "t", password = "12345678910")
        }
    }

    @Test
    fun `should return InvalidHashAndPasswordAdm because password or hash isn't correct`() {
      var adm: RegisterAdmDTO =RegisterAdmDTO(name="testName",email="testEmail@gmail.com",password="12345678910",hash="wrongHash",passwordAdm="wrongPassword")
        assertThrows(InvalidHashAndPasswordAdm::class.java) {
            validationAuth.validateRegisterAdm(adm)
        }
    }
    @Test
    fun `should return true because the hash and password adm is valid`(){
        var adm: RegisterAdmDTO =RegisterAdmDTO(name="testName",email="testEmail@gmail.com",password="12345678910",hash="hashAdm",passwordAdm="passwordAdm")
        Assertions.assertEquals(validationAuth.validateRegisterAdm(adm), true)
    }

}


//    @Test
//    fun ``(){
//    }