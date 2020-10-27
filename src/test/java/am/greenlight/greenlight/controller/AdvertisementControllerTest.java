package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.service.AdvertisementService;
import am.greenlight.greenlight.service.CarService;
import am.greenlight.greenlight.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class AdvertisementControllerTest {
    private MockMvc mvc;
    @Autowired
    private AdvertisementService advertisementService;


    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new AdvertisementController(advertisementService)).build();
    }
    
    @Test
    void findAll_Ok() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/advertisements")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}