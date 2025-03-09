package com.kulushev.app.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kulushev.app.controller.UserController;
import com.kulushev.app.controllerTest.dataProvider.UserDataProvider;
import com.kulushev.app.dto.UserReqDto;
import com.kulushev.app.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    UserService svc;

    private static String BASE_URL;
    private static UUID userId;

    @BeforeAll
    static void setUp() {
        BASE_URL = "/app/users";
        userId = UUID.fromString("32e2a465-4945-4f5e-81df-63e1780df364");
    }

    @Test
    void addNewUser_thenReturn200Status() throws Exception {
        when(svc.createUser(any(UserReqDto.class)))
                .thenReturn(UserDataProvider.getUserRespDto_1());

        mockMvc.perform(post(BASE_URL)
                        .content(objectMapper.writeValueAsString(UserDataProvider.getUserReq_ValidDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addNewUser_thenReturn400Status_invalidName() throws Exception {

        mockMvc.perform(post(BASE_URL)
                        .content(objectMapper.writeValueAsString(UserDataProvider.getUserReqDto_InvalidName()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("Name must consist of two words, each starting with a capital letter, separated by a space"));
    }

    @Test
    void addNewUser_thenReturn400Status_invalidEmail() throws Exception {

        mockMvc.perform(post(BASE_URL)
                        .content(objectMapper.writeValueAsString(UserDataProvider.getUserReqDto_InvalidEmail()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("Invalid email format"));
    }

    @Test
    void addNewUser_thenReturn400Status_invalidEmailAndName() throws Exception {

        mockMvc.perform(post(BASE_URL)
                        .content(objectMapper.writeValueAsString(UserDataProvider.getUserReqDto_InvalidEmailAndName()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors[1]").value("Name must consist of two words, each starting with a capital letter, separated by a space"))
                .andExpect(jsonPath("$.errors[0]").value("Invalid email format"));
    }

    @Test
    void getUserById_thenReturn200Status() throws Exception {
        when(svc.getUserById(any(UUID.class)))
                .thenReturn(UserDataProvider.getUserRespDto_1());

        mockMvc.perform(get(BASE_URL + "/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void getFullInfoByUserId_thenReturn200Status() throws Exception {
        when(svc.getUserById(any(UUID.class)))
                .thenReturn(UserDataProvider.getUserRespDto_1());

        mockMvc.perform(get(BASE_URL + "/full/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("32e2a465-4945-4f5e-81df-63e1780df364"))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.orders", hasSize(2)))
                .andExpect(jsonPath("$.orders[0].id").value(1))
                .andExpect(jsonPath("$.orders[0].status").value("NEW"))
                .andExpect(jsonPath("$.orders[0].totalPrice").value(1100.00))
                .andExpect(jsonPath("$.orders[1].id").value(2))
                .andExpect(jsonPath("$.orders[1].status").value("NEW"))
                .andExpect(jsonPath("$.orders[1].totalPrice").value(2200.00))
                .andExpect(jsonPath("$.orders[0].goods", hasSize(2)))
                .andExpect(jsonPath("$.orders[0].goods[0].id").value(1))
                .andExpect(jsonPath("$.orders[0].goods[0].name").value("Laptop"))
                .andExpect(jsonPath("$.orders[0].goods[0].price").value(1000.00))
                .andExpect(jsonPath("$.orders[0].goods[1].id").value(2))
                .andExpect(jsonPath("$.orders[0].goods[1].name").value("Mouse"))
                .andExpect(jsonPath("$.orders[0].goods[1].price").value(100.00))
                .andExpect(jsonPath("$.orders[1].goods", hasSize(2)))
                .andExpect(jsonPath("$.orders[1].goods[0].id").value(3))
                .andExpect(jsonPath("$.orders[1].goods[0].name").value("Laptop"))
                .andExpect(jsonPath("$.orders[1].goods[0].price").value(2000.00))
                .andExpect(jsonPath("$.orders[1].goods[1].id").value(4))
                .andExpect(jsonPath("$.orders[1].goods[1].name").value("Mouse"))
                .andExpect(jsonPath("$.orders[1].goods[1].price").value(200.00));
    }




}
