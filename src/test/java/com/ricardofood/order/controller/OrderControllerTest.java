package com.ricardofood.order.controller;

import com.ricardofood.order.dto.OrderDto;
import com.ricardofood.order.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc
@DisplayName("Order Controller Test")
class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderService service;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Nested
    @DisplayName("GET /orders")
    class FindAllTest {

        @Test
        @DisplayName("Should return 200")
        void findAllTest1() throws Exception {
            // Arrange
            var emptyList = Collections.<OrderDto>emptyList();

            // Act
            when(service.findAll()).thenReturn(emptyList);

            // Assert
            mvc.perform(
                            get("/orders")
                                    .param("page", "0")
                                    .param("size", "10"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray());
        }
    }
}
