package registered.project.api.service.email

import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.MailException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import org.springframework.stereotype.Service
import kotlin.random.Random
import java.util.Base64

@Service
class VerifyEmailService(
    private val sendEmail: JavaMailSender

) {

    @Value("\${api-key-encrypt}")
    private lateinit var key: String

    fun sendCodeEmail(email: String): String? {
        val msg = SimpleMailMessage()
        val code = this.generateRandomWord(this.generateLengthWord())
        msg.setTo(email)
        msg.setSubject("Vefique seu email para conclusão do cadastro!")
        msg.setText("Código para Validação: $code")

        try {
            sendEmail.send(msg)
            return this.encryptCode(code)
        } catch (ex: MailException) {
            System.err.println(ex.cause);
        }

        return null
    }

    private fun generateLengthWord(): Int {
        return (4..6).random()
    }

    private fun generateRandomWord(length: Int): String {
        val alphabet = "abcdefghijklmnopqrstuvwxyz"
        return (1..length)
            .map { alphabet[Random.nextInt(alphabet.length)] }
            .joinToString("")
    }

    private fun encryptCode(code: String): String {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key.toByteArray(), "AES"))
        val encryptedBytes: ByteArray = cipher.doFinal(code.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    fun decrypt(encryptedData: String): String {
        val decodedBytes: ByteArray = Base64.getDecoder().decode(encryptedData)
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(key.toByteArray(), "AES"))
        val decryptedBytes: ByteArray = cipher.doFinal(decodedBytes)
        return String(decryptedBytes)
    }


}