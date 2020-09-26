package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    private String basePath = "http://localhost:8080/";

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "arzuman.kochoyan@mail.ru", roles = {"USER", "ADMIN"})
    void getUser() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void auth() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("email", "arzuman.kochoyan@mail.ru");
        objectNode.put("password", "Arzuman");

        mockMvc.perform(post(basePath + "/user/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectNode.toString()))
                .andExpect(status().isOk());
    }


    @Test
    void registerUser_Ok() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("name", "poxosyan");
        objectNode.put("surname", "poxosyan");
        objectNode.put("password", "poxosyan");
        objectNode.put("confirmPassword", "poxosyan");
        objectNode.put("email", "poxosyan.poxo@mail.ru");
        objectNode.put("gender", "MALE");

        mockMvc.perform(post(basePath + "user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectNode.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void registerUser_ClientError() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("name", "poxos");
        objectNode.put("surname", "poxosya");
        objectNode.put("password", "poxosya");
        objectNode.put("confirmPassword", "poxosya");
        objectNode.put("email", "poxos.poxosy@mail.ru");
        objectNode.put("gender", "MALE");

        mockMvc.perform(post(basePath + "user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectNode.toString()))
                .andExpect(status().is4xxClientError());
    }


    @Test
    void activate() {
    }

    @Test
    void addAboutMe() {
    }

    @Test
    void saveUserImg() {
    }

    @Test
    void userDataChange() {
    }

    @Test
    void userPasswordChange() {
    }

    @Test
    void forgotPass() {
    }

    @Test
    void reset() {
    }

    @Test
    void changePass() {
    }
}