package com.example.demo.controller;

import com.example.demo.service.MathService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AdditionController Unit Tests")
class AdditionControllerUnitTest {

    @Mock
    private MathService mathService;

    @InjectMocks
    private AdditionController additionController;

    @Nested
    @DisplayName("Square Root Endpoint Tests")
    class SqrtEndpointTests {

        @Test
        @DisplayName("Should return square root for valid positive number")
        void testSqrtValidNumber() {
            // Given
            double input = 9.0;
            double expectedResult = 3.0;
            when(mathService.sqrt(input)).thenReturn(expectedResult);

            // When
            ResponseEntity<Double> response = additionController.sqrt(input);

            // Then
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(expectedResult, response.getBody());
            verify(mathService, times(1)).sqrt(input);
        }

        @Test
        @DisplayName("Should return zero for sqrt(0)")
        void testSqrtZero() {
            // Given
            double input = 0.0;
            double expectedResult = 0.0;
            when(mathService.sqrt(input)).thenReturn(expectedResult);

            // When
            ResponseEntity<Double> response = additionController.sqrt(input);

            // Then
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(expectedResult, response.getBody());
            verify(mathService, times(1)).sqrt(input);
        }

        @Test
        @DisplayName("Should return BadRequest for negative number")
        void testSqrtNegativeNumber() {
            // Given
            double input = -4.0;
            when(mathService.sqrt(input)).thenThrow(new IllegalArgumentException("No se puede calcular la raíz cuadrada de un número negativo"));

            // When
            ResponseEntity<Double> response = additionController.sqrt(input);

            // Then
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
            verify(mathService, times(1)).sqrt(input);
        }

        @Test
        @DisplayName("Should handle decimal input correctly")
        void testSqrtDecimalInput() {
            // Given
            double input = 2.25;
            double expectedResult = 1.5;
            when(mathService.sqrt(input)).thenReturn(expectedResult);

            // When
            ResponseEntity<Double> response = additionController.sqrt(input);

            // Then
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(expectedResult, response.getBody());
            verify(mathService, times(1)).sqrt(input);
        }
    }

    @Nested
    @DisplayName("Addition Endpoint Tests")
    class AddEndpointTests {

        @Test
        @DisplayName("Should add two positive numbers")
        void testAddPositiveNumbers() {
            // Given
            int num1 = 3;
            int num2 = 5;
            int expectedResult = 8;
            when(mathService.add(num1, num2)).thenReturn(expectedResult);

            // When
            ResponseEntity<Integer> response = additionController.add(num1, num2);

            // Then
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(expectedResult, response.getBody());
            verify(mathService, times(1)).add(num1, num2);
        }

        @Test
        @DisplayName("Should return BadRequest for negative numbers")
        void testAddNegativeNumbers() {
            // Given
            int num1 = -1;
            int num2 = 5;
            when(mathService.add(num1, num2)).thenThrow(new IllegalArgumentException("Los números no pueden ser negativos"));

            // When
            ResponseEntity<Integer> response = additionController.add(num1, num2);

            // Then
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
            verify(mathService, times(1)).add(num1, num2);
        }
    }

    @Nested
    @DisplayName("Other Operations Tests")
    class OtherOperationsTests {

        @Test
        @DisplayName("Should multiply two numbers")
        void testMultiply() {
            // Given
            int num1 = 4;
            int num2 = 6;
            int expectedResult = 24;
            when(mathService.multiply(num1, num2)).thenReturn(expectedResult);

            // When
            ResponseEntity<Integer> response = additionController.multiply(num1, num2);

            // Then
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(expectedResult, response.getBody());
            verify(mathService, times(1)).multiply(num1, num2);
        }

        @Test
        @DisplayName("Should divide two numbers")
        void testDivide() {
            // Given
            int num1 = 15;
            int num2 = 3;
            double expectedResult = 5.0;
            when(mathService.divide(num1, num2)).thenReturn(expectedResult);

            // When
            ResponseEntity<Double> response = additionController.divide(num1, num2);

            // Then
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(expectedResult, response.getBody());
            verify(mathService, times(1)).divide(num1, num2);
        }

        @Test
        @DisplayName("Should return BadRequest for division by zero")
        void testDivideByZero() {
            // Given
            int num1 = 10;
            int num2 = 0;
            when(mathService.divide(num1, num2)).thenThrow(new ArithmeticException("No se puede dividir por cero"));

            // When
            ResponseEntity<Double> response = additionController.divide(num1, num2);

            // Then
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
            verify(mathService, times(1)).divide(num1, num2);
        }

        @Test
        @DisplayName("Should subtract two numbers")
        void testSubtract() {
            // Given
            int num1 = 10;
            int num2 = 4;
            int expectedResult = 6;
            when(mathService.subtract(num1, num2)).thenReturn(expectedResult);

            // When
            ResponseEntity<Integer> response = additionController.subtract(num1, num2);

            // Then
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(expectedResult, response.getBody());
            verify(mathService, times(1)).subtract(num1, num2);
        }
    }
}