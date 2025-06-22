package com.example.demo.controller;

import com.example.demo.service.MathService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MathController.class)
@DisplayName("MathController Integration Tests")
class MathControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MathService mathService;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("POST /api/math/sum endpoint tests")
    class SumEndpointTests {

        @Test
        @DisplayName("Should return sum for valid integers")
        void testSumValidIntegers() throws Exception {
            // Given
            Map<String, Object> request = Map.of("a", 5, "b", 3);
            when(mathService.add(5, 3)).thenReturn(8);

            // When & Then
            mockMvc.perform(post("/api/math/sum")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(8))
                    .andExpect(jsonPath("$.operation").value("sum"))
                    .andExpect(jsonPath("$.operands[0]").value(5))
                    .andExpect(jsonPath("$.operands[1]").value(3));

            verify(mathService, times(1)).add(5, 3);
        }

        @Test
        @DisplayName("Should return error for invalid input")
        void testSumInvalidInput() throws Exception {
            // Given
            Map<String, Object> request = Map.of("a", "invalid", "b", 3);

            // When & Then
            mockMvc.perform(post("/api/math/sum")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").value("Invalid input"));

            verify(mathService, never()).add(anyInt(), anyInt());
        }

        @Test
        @DisplayName("Should return error when service throws exception")
        void testSumServiceException() throws Exception {
            // Given
            Map<String, Object> request = Map.of("a", -1, "b", 5);
            when(mathService.add(-1, 5)).thenThrow(new IllegalArgumentException("Los números no pueden ser negativos"));

            // When & Then
            mockMvc.perform(post("/api/math/sum")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").value("Invalid input"));

            verify(mathService, times(1)).add(-1, 5);
        }

        @Test
        @DisplayName("Should handle missing parameters")
        void testSumMissingParameters() throws Exception {
            // Given
            Map<String, Object> request = Map.of("a", 5); // missing 'b'

            // When & Then
            mockMvc.perform(post("/api/math/sum")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").value("Invalid input"));

            verify(mathService, never()).add(anyInt(), anyInt());
        }
    }

    @Nested
    @DisplayName("POST /api/math/sum-list endpoint tests")
    class SumListEndpointTests {

        @Test
        @DisplayName("Should return sum for valid number list")
        void testSumListValidNumbers() throws Exception {
            // Given
            List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
            Map<String, List<Integer>> request = Map.of("numbers", numbers);
            when(mathService.sumList(numbers)).thenReturn(15);

            // When & Then
            mockMvc.perform(post("/api/math/sum-list")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(15))
                    .andExpect(jsonPath("$.operation").value("sum-list"))
                    .andExpect(jsonPath("$.operands").isArray())
                    .andExpect(jsonPath("$.operands[0]").value(1))
                    .andExpect(jsonPath("$.operands[4]").value(5));

            verify(mathService, times(1)).sumList(numbers);
        }

        @Test
        @DisplayName("Should return error for invalid input")
        void testSumListInvalidInput() throws Exception {
            // Given
            Map<String, Object> request = Map.of("numbers", "invalid");

            // When & Then
            mockMvc.perform(post("/api/math/sum-list")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").value("Invalid input"));

            verify(mathService, never()).sumList(anyList());
        }

        @Test
        @DisplayName("Should return error when service throws exception")
        void testSumListServiceException() throws Exception {
            // Given
            List<Integer> numbers = Arrays.asList(1, 2, 3);
            Map<String, List<Integer>> request = Map.of("numbers", numbers);
            when(mathService.sumList(numbers)).thenThrow(new RuntimeException("Service error"));

            // When & Then
            mockMvc.perform(post("/api/math/sum-list")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").value("Invalid input"));

            verify(mathService, times(1)).sumList(numbers);
        }

        @Test
        @DisplayName("Should handle empty list")
        void testSumListEmptyList() throws Exception {
            // Given
            List<Integer> numbers = Arrays.asList();
            Map<String, List<Integer>> request = Map.of("numbers", numbers);
            when(mathService.sumList(numbers)).thenReturn(0);

            // When & Then
            mockMvc.perform(post("/api/math/sum-list")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(0))
                    .andExpect(jsonPath("$.operation").value("sum-list"));

            verify(mathService, times(1)).sumList(numbers);
        }
    }

    @Nested
    @DisplayName("GET /api/math/check/{number} endpoint tests")
    class CheckNumberEndpointTests {

        @Test
        @DisplayName("Should check positive even number")
        void testCheckPositiveEvenNumber() throws Exception {
            // Given
            int number = 4;
            when(mathService.isEven(4)).thenReturn(true);
            when(mathService.isPositive(4)).thenReturn(true);

            // When & Then
            mockMvc.perform(get("/api/math/check/{number}", number))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.number").value(4))
                    .andExpect(jsonPath("$.isEven").value(true))
                    .andExpect(jsonPath("$.isPositive").value(true));

            verify(mathService, times(1)).isEven(4);
            verify(mathService, times(1)).isPositive(4);
        }

        @Test
        @DisplayName("Should check positive odd number")
        void testCheckPositiveOddNumber() throws Exception {
            // Given
            int number = 5;
            when(mathService.isEven(5)).thenReturn(false);
            when(mathService.isPositive(5)).thenReturn(true);

            // When & Then
            mockMvc.perform(get("/api/math/check/{number}", number))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.number").value(5))
                    .andExpect(jsonPath("$.isEven").value(false))
                    .andExpect(jsonPath("$.isPositive").value(true));

            verify(mathService, times(1)).isEven(5);
            verify(mathService, times(1)).isPositive(5);
        }

        @Test
        @DisplayName("Should check negative number")
        void testCheckNegativeNumber() throws Exception {
            // Given
            int number = -3;
            when(mathService.isEven(-3)).thenReturn(false);
            when(mathService.isPositive(-3)).thenReturn(false);

            // When & Then
            mockMvc.perform(get("/api/math/check/{number}", number))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.number").value(-3))
                    .andExpect(jsonPath("$.isEven").value(false))
                    .andExpect(jsonPath("$.isPositive").value(false));

            verify(mathService, times(1)).isEven(-3);
            verify(mathService, times(1)).isPositive(-3);
        }

        @Test
        @DisplayName("Should check zero")
        void testCheckZero() throws Exception {
            // Given
            int number = 0;
            when(mathService.isEven(0)).thenReturn(true);
            when(mathService.isPositive(0)).thenReturn(false);

            // When & Then
            mockMvc.perform(get("/api/math/check/{number}", number))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.number").value(0))
                    .andExpect(jsonPath("$.isEven").value(true))
                    .andExpect(jsonPath("$.isPositive").value(false));

            verify(mathService, times(1)).isEven(0);
            verify(mathService, times(1)).isPositive(0);
        }
    }

    @Nested
    @DisplayName("POST /api/math/multiply endpoint tests")
    class MultiplyEndpointTests {

        @Test
        @DisplayName("Should return product for valid integers")
        void testMultiplyValidIntegers() throws Exception {
            // Given
            Map<String, Object> request = Map.of("a", 6, "b", 7);
            when(mathService.multiply(6, 7)).thenReturn(42);

            // When & Then
            mockMvc.perform(post("/api/math/multiply")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(42))
                    .andExpect(jsonPath("$.operation").value("multiply"))
                    .andExpect(jsonPath("$.operands[0]").value(6))
                    .andExpect(jsonPath("$.operands[1]").value(7));

            verify(mathService, times(1)).multiply(6, 7);
        }

        @Test
        @DisplayName("Should return error for invalid input")
        void testMultiplyInvalidInput() throws Exception {
            // Given
            Map<String, Object> request = Map.of("a", "invalid", "b", 7);

            // When & Then
            mockMvc.perform(post("/api/math/multiply")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").value("Invalid input"));

            verify(mathService, never()).multiply(anyInt(), anyInt());
        }

        @Test
        @DisplayName("Should return error when service throws exception")
        void testMultiplyServiceException() throws Exception {
            // Given
            Map<String, Object> request = Map.of("a", Integer.MAX_VALUE, "b", 2);
            when(mathService.multiply(Integer.MAX_VALUE, 2)).thenThrow(new RuntimeException("Overflow"));

            // When & Then
            mockMvc.perform(post("/api/math/multiply")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").value("Invalid input"));

            verify(mathService, times(1)).multiply(Integer.MAX_VALUE, 2);
        }

        @Test
        @DisplayName("Should handle multiplication with zero")
        void testMultiplyWithZero() throws Exception {
            // Given
            Map<String, Object> request = Map.of("a", 5, "b", 0);
            when(mathService.multiply(5, 0)).thenReturn(0);

            // When & Then
            mockMvc.perform(post("/api/math/multiply")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(0))
                    .andExpect(jsonPath("$.operation").value("multiply"));

            verify(mathService, times(1)).multiply(5, 0);
        }
    }
}