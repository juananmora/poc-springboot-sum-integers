package com.example.demo.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GlobalExceptionHandler Unit Tests")
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    @DisplayName("Should handle HttpMessageNotReadableException")
    void testHandleHttpMessageNotReadableException() {
        // Given
        HttpMessageNotReadableException exception = new HttpMessageNotReadableException("Test message");

        // When
        Map<String, String> result = exceptionHandler.handleHttpMessageNotReadable(exception);

        // Then
        assertNotNull(result);
        assertEquals("Invalid input", result.get("error"));
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should handle MethodArgumentNotValidException")
    void testHandleMethodArgumentNotValidException() {
        // Given - We'll pass null since we only care about the response format
        MethodArgumentNotValidException exception = null;

        // When
        Map<String, String> result = exceptionHandler.handleValidationExceptions(exception);

        // Then
        assertNotNull(result);
        assertEquals("Invalid input", result.get("error"));
        assertEquals(1, result.size());
    }
}