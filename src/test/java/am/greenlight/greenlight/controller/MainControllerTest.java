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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MainControllerTest {

    private MockMvc mvc;
    @Autowired
    private MainController mainController;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(mainController).build();
    }

    @Test
    void items_Ok() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("outset", "t");
        objectNode.put("end", "t");
        objectNode.put("dateFrom", "2021-01-09");
        objectNode.put("type", "CAR_DRIVER");
        mvc.perform(MockMvcRequestBuilders.post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectNode.toString()))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void getImage_Ok() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/image/17.png")
                .contentType(MediaType.IMAGE_JPEG_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}