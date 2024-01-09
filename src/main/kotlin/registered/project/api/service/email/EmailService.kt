package registered.project.api.service.email

import org.springframework.mail.MailException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import registered.project.api.entities.User
import registered.project.api.repositories.UserRepository
import registered.project.api.service.RecoverToken
import registered.project.api.service.auth.AuthorizationService
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

@Service
class EmailService(
    private val sendEmail: JavaMailSender,
    private val recoverToken: RecoverToken,
    private val authorizationService: AuthorizationService,
    private val userRepository: UserRepository
) {


    fun sendEmailToAdm(user: User,toDelete:User) {

        var msg = SimpleMailMessage()
        msg.setTo(user.email)
        msg.setSubject(this.subjectEmail(toDelete))
        msg.setText(this.textEmail(toDelete))

        try {
            sendEmail.send(msg)

        } catch (ex: MailException) {
            System.err.println(ex.cause);
        }


    }

    private fun textEmail(user: User): String {
        val text: String =
            "Olá ADM, hoje, dia: ${this.getDate()} o usuário ${user.nameUser} solicitou a exclusão de sa conta, para concluir a solicitação, entre no sistema e aceite a exclusão clicando no botão vermelho ao lado do nome do usuário. /n EMAIL ENVIADO PELO SISTEMA /t NÃO RESPONDA "
        return text
    }

    private fun subjectEmail(user: User): String {
        val subject: String =
            "Solicitação de exclusão de conta do usuário: ${user.nameUser}. Para ver mais detalhes consulte a página de AMD /n DATA SOLICITAÇÃO: ${this.getDate()}"
        return subject
    }

    private fun getDate(): String {
        val date = Date(Calendar.getInstance().timeInMillis)
        val formatBrasil = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
        val dateFormat = formatBrasil.format(date)
        return dateFormat
    }
}