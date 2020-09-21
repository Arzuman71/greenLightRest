package am.greenlight.greenlight.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {
    private String basePath = "http://localhost:8080/";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void mainPage() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.get("/"))
                    .andDo(print())
                   .andExpect(status().isOk());

    }

    @Test
    void successLogin() {
    }

    @Test
    void getImage() {
    }
}