package am.greenlight.greenlight.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import am.greenlight.greenlight.security.JwtTokenUtil;
import am.greenlight.greenlight.service.EmailService;
import am.greenlight.greenlight.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    private MockMvc mvc;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtTokenUtil tokenUtil;
    @Autowired
    private EmailService emailService;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new UserController(userService,
                passwordEncoder, modelMapper, tokenUtil, emailService)).build();
    }


    // @Test
    // @WithMockUser(username = "arzuman.kochoyan@mail.ru", password = "Arzuman", roles = {"USER", "ADMIN"})
    // public void getUser() throws Exception {

    //     mvc.perform(MockMvcRequestBuilders.get("/user")
    //             .contentType(MediaType.ALL))
    //             //  .andExpect(status().isOk())
    //             .andDo(MockMvcResultHandlers.print());

    // }

    @Test
    public void auth_Ok() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("email", "arzuman.kochoyan@mail.ru");
        objectNode.put("password", "Arzuman");

        mvc.perform(MockMvcRequestBuilders.post("/user/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectNode.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void auth_ClientError() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("email", "an.kochoyan@mail.ru");
        objectNode.put("password", "A");

        mvc.perform(MockMvcRequestBuilders.post("/user/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectNode.toString()))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print());

    }


    @Test
    public void registerUser_Ok() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("name", "poxosyan");
        objectNode.put("surname", "poxosyan");
        objectNode.put("password", "poxosyan");
        objectNode.put("confirmPassword", "poxosyan");
        objectNode.put("email", "arzuman.kochoyan98@mail.ru");
        objectNode.put("gender", "MALE");

        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectNode.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void registerUser_ClientError() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("name", "poxos");
        objectNode.put("surname", "poxosya");
        objectNode.put("password", "poxosya");
        objectNode.put("confirmPassword", "poxosya");
        objectNode.put("email", "arzuman.ClientError");
        objectNode.put("gender", "MALE");

        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectNode.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void activate_Ok() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("email", "arzuman.kochoyan@mail.ru");
        objectNode.put("otp", "");

        mvc.perform(MockMvcRequestBuilders.put("/user/activate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectNode.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void activate_ClientError() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("email", "arzuman.kochoyan@mail.ru");
        objectNode.put("otp", "ClientError");

        mvc.perform(MockMvcRequestBuilders.put("/user/activate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectNode.toString()))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void forgotPass_Ok() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/forgotPassword?email=arzuman.kochoyan98@mail.ru")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void forgotPass_ClientError() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/forgotPassword?email=ClientError@mail.ru")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void reset_Ok() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/forgotPassword/reset?email=arzuman.kochoyan98@mail.ru&otp=6b1bad5e-2198-4dd7-be38-4799c3661049")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void reset_ClientError() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/forgotPassword/reset?email=arzuman.kochoyan98@mail.ru&otp=ClientError")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print());
    }
}
