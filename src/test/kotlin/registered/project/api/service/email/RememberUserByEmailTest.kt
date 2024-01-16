package registered.project.api.service.email

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.mail.javamail.JavaMailSender
import registered.project.api.entities.Card
import registered.project.api.entities.User
import registered.project.api.enums.FrequencyCard
import registered.project.api.repositories.CardRepository
import registered.project.api.repositories.UserRepository
import java.sql.Date
import java.text.SimpleDateFormat


@ExtendWith(MockKExtension::class)
class RememberUserByEmailTest {

    @InjectMockKs
    lateinit var rememberUserCardsService: RememberUserCardsService
    @MockK
    lateinit var sendEmail: JavaMailSender

    @MockK
    lateinit var cardRepository: CardRepository

    @MockK
    lateinit var userRepository: UserRepository




    @Test
    fun `Should return TRUE because user is valid`() {

        val user: User = User(email="testEmail@gmail.com", nameUser = "testName")

        Assertions.assertEquals(rememberUserCardsService.sendEmailRememberUsersCards(user),true)
    }

    @Test
    fun `Should return false because the frequency is low and the date of card is after the date actual`(){
        val dateString = "2024-01-18"
        val dateFormat = generatedDate(dateString)
        var card:Card = Card(id=1,name="testCard",description="testDescription",dateFinish=dateFormat)
        var cards: MutableList<Card>? = mutableListOf<Card>(card)
        Assertions.assertEquals(rememberUserCardsService.decideFrequencyEmails(FrequencyCard.LOW,cards),false)

    }
    @Test
    fun `Should return true because the frequency is low and the date of card is equals the date actual`(){
        val dateString = "2024-01-16"
        val dateFormat = generatedDate(dateString)
        var card:Card = Card(id=1,name="testCard",description="testDescription",dateFinish=dateFormat)
        var cards: MutableList<Card>? = mutableListOf<Card>(card)
        Assertions.assertEquals(rememberUserCardsService.decideFrequencyEmails(FrequencyCard.LOW,cards),true)

    }

    @Test
    fun `Should return true because the frequency is AVERAGE and the date actual is equals date card`(){
        val dateString = "2024-01-16"
        val dateFormat = generatedDate(dateString)
        var card:Card = Card(id=1,name="testCard",description="testDescription",dateFinish=dateFormat)
        var cards: MutableList<Card>? = mutableListOf<Card>(card)
        Assertions.assertEquals(rememberUserCardsService.decideFrequencyEmails(FrequencyCard.AVERAGE,cards),true)

    }
    @Test
    fun `Should return true because the frequency is AVERAGE and the date actual is even and the date od card is odd`(){
        val dateString = "2024-01-17"
        val dateFormat = generatedDate(dateString)
        var card:Card = Card(id=1,name="testCard",description="testDescription",dateFinish=dateFormat)
        var cards: MutableList<Card>? = mutableListOf<Card>(card)
        Assertions.assertEquals(rememberUserCardsService.decideFrequencyEmails(FrequencyCard.AVERAGE,cards),true)

    }

    @Test
    fun `Should return true because the frequency is AVERAGE and the date actual is odd and the date card is even`(){
        val dateString = "2024-01-18"
        val dateFormat = generatedDate(dateString)
        var card:Card = Card(id=1,name="testCard",description="testDescription",dateFinish=dateFormat)
        var cards: MutableList<Card>? = mutableListOf<Card>(card)
        Assertions.assertEquals(rememberUserCardsService.decideFrequencyEmails(FrequencyCard.AVERAGE,cards),true)

    }

    @Test
    fun `Should return false because the frequency is AVERAGE and the date actual is even and the date card is even`(){
        val dateString = "2024-01-18"
        val dateFormat = generatedDate(dateString)
        var card:Card = Card(id=1,name="testCard",description="testDescription",dateFinish=dateFormat)
        var cards: MutableList<Card>? = mutableListOf<Card>(card)
        Assertions.assertEquals(rememberUserCardsService.decideFrequencyEmails(FrequencyCard.AVERAGE,cards),false)

    }
    @Test
    fun `Should return false because the frequency is AVERAGE and the date actual is odd and the date card is odd`(){
        val dateString = "2024-01-19"
        val dateFormat = generatedDate(dateString)
        var card:Card = Card(id=1,name="testCard",description="testDescription",dateFinish=dateFormat)
        var cards: MutableList<Card>? = mutableListOf<Card>(card)
        Assertions.assertEquals(rememberUserCardsService.decideFrequencyEmails(FrequencyCard.AVERAGE,cards),false)

    }

    companion object {
        fun generatedDate(dateString:String):Date{
            val format = SimpleDateFormat("yyyy-MM-dd")
            var dateFormat = format.parse(dateString)
            dateFormat = Date(dateFormat.time)
            return dateFormat
        }
    }
}