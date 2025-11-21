package com.example.demo.controller;

import com.example.demo.service.MathService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AdditionController.class, 
    excludeAutoConfiguration = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
@DisplayName("AdditionController Integration Tests with MockMvc")
class AdditionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MathService mathService;

    @Nested
    @DisplayName("Square Root Endpoint Integration Tests")
    class SqrtIntegrationTests {

        @Test
        @DisplayName("GET /sqrt should return square root for valid number")
        void testSqrtEndpointValidNumber() throws Exception {
            // Given
            double input = 16.0;
            double expectedResult = 4.0;
            when(mathService.sqrt(input)).thenReturn(expectedResult);

            // When & Then
            mockMvc.perform(get("/sqrt").param("number", String.valueOf(input)))
                    .andExpect(status().isOk())
                    .andExpect(content().string("4.0"));

            verify(mathService, times(1)).sqrt(input);
        }

        @Test
        @DisplayName("GET /sqrt should return 0 for sqrt(0)")
        void testSqrtEndpointZero() throws Exception {
            // Given
            double input = 0.0;
            double expectedResult = 0.0;
            when(mathService.sqrt(input)).thenReturn(expectedResult);

            // When & Then
            mockMvc.perform(get("/sqrt").param("number", "0.0"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("0.0"));

            verify(mathService, times(1)).sqrt(input);
        }

        @Test
        @DisplayName("GET /sqrt should return 1 for sqrt(1)")
        void testSqrtEndpointOne() throws Exception {
            // Given
            double input = 1.0;
            double expectedResult = 1.0;
            when(mathService.sqrt(input)).thenReturn(expectedResult);

            // When & Then
            mockMvc.perform(get("/sqrt").param("number", "1.0"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("1.0"));

            verify(mathService, times(1)).sqrt(input);
        }

        @Test
        @DisplayName("GET /sqrt should return BadRequest for negative number")
        void testSqrtEndpointNegativeNumber() throws Exception {
            // Given
            double input = -4.0;
            when(mathService.sqrt(input)).thenThrow(new IllegalArgumentException("No se puede calcular la raíz cuadrada de un número negativo"));

            // When & Then
            mockMvc.perform(get("/sqrt").param("number", "-4.0"))
                    .andExpect(status().isBadRequest());

            verify(mathService, times(1)).sqrt(input);
        }

        @Test
        @DisplayName("GET /sqrt should handle decimal numbers")
        void testSqrtEndpointDecimal() throws Exception {
            // Given
            double input = 2.25;
            double expectedResult = 1.5;
            when(mathService.sqrt(input)).thenReturn(expectedResult);

            // When & Then
            mockMvc.perform(get("/sqrt").param("number", "2.25"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("1.5"));

            verify(mathService, times(1)).sqrt(input);
        }

        @Test
        @DisplayName("GET /sqrt should return BadRequest for missing parameter")
        void testSqrtEndpointMissingParameter() throws Exception {
            // When & Then
            mockMvc.perform(get("/sqrt"))
                    .andExpect(status().isBadRequest());

            verify(mathService, never()).sqrt(anyDouble());
        }
    }

    @Nested
    @DisplayName("Other Endpoints Integration Tests")
    class OtherEndpointsTests {

        @Test
        @DisplayName("GET /add should work correctly")
        void testAddEndpoint() throws Exception {
            // Given
            when(mathService.add(3, 5)).thenReturn(8);

            // When & Then
            mockMvc.perform(get("/add").param("num1", "3").param("num2", "5"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("8"));

            verify(mathService, times(1)).add(3, 5);
        }

        @Test
        @DisplayName("GET /multiply should work correctly")
        void testMultiplyEndpoint() throws Exception {
            // Given
            when(mathService.multiply(4, 6)).thenReturn(24);

            // When & Then
            mockMvc.perform(get("/multiply").param("num1", "4").param("num2", "6"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("24"));

            verify(mathService, times(1)).multiply(4, 6);
        }

        @Test
        @DisplayName("GET /divide should work correctly")
        void testDivideEndpoint() throws Exception {
            // Given
            when(mathService.divide(15, 3)).thenReturn(5.0);

            // When & Then
            mockMvc.perform(get("/divide").param("num1", "15").param("num2", "3"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("5.0"));

            verify(mathService, times(1)).divide(15, 3);
        }

        @Test
        @DisplayName("GET /subtract should work correctly")
        void testSubtractEndpoint() throws Exception {
            // Given
            when(mathService.subtract(10, 4)).thenReturn(6);

            // When & Then
            mockMvc.perform(get("/subtract").param("num1", "10").param("num2", "4"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("6"));

            verify(mathService, times(1)).subtract(10, 4);
        }
    }
}