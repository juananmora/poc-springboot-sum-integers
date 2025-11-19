package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler that provides centralized error handling across all controllers.
 * This class uses Spring's {@code @ControllerAdvice} to intercept exceptions thrown by
 * controller methods and return consistent, user-friendly error responses.
 * 
 * <p>All error responses follow a consistent JSON format with an "error" key containing
 * a descriptive message. This approach:
 * <ul>
 *   <li>Prevents exposure of internal implementation details</li>
 *   <li>Provides consistent error responses across the API</li>
 *   <li>Simplifies client-side error handling</li>
 *   <li>Follows security best practices by not leaking sensitive information</li>
 * </ul>
 * 
 * @author Spring Boot Math Service Team
 * @version 1.0
 * @since 1.0
 * @see ControllerAdvice
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Handles exceptions when HTTP request body cannot be read or parsed.
     * This typically occurs when:
     * <ul>
     *   <li>JSON payload is malformed</li>
     *   <li>Request Content-Type header is incorrect</li>
     *   <li>Required fields are missing in the request body</li>
     * </ul>
     *
     * @param ex the HttpMessageNotReadableException that was thrown
     * @return Map containing error message with key "error"
     * 
     * @apiNote Returns HTTP 400 Bad Request with JSON: {"error": "Invalid input"}
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Invalid input");
        return error;
    }

    /**
     * Handles validation exceptions when method arguments fail validation.
     * This occurs when:
     * <ul>
     *   <li>Request parameters don't meet validation constraints</li>
     *   <li>Bean validation annotations (e.g., @Valid, @NotNull) fail</li>
     *   <li>Custom validation rules are violated</li>
     * </ul>
     *
     * @param ex the MethodArgumentNotValidException containing validation details
     * @return Map containing error message with key "error"
     * 
     * @apiNote Returns HTTP 400 Bad Request with JSON: {"error": "Invalid input"}
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Invalid input");
        return error;
    }
}
