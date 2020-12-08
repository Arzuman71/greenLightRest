package am.greenlight.greenlight.controller;


import am.greenlight.greenlight.service.CarService;
import am.greenlight.greenlight.service.ItemService;
import am.greenlight.greenlight.service.RatingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    private MockMvc mvc;
    private MockMvc mvc2;
    @Autowired
    private ItemService itemService;
    @Autowired
    private CarService carService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private  RatingService ratingService;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(new ItemController(itemService, carService, modelMapper, ratingService))
                .build();
        mvc2 = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void findById_Ok() throws Exception {
        mvc2.perform(MockMvcRequestBuilders.get("/item/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithUserDetails("arzuman.@mail.ru")
    void getItemsActive_Ok() throws Exception {
        mvc2.perform(MockMvcRequestBuilders.get("/item/active")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithUserDetails("arzuman.@mail.ru")
    void getItemsArchived() throws Exception {
        mvc2.perform(MockMvcRequestBuilders.get("/item/archived")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithUserDetails("arzuman.@mail.ru")
    void save_Ok() {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("carId", -1);
        objectNode.put("outset", "fr");
        objectNode.put("end", "wh");
        objectNode.put("startDate", String.valueOf(LocalDateTime.now()));
        objectNode.put("type", "CAR_DRIVER");

        try {
            mvc2.perform(MockMvcRequestBuilders.post("/item")
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
            mvc2.perform(MockMvcRequestBuilders.post("/item")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectNode.toString()))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}