package registered.project.api.service.validation

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import java.sql.Date
import java.text.SimpleDateFormat
import org.junit.jupiter.api.Test
import registered.project.api.exceptions.DateCardInvalidException
import registered.project.api.exceptions.LengthDescriptionInvalidException
import registered.project.api.exceptions.LengthNameInvalidException


@ExtendWith(MockKExtension::class)
class ValidationCardTest {

    @InjectMockKs
    lateinit var validationCard: ValidationCard

    @Test
    fun `should return true because the data to add card is valid`(){
        val dateString = "2024-01-18"
        val format = SimpleDateFormat("yyyy-MM-dd")
        var dateFormat = format.parse(dateString)
        dateFormat = Date(dateFormat.time)
        Assertions.assertEquals(validationCard.validateCard(name="testCard",description="testDescription",dateFinish=dateFormat), true)
    }
    @Test
    fun `should return LengthNameInvalidException because the name is invalid`(){
        val dateString = "2024-01-18"
        val format = SimpleDateFormat("yyyy-MM-dd")
        var dateFormat = format.parse(dateString)
        dateFormat = Date(dateFormat.time)

        Assertions.assertThrows(LengthNameInvalidException::class.java) {
            validationCard.validateCard(name="te",description="testDescription",dateFinish=dateFormat)
        }
    }
    @Test
    fun `should return LengthDescriptionInvalidException because the description is invalid`(){
        val dateString = "2024-01-18"
        val format = SimpleDateFormat("yyyy-MM-dd")
        var dateFormat = format.parse(dateString)
        dateFormat = Date(dateFormat.time)

        Assertions.assertThrows(LengthDescriptionInvalidException::class.java) {
            validationCard.validateCard(name="test",description="t",dateFinish=dateFormat)
        }
    }
    @Test
    fun `should return DateCardInvalidException because the date isn't after the date actual`(){
        val dateString = "2024-01-15"
        val format = SimpleDateFormat("yyyy-MM-dd")
        var dateFormat = format.parse(dateString)
        dateFormat = Date(dateFormat.time)

        Assertions.assertThrows(DateCardInvalidException::class.java) {
            validationCard.validateCard(name="testname",description="testDescription",dateFinish=dateFormat)
        }
    }
}


//``