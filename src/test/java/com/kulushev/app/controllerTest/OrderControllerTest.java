package com.kulushev.app.controllerTest;

import com.kulushev.app.controller.OrderController;
import com.kulushev.app.controllerTest.dataProvider.OrderDataProvider;
import com.kulushev.app.dto.OrderRespDto;
import com.kulushev.app.service.OrderService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    private static String BASE_URL;

    @BeforeAll
    static void setUp() {
        BASE_URL = "/app/orders";
    }

    @Test
    void getAll_thenReturn200Status() throws Exception {

        Page<OrderRespDto> page = new PageImpl<>(
                OrderDataProvider.getOrdersList().subList(0, 10), PageRequest.of(0, 10), 11);

        Pageable pageable = PageRequest.of(0, 10);
        when(orderService.getAll(pageable)).thenReturn(page);

        mockMvc.perform(get(BASE_URL + "?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Проверяем, что статус 200 OK
                .andExpect(jsonPath("$.content.size()").value(10))  // Проверяем количество элементов на странице
                .andExpect(jsonPath("$.totalPages").value(2))  // Проверяем общее количество страниц
                .andExpect(jsonPath("$.totalElements").value(11))  // Проверяем общее количество элементов
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[2].id").value(3))
                .andExpect(jsonPath("$.content[3].id").value(4))
                .andExpect(jsonPath("$.content[4].id").value(5))
                .andExpect(jsonPath("$.content[5].id").value(6))
                .andExpect(jsonPath("$.content[6].id").value(7))
                .andExpect(jsonPath("$.content[7].id").value(8))
                .andExpect(jsonPath("$.content[8].id").value(9))
                .andExpect(jsonPath("$.content[9].id").value(10));
    }

    @Test
    void getAll_thenReturn400Status_invalidPage() throws Exception {
        mockMvc.perform(get(BASE_URL + "?page=0&size=-1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("getAll.size: must be greater than or equal to 1"));
    }
}
