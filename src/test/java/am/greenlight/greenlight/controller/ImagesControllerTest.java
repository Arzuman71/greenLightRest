package am.greenlight.greenlight.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@AutoConfigureMockMvc
//@ActiveProfiles("test")
class ImagesControllerTest {


    private MockMvc mvc;
    @Autowired
    ImagesController imagesController;


    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(imagesController)
                .build();

    }
    @Test
    void savePicture() {
            try {
                mvc.perform(MockMvcRequestBuilders.post("/car/picture?carId=2&image=test")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andDo(MockMvcResultHandlers.print());
            } catch (Exception e) {
                e.printStackTrace();
            }

    }



    @Test
    void changePicture() {
    }
}