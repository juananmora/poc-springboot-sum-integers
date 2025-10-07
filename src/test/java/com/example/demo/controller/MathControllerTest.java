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
import java.util.Collections;
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

    @Nested
    @DisplayName("POST /api/math/mean endpoint tests")
    class MeanEndpointTests {

        @Test
        @DisplayName("Should calculate mean for valid number list")
        void testMeanValidNumbers() throws Exception {
            // Given - Escenario 1: [1, 2, 3, 4, 5] -> 3.0
            List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
            Map<String, List<Integer>> request = Map.of("numbers", numbers);
            when(mathService.calculateMean(numbers)).thenReturn(3.0);

            // When & Then
            mockMvc.perform(post("/api/math/mean")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(3.0))
                    .andExpect(jsonPath("$.operation").value("mean"))
                    .andExpect(jsonPath("$.operands[0]").value(1))
                    .andExpect(jsonPath("$.operands[1]").value(2))
                    .andExpect(jsonPath("$.operands[2]").value(3))
                    .andExpect(jsonPath("$.operands[3]").value(4))
                    .andExpect(jsonPath("$.operands[4]").value(5));

            verify(mathService, times(1)).calculateMean(numbers);
        }

        @Test
        @DisplayName("Should calculate mean for single element list")
        void testMeanSingleElement() throws Exception {
            // Given - Escenario 2: [7] -> 7.0
            List<Integer> numbers = Arrays.asList(7);
            Map<String, List<Integer>> request = Map.of("numbers", numbers);
            when(mathService.calculateMean(numbers)).thenReturn(7.0);

            // When & Then
            mockMvc.perform(post("/api/math/mean")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(7.0))
                    .andExpect(jsonPath("$.operation").value("mean"))
                    .andExpect(jsonPath("$.operands[0]").value(7));

            verify(mathService, times(1)).calculateMean(numbers);
        }

        @Test
        @DisplayName("Should return 0.0 for empty list")
        void testMeanEmptyList() throws Exception {
            // Given - Escenario 3: [] -> 0.0
            List<Integer> numbers = Collections.emptyList();
            Map<String, List<Integer>> request = Map.of("numbers", numbers);
            when(mathService.calculateMean(numbers)).thenReturn(0.0);

            // When & Then
            mockMvc.perform(post("/api/math/mean")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(0.0))
                    .andExpect(jsonPath("$.operation").value("mean"))
                    .andExpect(jsonPath("$.operands").isEmpty());

            verify(mathService, times(1)).calculateMean(numbers);
        }

        @Test
        @DisplayName("Should calculate mean with negative numbers")
        void testMeanWithNegatives() throws Exception {
            // Given - Escenario 4: [-1, 0, 1] -> 0.0
            List<Integer> numbers = Arrays.asList(-1, 0, 1);
            Map<String, List<Integer>> request = Map.of("numbers", numbers);
            when(mathService.calculateMean(numbers)).thenReturn(0.0);

            // When & Then
            mockMvc.perform(post("/api/math/mean")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(0.0))
                    .andExpect(jsonPath("$.operation").value("mean"))
                    .andExpect(jsonPath("$.operands[0]").value(-1))
                    .andExpect(jsonPath("$.operands[1]").value(0))
                    .andExpect(jsonPath("$.operands[2]").value(1));

            verify(mathService, times(1)).calculateMean(numbers);
        }

        @Test
        @DisplayName("Should return error for invalid input")
        void testMeanInvalidInput() throws Exception {
            // Given
            Map<String, Object> request = Map.of("numbers", "invalid");

            // When & Then
            mockMvc.perform(post("/api/math/mean")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").value("Invalid input"));

            verify(mathService, never()).calculateMean(anyList());
        }

        @Test
        @DisplayName("Should handle null numbers in request")
        void testMeanNullNumbers() throws Exception {
            // Given
            Map<String, List<Integer>> request = Map.of();
            when(mathService.calculateMean(null)).thenReturn(0.0);

            // When & Then
            mockMvc.perform(post("/api/math/mean")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(0.0))
                    .andExpect(jsonPath("$.operation").value("mean"))
                    .andExpect(jsonPath("$.operands").isEmpty());

            verify(mathService, times(1)).calculateMean(null);
        }
    }

    @Nested
    @DisplayName("POST /api/math/quicksort endpoint tests")
    class QuicksortEndpointTests {

        @Test
        @DisplayName("Should sort valid number list")
        void testQuicksortValidNumbers() throws Exception {
            // Given
            List<Integer> numbers = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6);
            List<Integer> sortedNumbers = Arrays.asList(1, 1, 2, 3, 4, 5, 6, 9);
            Map<String, List<Integer>> request = Map.of("numbers", numbers);
            when(mathService.quicksort(numbers)).thenReturn(sortedNumbers);

            // When & Then
            mockMvc.perform(post("/api/math/quicksort")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").isArray())
                    .andExpect(jsonPath("$.result[0]").value(1))
                    .andExpect(jsonPath("$.result[1]").value(1))
                    .andExpect(jsonPath("$.result[2]").value(2))
                    .andExpect(jsonPath("$.result[3]").value(3))
                    .andExpect(jsonPath("$.result[4]").value(4))
                    .andExpect(jsonPath("$.result[5]").value(5))
                    .andExpect(jsonPath("$.result[6]").value(6))
                    .andExpect(jsonPath("$.result[7]").value(9))
                    .andExpect(jsonPath("$.operation").value("quicksort"))
                    .andExpect(jsonPath("$.operands").isArray())
                    .andExpect(jsonPath("$.operands[0]").value(3))
                    .andExpect(jsonPath("$.operands[1]").value(1));

            verify(mathService, times(1)).quicksort(numbers);
        }

        @Test
        @DisplayName("Should sort single element list")
        void testQuicksortSingleElement() throws Exception {
            // Given
            List<Integer> numbers = Arrays.asList(42);
            List<Integer> sortedNumbers = Arrays.asList(42);
            Map<String, List<Integer>> request = Map.of("numbers", numbers);
            when(mathService.quicksort(numbers)).thenReturn(sortedNumbers);

            // When & Then
            mockMvc.perform(post("/api/math/quicksort")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").isArray())
                    .andExpect(jsonPath("$.result[0]").value(42))
                    .andExpect(jsonPath("$.operation").value("quicksort"))
                    .andExpect(jsonPath("$.operands[0]").value(42));

            verify(mathService, times(1)).quicksort(numbers);
        }

        @Test
        @DisplayName("Should sort empty list")
        void testQuicksortEmptyList() throws Exception {
            // Given
            List<Integer> numbers = Collections.emptyList();
            List<Integer> sortedNumbers = Collections.emptyList();
            Map<String, List<Integer>> request = Map.of("numbers", numbers);
            when(mathService.quicksort(numbers)).thenReturn(sortedNumbers);

            // When & Then
            mockMvc.perform(post("/api/math/quicksort")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").isArray())
                    .andExpect(jsonPath("$.result").isEmpty())
                    .andExpect(jsonPath("$.operation").value("quicksort"))
                    .andExpect(jsonPath("$.operands").isEmpty());

            verify(mathService, times(1)).quicksort(numbers);
        }

        @Test
        @DisplayName("Should sort list with negative numbers")
        void testQuicksortWithNegatives() throws Exception {
            // Given
            List<Integer> numbers = Arrays.asList(-3, 1, -2, 5, 0);
            List<Integer> sortedNumbers = Arrays.asList(-3, -2, 0, 1, 5);
            Map<String, List<Integer>> request = Map.of("numbers", numbers);
            when(mathService.quicksort(numbers)).thenReturn(sortedNumbers);

            // When & Then
            mockMvc.perform(post("/api/math/quicksort")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").isArray())
                    .andExpect(jsonPath("$.result[0]").value(-3))
                    .andExpect(jsonPath("$.result[1]").value(-2))
                    .andExpect(jsonPath("$.result[2]").value(0))
                    .andExpect(jsonPath("$.result[3]").value(1))
                    .andExpect(jsonPath("$.result[4]").value(5))
                    .andExpect(jsonPath("$.operation").value("quicksort"));

            verify(mathService, times(1)).quicksort(numbers);
        }

        @Test
        @DisplayName("Should sort already sorted list")
        void testQuicksortAlreadySorted() throws Exception {
            // Given
            List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
            List<Integer> sortedNumbers = Arrays.asList(1, 2, 3, 4, 5);
            Map<String, List<Integer>> request = Map.of("numbers", numbers);
            when(mathService.quicksort(numbers)).thenReturn(sortedNumbers);

            // When & Then
            mockMvc.perform(post("/api/math/quicksort")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").isArray())
                    .andExpect(jsonPath("$.result[0]").value(1))
                    .andExpect(jsonPath("$.result[1]").value(2))
                    .andExpect(jsonPath("$.result[2]").value(3))
                    .andExpect(jsonPath("$.result[3]").value(4))
                    .andExpect(jsonPath("$.result[4]").value(5))
                    .andExpect(jsonPath("$.operation").value("quicksort"));

            verify(mathService, times(1)).quicksort(numbers);
        }

        @Test
        @DisplayName("Should sort reverse sorted list")
        void testQuicksortReverseSorted() throws Exception {
            // Given
            List<Integer> numbers = Arrays.asList(5, 4, 3, 2, 1);
            List<Integer> sortedNumbers = Arrays.asList(1, 2, 3, 4, 5);
            Map<String, List<Integer>> request = Map.of("numbers", numbers);
            when(mathService.quicksort(numbers)).thenReturn(sortedNumbers);

            // When & Then
            mockMvc.perform(post("/api/math/quicksort")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").isArray())
                    .andExpect(jsonPath("$.result[0]").value(1))
                    .andExpect(jsonPath("$.result[1]").value(2))
                    .andExpect(jsonPath("$.result[2]").value(3))
                    .andExpect(jsonPath("$.result[3]").value(4))
                    .andExpect(jsonPath("$.result[4]").value(5))
                    .andExpect(jsonPath("$.operation").value("quicksort"));

            verify(mathService, times(1)).quicksort(numbers);
        }

        @Test
        @DisplayName("Should return error for invalid input")
        void testQuicksortInvalidInput() throws Exception {
            // Given
            Map<String, Object> request = Map.of("numbers", "invalid");

            // When & Then
            mockMvc.perform(post("/api/math/quicksort")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").value("Invalid input"));

            verify(mathService, never()).quicksort(anyList());
        }

        @Test
        @DisplayName("Should handle null numbers in request")
        void testQuicksortNullNumbers() throws Exception {
            // Given
            Map<String, List<Integer>> request = Map.of();
            when(mathService.quicksort(null)).thenReturn(Collections.emptyList());

            // When & Then
            mockMvc.perform(post("/api/math/quicksort")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").isArray())
                    .andExpect(jsonPath("$.result").isEmpty())
                    .andExpect(jsonPath("$.operation").value("quicksort"))
                    .andExpect(jsonPath("$.operands").isEmpty());

            verify(mathService, times(1)).quicksort(null);
        }

        @Test
        @DisplayName("Should return error when service throws exception")
        void testQuicksortServiceException() throws Exception {
            // Given
            List<Integer> numbers = Arrays.asList(1, 2, 3);
            Map<String, List<Integer>> request = Map.of("numbers", numbers);
            when(mathService.quicksort(numbers)).thenThrow(new RuntimeException("Service error"));

            // When & Then
            mockMvc.perform(post("/api/math/quicksort")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").value("Invalid input"));

            verify(mathService, times(1)).quicksort(numbers);
        }

        @Test
        @DisplayName("Should handle list with duplicates")
        void testQuicksortWithDuplicates() throws Exception {
            // Given
            List<Integer> numbers = Arrays.asList(3, 1, 3, 1, 2);
            List<Integer> sortedNumbers = Arrays.asList(1, 1, 2, 3, 3);
            Map<String, List<Integer>> request = Map.of("numbers", numbers);
            when(mathService.quicksort(numbers)).thenReturn(sortedNumbers);

            // When & Then
            mockMvc.perform(post("/api/math/quicksort")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").isArray())
                    .andExpect(jsonPath("$.result[0]").value(1))
                    .andExpect(jsonPath("$.result[1]").value(1))
                    .andExpect(jsonPath("$.result[2]").value(2))
                    .andExpect(jsonPath("$.result[3]").value(3))
                    .andExpect(jsonPath("$.result[4]").value(3))
                    .andExpect(jsonPath("$.operation").value("quicksort"));

            verify(mathService, times(1)).quicksort(numbers);
        }
    }

    @Nested
    @DisplayName("Quicksort Rational Numbers Endpoint Tests")
    class QuicksortRationalEndpointTests {

        @Test
        @DisplayName("Should sort list of rational numbers successfully")
        void testQuicksortRationalSuccess() throws Exception {
            // Given
            List<Double> numbers = Arrays.asList(3.14, 1.41, 2.71, 9.81);
            List<Double> sortedNumbers = Arrays.asList(1.41, 2.71, 3.14, 9.81);
            Map<String, List<Double>> request = Map.of("numbers", numbers);
            when(mathService.quicksortRational(numbers)).thenReturn(sortedNumbers);

            // When & Then
            mockMvc.perform(post("/api/math/quicksort-rational")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").isArray())
                    .andExpect(jsonPath("$.result[0]").value(1.41))
                    .andExpect(jsonPath("$.result[1]").value(2.71))
                    .andExpect(jsonPath("$.result[2]").value(3.14))
                    .andExpect(jsonPath("$.result[3]").value(9.81))
                    .andExpect(jsonPath("$.operation").value("quicksort-rational"))
                    .andExpect(jsonPath("$.operands").isArray())
                    .andExpect(jsonPath("$.operands[0]").value(3.14))
                    .andExpect(jsonPath("$.operands[1]").value(1.41))
                    .andExpect(jsonPath("$.operands[2]").value(2.71))
                    .andExpect(jsonPath("$.operands[3]").value(9.81));

            verify(mathService, times(1)).quicksortRational(numbers);
        }

        @Test
        @DisplayName("Should handle single decimal element")
        void testQuicksortRationalSingleElement() throws Exception {
            // Given
            List<Double> numbers = Arrays.asList(42.5);
            List<Double> sortedNumbers = Arrays.asList(42.5);
            Map<String, List<Double>> request = Map.of("numbers", numbers);
            when(mathService.quicksortRational(numbers)).thenReturn(sortedNumbers);

            // When & Then
            mockMvc.perform(post("/api/math/quicksort-rational")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").isArray())
                    .andExpect(jsonPath("$.result[0]").value(42.5))
                    .andExpect(jsonPath("$.operation").value("quicksort-rational"));

            verify(mathService, times(1)).quicksortRational(numbers);
        }

        @Test
        @DisplayName("Should sort decimal numbers with negative values")
        void testQuicksortRationalWithNegatives() throws Exception {
            // Given
            List<Double> numbers = Arrays.asList(-2.5, 1.5, 0.0, -1.5, 2.5);
            List<Double> sortedNumbers = Arrays.asList(-2.5, -1.5, 0.0, 1.5, 2.5);
            Map<String, List<Double>> request = Map.of("numbers", numbers);
            when(mathService.quicksortRational(numbers)).thenReturn(sortedNumbers);

            // When & Then
            mockMvc.perform(post("/api/math/quicksort-rational")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").isArray())
                    .andExpect(jsonPath("$.result[0]").value(-2.5))
                    .andExpect(jsonPath("$.result[1]").value(-1.5))
                    .andExpect(jsonPath("$.result[2]").value(0.0))
                    .andExpect(jsonPath("$.result[3]").value(1.5))
                    .andExpect(jsonPath("$.result[4]").value(2.5))
                    .andExpect(jsonPath("$.operation").value("quicksort-rational"));

            verify(mathService, times(1)).quicksortRational(numbers);
        }

        @Test
        @DisplayName("Should handle empty rational list")
        void testQuicksortRationalEmptyList() throws Exception {
            // Given
            List<Double> numbers = Collections.emptyList();
            List<Double> sortedNumbers = Collections.emptyList();
            Map<String, List<Double>> request = Map.of("numbers", numbers);
            when(mathService.quicksortRational(numbers)).thenReturn(sortedNumbers);

            // When & Then
            mockMvc.perform(post("/api/math/quicksort-rational")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").isArray())
                    .andExpect(jsonPath("$.result").isEmpty())
                    .andExpect(jsonPath("$.operation").value("quicksort-rational"))
                    .andExpect(jsonPath("$.operands").isEmpty());

            verify(mathService, times(1)).quicksortRational(numbers);
        }

        @Test
        @DisplayName("Should handle null rational list")
        void testQuicksortRationalNullList() throws Exception {
            // Given
            when(mathService.quicksortRational(null)).thenReturn(Collections.emptyList());

            // When & Then
            mockMvc.perform(post("/api/math/quicksort-rational")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"numbers\": null}"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").isArray())
                    .andExpect(jsonPath("$.result").isEmpty())
                    .andExpect(jsonPath("$.operation").value("quicksort-rational"))
                    .andExpect(jsonPath("$.operands").isEmpty());

            verify(mathService, times(1)).quicksortRational(null);
        }

        @Test
        @DisplayName("Should return error when service throws exception")
        void testQuicksortRationalServiceException() throws Exception {
            // Given
            List<Double> numbers = Arrays.asList(1.1, 2.2, 3.3);
            Map<String, List<Double>> request = Map.of("numbers", numbers);
            when(mathService.quicksortRational(numbers)).thenThrow(new RuntimeException("Service error"));

            // When & Then
            mockMvc.perform(post("/api/math/quicksort-rational")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").value("Invalid input"));

            verify(mathService, times(1)).quicksortRational(numbers);
        }

        @Test
        @DisplayName("Should handle rational list with duplicate values")
        void testQuicksortRationalWithDuplicates() throws Exception {
            // Given
            List<Double> numbers = Arrays.asList(3.3, 1.1, 3.3, 1.1, 2.2);
            List<Double> sortedNumbers = Arrays.asList(1.1, 1.1, 2.2, 3.3, 3.3);
            Map<String, List<Double>> request = Map.of("numbers", numbers);
            when(mathService.quicksortRational(numbers)).thenReturn(sortedNumbers);

            // When & Then
            mockMvc.perform(post("/api/math/quicksort-rational")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").isArray())
                    .andExpect(jsonPath("$.result[0]").value(1.1))
                    .andExpect(jsonPath("$.result[1]").value(1.1))
                    .andExpect(jsonPath("$.result[2]").value(2.2))
                    .andExpect(jsonPath("$.result[3]").value(3.3))
                    .andExpect(jsonPath("$.result[4]").value(3.3))
                    .andExpect(jsonPath("$.operation").value("quicksort-rational"));

            verify(mathService, times(1)).quicksortRational(numbers);
        }

        @Test
        @DisplayName("Should handle very small decimal differences")
        void testQuicksortRationalSmallDifferences() throws Exception {
            // Given
            List<Double> numbers = Arrays.asList(1.0001, 1.0002, 1.0000);
            List<Double> sortedNumbers = Arrays.asList(1.0000, 1.0001, 1.0002);
            Map<String, List<Double>> request = Map.of("numbers", numbers);
            when(mathService.quicksortRational(numbers)).thenReturn(sortedNumbers);

            // When & Then
            mockMvc.perform(post("/api/math/quicksort-rational")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").isArray())
                    .andExpect(jsonPath("$.result[0]").value(1.0000))
                    .andExpect(jsonPath("$.result[1]").value(1.0001))
                    .andExpect(jsonPath("$.result[2]").value(1.0002))
                    .andExpect(jsonPath("$.operation").value("quicksort-rational"));

            verify(mathService, times(1)).quicksortRational(numbers);
        }
    }

    @Nested
    @DisplayName("POST /api/math/mode endpoint tests")
    class ModeEndpointTests {

        @Test
        @DisplayName("Should calculate mode for valid number list with clear mode")
        void testModeValidNumbersWithClearMode() throws Exception {
            // Given
            List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 2);
            List<Integer> modeResult = Arrays.asList(2);
            Map<String, List<Integer>> request = Map.of("numbers", numbers);
            when(mathService.calculateMode(numbers)).thenReturn(modeResult);

            // When & Then
            mockMvc.perform(post("/api/math/mode")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").isArray())
                    .andExpect(jsonPath("$.result[0]").value(2))
                    .andExpect(jsonPath("$.operation").value("mode"))
                    .andExpect(jsonPath("$.operands").isArray())
                    .andExpect(jsonPath("$.operands[0]").value(1))
                    .andExpect(jsonPath("$.operands[1]").value(2))
                    .andExpect(jsonPath("$.operands[2]").value(2))
                    .andExpect(jsonPath("$.operands[3]").value(3))
                    .andExpect(jsonPath("$.operands[4]").value(2));

            verify(mathService, times(1)).calculateMode(numbers);
        }

        @Test
        @DisplayName("Should calculate mode for list with multiple modes")
        void testModeMultipleModes() throws Exception {
            // Given
            List<Integer> numbers = Arrays.asList(1, 1, 2, 2, 3);
            List<Integer> modeResult = Arrays.asList(1, 2);
            Map<String, List<Integer>> request = Map.of("numbers", numbers);
            when(mathService.calculateMode(numbers)).thenReturn(modeResult);

            // When & Then
            mockMvc.perform(post("/api/math/mode")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").isArray())
                    .andExpect(jsonPath("$.result[0]").value(1))
                    .andExpect(jsonPath("$.result[1]").value(2))
                    .andExpect(jsonPath("$.operation").value("mode"));

            verify(mathService, times(1)).calculateMode(numbers);
        }

        @Test
        @DisplayName("Should calculate mode for single element list")
        void testModeSingleElement() throws Exception {
            // Given
            List<Integer> numbers = Arrays.asList(42);
            List<Integer> modeResult = Arrays.asList(42);
            Map<String, List<Integer>> request = Map.of("numbers", numbers);
            when(mathService.calculateMode(numbers)).thenReturn(modeResult);

            // When & Then
            mockMvc.perform(post("/api/math/mode")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").isArray())
                    .andExpect(jsonPath("$.result[0]").value(42))
                    .andExpect(jsonPath("$.operation").value("mode"))
                    .andExpect(jsonPath("$.operands[0]").value(42));

            verify(mathService, times(1)).calculateMode(numbers);
        }

        @Test
        @DisplayName("Should return empty result for empty list")
        void testModeEmptyList() throws Exception {
            // Given
            List<Integer> numbers = Collections.emptyList();
            List<Integer> modeResult = Collections.emptyList();
            Map<String, List<Integer>> request = Map.of("numbers", numbers);
            when(mathService.calculateMode(numbers)).thenReturn(modeResult);

            // When & Then
            mockMvc.perform(post("/api/math/mode")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").isArray())
                    .andExpect(jsonPath("$.result").isEmpty())
                    .andExpect(jsonPath("$.operation").value("mode"))
                    .andExpect(jsonPath("$.operands").isEmpty());

            verify(mathService, times(1)).calculateMode(numbers);
        }

        @Test
        @DisplayName("Should handle mode calculation with negative numbers")
        void testModeWithNegativeNumbers() throws Exception {
            // Given
            List<Integer> numbers = Arrays.asList(-1, -1, 0, 1, -1);
            List<Integer> modeResult = Arrays.asList(-1);
            Map<String, List<Integer>> request = Map.of("numbers", numbers);
            when(mathService.calculateMode(numbers)).thenReturn(modeResult);

            // When & Then
            mockMvc.perform(post("/api/math/mode")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").isArray())
                    .andExpect(jsonPath("$.result[0]").value(-1))
                    .andExpect(jsonPath("$.operation").value("mode"));

            verify(mathService, times(1)).calculateMode(numbers);
        }

        @Test
        @DisplayName("Should handle all elements with same frequency")
        void testModeAllSameFrequency() throws Exception {
            // Given
            List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
            List<Integer> modeResult = Arrays.asList(1, 2, 3, 4);
            Map<String, List<Integer>> request = Map.of("numbers", numbers);
            when(mathService.calculateMode(numbers)).thenReturn(modeResult);

            // When & Then
            mockMvc.perform(post("/api/math/mode")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").isArray())
                    .andExpect(jsonPath("$.result[0]").value(1))
                    .andExpect(jsonPath("$.result[1]").value(2))
                    .andExpect(jsonPath("$.result[2]").value(3))
                    .andExpect(jsonPath("$.result[3]").value(4))
                    .andExpect(jsonPath("$.operation").value("mode"));

            verify(mathService, times(1)).calculateMode(numbers);
        }

        @Test
        @DisplayName("Should return error for invalid input")
        void testModeInvalidInput() throws Exception {
            // Given
            Map<String, Object> request = Map.of("numbers", "invalid");

            // When & Then
            mockMvc.perform(post("/api/math/mode")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").value("Invalid input"));

            verify(mathService, never()).calculateMode(anyList());
        }

        @Test
        @DisplayName("Should handle null numbers in request")
        void testModeNullNumbers() throws Exception {
            // Given
            Map<String, List<Integer>> request = Map.of();
            when(mathService.calculateMode(null)).thenReturn(Collections.emptyList());

            // When & Then
            mockMvc.perform(post("/api/math/mode")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").isArray())
                    .andExpect(jsonPath("$.result").isEmpty())
                    .andExpect(jsonPath("$.operation").value("mode"))
                    .andExpect(jsonPath("$.operands").isEmpty());

            verify(mathService, times(1)).calculateMode(null);
        }

        @Test
        @DisplayName("Should return error when service throws exception")
        void testModeServiceException() throws Exception {
            // Given
            List<Integer> numbers = Arrays.asList(1, 2, 3);
            Map<String, List<Integer>> request = Map.of("numbers", numbers);
            when(mathService.calculateMode(numbers)).thenThrow(new RuntimeException("Service error"));

            // When & Then
            mockMvc.perform(post("/api/math/mode")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").value("Invalid input"));

            verify(mathService, times(1)).calculateMode(numbers);
        }

        @Test
        @DisplayName("Should handle complex mode pattern")
        void testModeComplexPattern() throws Exception {
            // Given
            List<Integer> numbers = Arrays.asList(1, 2, 3, 1, 2, 1, 4, 4, 4);
            List<Integer> modeResult = Arrays.asList(1, 4);
            Map<String, List<Integer>> request = Map.of("numbers", numbers);
            when(mathService.calculateMode(numbers)).thenReturn(modeResult);

            // When & Then
            mockMvc.perform(post("/api/math/mode")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").isArray())
                    .andExpect(jsonPath("$.result[0]").value(1))
                    .andExpect(jsonPath("$.result[1]").value(4))
                    .andExpect(jsonPath("$.operation").value("mode"));

            verify(mathService, times(1)).calculateMode(numbers);
        }
    }

    @Nested
    @DisplayName("POST /api/math/factorial endpoint tests")
    class FactorialEndpointTests {

        @Test
        @DisplayName("Should return factorial for 0")
        void testFactorialZero() throws Exception {
            // Given
            Map<String, Object> request = Map.of("number", 0);
            when(mathService.factorial(0)).thenReturn(1L);

            // When & Then
            mockMvc.perform(post("/api/math/factorial")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(1))
                    .andExpect(jsonPath("$.operation").value("factorial"))
                    .andExpect(jsonPath("$.operand").value(0));

            verify(mathService, times(1)).factorial(0);
        }

        @Test
        @DisplayName("Should return factorial for 1")
        void testFactorialOne() throws Exception {
            // Given
            Map<String, Object> request = Map.of("number", 1);
            when(mathService.factorial(1)).thenReturn(1L);

            // When & Then
            mockMvc.perform(post("/api/math/factorial")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(1))
                    .andExpect(jsonPath("$.operation").value("factorial"))
                    .andExpect(jsonPath("$.operand").value(1));

            verify(mathService, times(1)).factorial(1);
        }

        @Test
        @DisplayName("Should return factorial for 5 = 120")
        void testFactorialFive() throws Exception {
            // Given
            Map<String, Object> request = Map.of("number", 5);
            when(mathService.factorial(5)).thenReturn(120L);

            // When & Then
            mockMvc.perform(post("/api/math/factorial")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(120))
                    .andExpect(jsonPath("$.operation").value("factorial"))
                    .andExpect(jsonPath("$.operand").value(5));

            verify(mathService, times(1)).factorial(5);
        }

        @Test
        @DisplayName("Should return factorial for 10 = 3,628,800")
        void testFactorialTen() throws Exception {
            // Given
            Map<String, Object> request = Map.of("number", 10);
            when(mathService.factorial(10)).thenReturn(3628800L);

            // When & Then
            mockMvc.perform(post("/api/math/factorial")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(3628800))
                    .andExpect(jsonPath("$.operation").value("factorial"))
                    .andExpect(jsonPath("$.operand").value(10));

            verify(mathService, times(1)).factorial(10);
        }

        @Test
        @DisplayName("Should return factorial for 20 (maximum valid value)")
        void testFactorialTwenty() throws Exception {
            // Given
            Map<String, Object> request = Map.of("number", 20);
            when(mathService.factorial(20)).thenReturn(2432902008176640000L);

            // When & Then
            mockMvc.perform(post("/api/math/factorial")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(2432902008176640000L))
                    .andExpect(jsonPath("$.operation").value("factorial"))
                    .andExpect(jsonPath("$.operand").value(20));

            verify(mathService, times(1)).factorial(20);
        }

        @Test
        @DisplayName("Should return error for negative number")
        void testFactorialNegative() throws Exception {
            // Given
            Map<String, Object> request = Map.of("number", -5);
            when(mathService.factorial(-5))
                    .thenThrow(new IllegalArgumentException("No se puede calcular el factorial de un número negativo"));

            // When & Then
            mockMvc.perform(post("/api/math/factorial")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").value("No se puede calcular el factorial de un número negativo"));

            verify(mathService, times(1)).factorial(-5);
        }

        @Test
        @DisplayName("Should return error for overflow (number > 20)")
        void testFactorialOverflow() throws Exception {
            // Given
            Map<String, Object> request = Map.of("number", 21);
            when(mathService.factorial(21))
                    .thenThrow(new IllegalArgumentException("El factorial de 21 excede el límite de Long.MAX_VALUE (máximo: 20!)"));

            // When & Then
            mockMvc.perform(post("/api/math/factorial")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").exists())
                    .andExpect(jsonPath("$.error").value(org.hamcrest.Matchers.containsString("excede el límite")));

            verify(mathService, times(1)).factorial(21);
        }

        @Test
        @DisplayName("Should return error when number field is missing")
        void testFactorialMissingField() throws Exception {
            // Given
            Map<String, Object> request = Map.of();

            // When & Then
            mockMvc.perform(post("/api/math/factorial")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").value("El campo 'number' es obligatorio"));

            verify(mathService, never()).factorial(anyInt());
        }

        @Test
        @DisplayName("Should return error for non-numeric input")
        void testFactorialNonNumeric() throws Exception {
            // Given
            String request = "{\"number\": \"not-a-number\"}";

            // When & Then
            mockMvc.perform(post("/api/math/factorial")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").exists());

            verify(mathService, never()).factorial(anyInt());
        }

        @Test
        @DisplayName("Should return error for null number")
        void testFactorialNullNumber() throws Exception {
            // Given - usando un string JSON con valor null
            String request = "{\"number\": null}";

            // When & Then
            mockMvc.perform(post("/api/math/factorial")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").exists());

            verify(mathService, never()).factorial(anyInt());
        }

        @Test
        @DisplayName("Should return error for empty request body")
        void testFactorialEmptyBody() throws Exception {
            // When & Then
            mockMvc.perform(post("/api/math/factorial")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(""))
                    .andExpect(status().isBadRequest());

            verify(mathService, never()).factorial(anyInt());
        }

        @Test
        @DisplayName("Should handle double number input by converting to int")
        void testFactorialDoubleInput() throws Exception {
            // Given
            Map<String, Object> request = Map.of("number", 5.0);
            when(mathService.factorial(5)).thenReturn(120L);

            // When & Then
            mockMvc.perform(post("/api/math/factorial")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(120))
                    .andExpect(jsonPath("$.operation").value("factorial"))
                    .andExpect(jsonPath("$.operand").value(5));

            verify(mathService, times(1)).factorial(5);
        }

        @Test
        @DisplayName("Should calculate factorial for intermediate values")
        void testFactorialIntermediateValues() throws Exception {
            // Given
            Map<String, Object> request = Map.of("number", 12);
            when(mathService.factorial(12)).thenReturn(479001600L);

            // When & Then
            mockMvc.perform(post("/api/math/factorial")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(479001600L))
                    .andExpect(jsonPath("$.operation").value("factorial"))
                    .andExpect(jsonPath("$.operand").value(12));

            verify(mathService, times(1)).factorial(12);
        }

        @Test
        @DisplayName("Should return consistent error format")
        void testFactorialErrorFormat() throws Exception {
            // Given
            Map<String, Object> request = Map.of("number", -1);
            when(mathService.factorial(-1))
                    .thenThrow(new IllegalArgumentException("Test error message"));

            // When & Then
            mockMvc.perform(post("/api/math/factorial")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").exists())
                    .andExpect(jsonPath("$.result").doesNotExist())
                    .andExpect(jsonPath("$.operation").doesNotExist());

            verify(mathService, times(1)).factorial(-1);
        }

        @Test
        @DisplayName("Should validate service is called with correct parameter")
        void testFactorialServiceInteraction() throws Exception {
            // Given
            Map<String, Object> request = Map.of("number", 7);
            when(mathService.factorial(7)).thenReturn(5040L);

            // When
            mockMvc.perform(post("/api/math/factorial")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());

            // Then
            verify(mathService, times(1)).factorial(7);
            verifyNoMoreInteractions(mathService);
        }
    }
}