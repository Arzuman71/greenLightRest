package am.greenlight.greenlight.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import am.greenlight.greenlight.dto.PasswordChangeDto;
import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.security.JwtTokenUtil;
import am.greenlight.greenlight.service.EmailService;
import am.greenlight.greenlight.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@SpringBootTest
@AutoConfigureMockMvc
//@ContextConfiguration
class UserControllerTest {

    private MockMvc mvc;
    private MockMvc mvc2;
    private MockMvc mockMvc;
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
    @Autowired
    private WebApplicationContext context;
    @Mock
    private MockMultipartFile file;
    @Autowired
    private WebApplicationContext webApplicationContext;


    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new UserController(userService,
                passwordEncoder, modelMapper, tokenUtil, emailService))
                .build();

        mvc2 = webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        mockMvc = webAppContextSetup(webApplicationContext).build();

    }

    @Test
    @WithUserDetails("arzuman.kochoyan@mail.ru")
    public void getUser_Ok() throws Exception {
        mvc2.perform(MockMvcRequestBuilders.get("/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

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
        objectNode.put("name", "user");
        objectNode.put("surname", "user");
        objectNode.put("password", "password");
        objectNode.put("confirmPassword", "password");
        objectNode.put("email", "user.user@mail.ru");
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
    void activate_Ok() {
        try {
            mvc.perform(MockMvcRequestBuilders.get("/user/activate?email=arzuman.kochoyan98@mail.ru&otp=0eace02f-9e10-4106-a01f-86a9550f01b3")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void activate_ClientError() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/activate?email=arzuman.kochoyan98@mail.ru&otp=ClientError")
                .contentType(MediaType.APPLICATION_JSON))
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

    @Test
    @WithUserDetails("arzuman.kochoyan@mail.ru")
    void passwordChange_Ok() {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("oldPassword", "passwordChange");
        objectNode.put("password", "Arzuman");
        objectNode.put("confirmPassword", "Arzuman");
        try {
            mvc2.perform(MockMvcRequestBuilders.put("/user/password/change")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectNode.toString()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @WithUserDetails("arzuman.kochoyan@mail.ru")
    void passwordChange_ConfirmPasswordError() {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("oldPassword", "Arzuman");
        objectNode.put("password", "passwordTest");
        objectNode.put("confirmPassword", "ClientError");
        try {
            mvc2.perform(MockMvcRequestBuilders.put("/user/password/change")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectNode.toString()))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @WithUserDetails("arzuman.kochoyan@mail.ru")
    void passwordChange_OldPasswordError() {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("oldPassword", "PasswordError");
        objectNode.put("password", "passwordTest");
        objectNode.put("confirmPassword", "passwordTest");
        try {
            mvc2.perform(MockMvcRequestBuilders.put("/user/password/change")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectNode.toString()))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @WithUserDetails("arzuman.kochoyan@mail.ru")
    void changeAbout_Ok() {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("about", "aboutTest");
        try {
            mvc2.perform(MockMvcRequestBuilders.put("/user/about")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectNode.toString()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @WithUserDetails("arzuman.kochoyan@mail.ru")
    void changeAbout_ClientError() {
        try {
            mvc2.perform(MockMvcRequestBuilders.put("/user/about")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    @WithUserDetails("arzuman.kochoyan@mail.ru")
    void changeAvatar_Ok() {
        try {
            File f = new File("C:\\Users\\Arzuman\\Desktop\\Folder\\rest\\greenLightRest\\upload\\17.png");
            InputStream inputStream = new FileInputStream(f);
            MockMultipartFile file = new MockMultipartFile("file", "17.png",
                    MediaType.IMAGE_PNG_VALUE, inputStream);
            mockMvc.perform(multipart("/user/avatar").file(file))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
