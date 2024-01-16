package registered.project.api.service.email

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import registered.project.api.exceptions.PasswordInvalidException


@ExtendWith(MockKExtension::class)
class VerifyEmailTest {

    @InjectMockKs
    lateinit var verifyEmailService: VerifyEmailService

    @MockK
    lateinit var sendEmail: JavaMailSender

    @Value("\${api-key-encrypt}")
    private var key: String="secretKey1011123"

    @Test
    fun `Should return D372BHpWmGU+ptunOTa8FQ== based on string testCode`() {
        Assertions.assertEquals(verifyEmailService.encryptCode("testCode"),"D372BHpWmGU+ptunOTa8FQ==")
    }

    @Test
    fun `Should return testCode based on code D372BHpWmGU+ptunOTa8FQ== `() {
        Assertions.assertEquals(verifyEmailService.decrypt("D372BHpWmGU+ptunOTa8FQ=="),"testCode")
    }

    @Test
    fun `Should return exception because the code is invalid `() {
        Assertions.assertThrows(Exception::class.java) {
            verifyEmailService.decrypt("D372BHpWmGU+ptunOTapFQ==")
        }
    }



}