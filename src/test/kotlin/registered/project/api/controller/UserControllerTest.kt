package registered.project.api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import registered.project.api.repositories.UserRepository
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import registered.project.api.dtos.RegisterDTO
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.http.MediaType



@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class UserControllerTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper


    companion object {
        const val URL: String = "/User/Register"

    }

    @BeforeEach
    fun setup() = userRepository.deleteAll()

    @AfterEach
    fun tearDown() = userRepository.deleteAll()

    @Test
    fun `should return 200 because the data is valid to register user`(){
        val register: RegisterDTO = RegisterDTO(name="testName",email="testEmail@gmail.com",password="testPassword")
        val valueAsString: String = objectMapper.writeValueAsString(register)

        mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())

    }

}