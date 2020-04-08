package xyz.simpleq.flystalker.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class RequestControllerTests {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `get exchanges`() {
        mockMvc
            .perform(get("/exchanges"))
            .andExpect(status().isOk)
    }

    @Test
    fun `create exchange`() {
        // TODO
    }
}
