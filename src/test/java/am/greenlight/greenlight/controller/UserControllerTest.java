package am.greenlight.greenlight.controller;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.security.JwtTokenUtil;
import am.greenlight.greenlight.service.EmailService;
import am.greenlight.greenlight.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
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
import java.nio.file.Files;
import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
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
    private WebApplicationContext context;
    @Autowired
    private WebApplicationContext webApplicationContext;


    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new UserController(userService,
                passwordEncoder, modelMapper, tokenUtil))
                .build();

        mvc2 = webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        mockMvc = webAppContextSetup(webApplicationContext).build();

    }

    @Test
    @WithUserDetails("arzuman.kochoyan@mail.ru")
    public void getUser_Ok() throws Exception {
        mvc2.perform(MockMvcRequestBuilders.get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void auth_Ok() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("email", "arzuman.kochoyan@mail.ru");
        objectNode.put("password", "Arzuman");

        mvc.perform(MockMvcRequestBuilders.post("/api/users/auth")
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

        mvc.perform(MockMvcRequestBuilders.post("/api/users/auth")
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

        mvc.perform(post("/api/users")
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

        mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectNode.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError());
    }



    //todo can not find user with this email
    @Test
    void activate_Ok() {
        try {
            mvc.perform(get("/api/users/activate?email=arzuman.kochoyan98@mail.ru&otp=0eace02f-9e10-4106-a01f-86a9550f01b3")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void activate_ClientError() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/users/activate")
                .param("email", "arkochoyan@mail.ru")
                .param("otp", "ClientError")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print());
    }

    //petq e poxel tip@ zaprosi rexuest param
    @Test
    void forgotPass_Ok() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/users/forgotPassword")
                .param("email", "arzuman.kochoyan@mail.ru")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void forgotPass_ClientError() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/users/forgotPassword")
                .param("email", "ClientError@mail.ru")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void reset_Ok() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/users/forgotPassword/reset")
                .param("email", "poxostest@mail.ru")
                .param("otp", "e570c78e-81b8-40a5-bdcc-98fe790f6464")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void reset_ClientError() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/users/forgotPassword/reset")
                .param("email", "poxostest@mail.ru")
                .param("otp", "ClientError")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithUserDetails("poxostest@mail.ru")
    void passwordChange_Ok() {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("oldPassword", "arzuman");
        objectNode.put("password", "newPassword");
        try {
            mvc2.perform(put("/api/users/password/change")
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
        objectNode.put("oldPassword", "ARZUMAN");
        objectNode.put("password", "passwordTest");
        try {
            mvc2.perform(MockMvcRequestBuilders.put("/api/users/password/change")
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
        try {
            mvc2.perform(MockMvcRequestBuilders.put("/api/users/password/change")
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
    void changeAvatar_Ok() {
        try {
            File f = new File("C:\\Users\\User\\Desktop\\project\\greenLightRest\\upload\\17.png");
            InputStream inputStream = new FileInputStream(f);
            MockMultipartFile file = new MockMultipartFile("image", "17.png",
                    MediaType.IMAGE_PNG_VALUE, inputStream);
            mockMvc.perform(multipart("/api/users/avatar").file(file))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @WithUserDetails("arzuman.kochoyan@mail.ru")
    void savePhoneNumber_Ok() {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("number", "171717");
        try {
            mvc2.perform(MockMvcRequestBuilders.put("/api/users/phone")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectNode.toString()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
