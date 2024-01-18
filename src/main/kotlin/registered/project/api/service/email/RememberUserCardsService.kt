package registered.project.api.service.email

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.mail.MailException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import registered.project.api.entities.Card
import registered.project.api.entities.User
import registered.project.api.enums.FrequencyCard
import registered.project.api.enums.UserRole
import registered.project.api.repositories.CardRepository
import registered.project.api.repositories.UserRepository
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.LocalDate


@Service
class RememberUserCardsService(
    private val sendEmail: JavaMailSender,
    private val cardRepository: CardRepository,
    private val userRepository: UserRepository
) {

    fun sendEmailRememberUsersCards(user: User): Boolean {

        val msg = SimpleMailMessage()
        msg.setTo(user.email)
        msg.setSubject("Lembrete!!")
        msg.setText("Olá ${user.nameUser} Você possui cadastros ativos, entre no site para verificar o que cadastrou")

        try {
            sendEmail.send(msg)
            return true
        } catch (ex: MailException) {
            System.err.println(ex.cause);
            println("email fail:${user.email}")
            return false
        }

    }

    fun decideFrequencyEmails(frequency: FrequencyCard, cards: MutableList<Card>?): Boolean? {
        val currentDate = Date(System.currentTimeMillis())
        val dateFormat = SimpleDateFormat("yyyy/MM/dd")
        val formattedDate = dateFormat.format(currentDate)
        var formattedDateFinish: String? = null


        if (cards?.get(0)?.dateFinish != null) {
            formattedDateFinish = dateFormat.format(cards[0].dateFinish)
        }

        if (frequency == FrequencyCard.LOW) {
            if (formattedDateFinish != null) {
                if (formattedDateFinish == formattedDate) {
                    return true
                } else {
                    return false
                }
            } else {
                return null
            }
        }

        if (frequency == FrequencyCard.AVERAGE) {
            var dataString = cards?.get(0)?.dateFinish
            val localDate = dataString?.toLocalDate()
            val dayOfWeek = localDate?.dayOfWeek
            val currentDate = LocalDate.now()
            val dayOfWeekActual = currentDate.dayOfWeek

            if (dayOfWeek == dayOfWeekActual) {
                return true
            } else {
                if (dayOfWeek?.value!! % 2 == 0) {
                    if (dayOfWeekActual.value % 2 != 0) {
                        return true
                    } else {
                        return false
                    }
                } else {
                    if (dayOfWeekActual.value % 2 == 0) {
                        return true
                    } else {
                        return false
                    }
                }
            }


        }

        if (frequency == FrequencyCard.HIGH) {
            return true
        }

        return null

    }

    @Scheduled(cron = "0 00 16 * * ?")
    fun alertUsers() {
        var offset: Int = 0
        
        var pageable: Pageable = PageRequest.of(offset, 4)
        var pageableCards: Pageable = PageRequest.of(0, 1)
        var users: MutableList<User>? = userRepository.listToAdm(pageable, UserRole.USER)

        while (users?.size != 0) {

            if (users != null) {
                for (i in 0 until users.size) {
                    val listCards = cardRepository.listCardsUser(users[i].id!!, pageableCards)
                    if (listCards?.size != 0) {
                        val response = this.decideFrequencyEmails(listCards?.get(0)?.frequency!!, listCards)
                        if (response != null && response == true) {
                            this.sendEmailRememberUsersCards(users[i])
                        }
                    }
                }

                offset++
                pageable = PageRequest.of(offset, 4)
                users = userRepository.listToAdm(pageable, UserRole.USER)


            }
        }
    }
}