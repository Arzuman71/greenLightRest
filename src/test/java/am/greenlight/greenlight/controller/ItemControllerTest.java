package am.greenlight.greenlight.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ItemControllerTest {

    private MockMvc mvc;
    private MockMvc mvc2;

    @Autowired
    private ItemController itemController;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(itemController)
                .build();
        mvc2 = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void findById_Ok() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/items/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithUserDetails("arzuman.@mail.ru")
    void getItemsActive_Ok() throws Exception {
        mvc2.perform(MockMvcRequestBuilders.get("/api/items/active")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithUserDetails("arzuman.@mail.ru")
    void getItemsArchived() throws Exception {
        mvc2.perform(MockMvcRequestBuilders.get("/api/items/archived")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithUserDetails("arzuman.@mail.ru")
    void save_Ok() {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("carId", 2);
        objectNode.put("outset", "fr");
        objectNode.put("end", "wh");
        objectNode.put("startDate", String.valueOf(LocalDateTime.now()));
        objectNode.put("type", "CAR_DRIVER");

        try {
            mvc2.perform(MockMvcRequestBuilders.post("/api/items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectNode.toString()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @WithUserDetails("arzuman.@mail.ru")
    void save_ClientError() {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("carId", -1);
        objectNode.put("startDate", "2020-10-29T01:15:21.641");
        objectNode.put("type", "ClientError");
        try {
            mvc.perform(MockMvcRequestBuilders.post("/api/items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectNode.toString()))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}