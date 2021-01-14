package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {

    private MockMvc mvc;
    private MockMvc mvc2;
    @Autowired
    private CarService carService;
    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(new CarController(carService, modelMapper))
                .build();
        mvc2 = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithUserDetails("arzuman.@mail.ru")
    void cars_Ok() throws Exception {
        mvc2.perform(MockMvcRequestBuilders.get("/car/cars")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getOne_Ok() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/car/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    @WithUserDetails("arzuman.@mail.ru")
    void save_Ok() {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("carType", "CAR");
        objectNode.put("carBrand", "Brand");
        objectNode.put("carModel", "Model");
        objectNode.put("carNumber", "17");
        objectNode.put("color", "BLACK");
        objectNode.put("year", "1998-10-29");
        try {
            mvc2.perform(MockMvcRequestBuilders.post("/car")
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
        objectNode.put("carType", "CAR");
        objectNode.put("carBrand", "ClientError");
        objectNode.put("color", "BLACK");
        try {
            mvc2.perform(MockMvcRequestBuilders.post("/car")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectNode.toString()))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void changeCarImg() {
    }

    @Test
    void deleteCar() {
    }
}