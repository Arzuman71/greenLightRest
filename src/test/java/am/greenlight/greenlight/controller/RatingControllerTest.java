package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.security.JwtTokenUtil;
import am.greenlight.greenlight.service.EmailService;
import am.greenlight.greenlight.service.RatingService;
import am.greenlight.greenlight.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RatingControllerTest {


    private MockMvc mvc;

    @Autowired
    private RatingService ratingService;
    @Autowired
    private UserService userService;


    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new RatingController(ratingService, userService)).build();
    }

    @Test
    void findAllByToId_Ok() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/rating/46")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }
}