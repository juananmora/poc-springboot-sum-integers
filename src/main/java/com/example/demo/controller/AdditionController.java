package com.example.demo.controller;

import com.example.demo.service.MathService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * REST Controller that provides basic mathematical operations endpoints.
 * This controller follows the layered architecture pattern where:
 * - Controller handles HTTP requests/responses
 * - Service layer contains business logic
 * 
 * <p>All endpoints return appropriate HTTP status codes:
 * <ul>
 *   <li>200 OK - Successful operation</li>
 *   <li>400 Bad Request - Invalid input or business rule violation</li>
 * </ul>
 * 
 * @author Spring Boot Math Service Team
 * @version 1.0
 * @since 1.0
 */
@RestController
public class AdditionController {

    private final MathService mathService;

    /**
     * Constructs an AdditionController with the required MathService dependency.
     * Uses constructor injection as per Spring best practices.
     *
     * @param mathService the service that performs mathematical operations
     */
    public AdditionController(MathService mathService) {
        this.mathService = mathService;
    }

    /**
     * Adds two integers and returns the result.
     * Validates that both numbers are non-negative before performing the operation.
     *
     * @param num1 the first number to add (must be non-negative)
     * @param num2 the second number to add (must be non-negative)
     * @return ResponseEntity containing the sum if successful, or 400 Bad Request if validation fails
     * 
     * <p><b>Example usage:</b> {@code GET /add?num1=5&num2=3} returns {@code 8}
     */
    @GetMapping("/add")
    public ResponseEntity<Integer> add(@RequestParam int num1, @RequestParam int num2) {
        try {
            int result = mathService.add(num1, num2);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Multiplies two integers and returns the result.
     *
     * @param num1 the first number to multiply
     * @param num2 the second number to multiply
     * @return ResponseEntity containing the product
     * 
     * <p><b>Example usage:</b> {@code GET /multiply?num1=4&num2=6} returns {@code 24}
     */
    @GetMapping("/multiply")
    public ResponseEntity<Integer> multiply(@RequestParam int num1, @RequestParam int num2) {
        int result = mathService.multiply(num1, num2);
        return ResponseEntity.ok(result);
    }

    /**
     * Divides the first integer by the second and returns the result as a double.
     * Validates that the divisor is not zero before performing the operation.
     *
     * @param num1 the dividend (number to be divided)
     * @param num2 the divisor (number to divide by, must not be zero)
     * @return ResponseEntity containing the quotient if successful, or 400 Bad Request if divisor is zero
     * 
     * <p><b>Example usage:</b> {@code GET /divide?num1=15&num2=3} returns {@code 5.0}
     */
    @GetMapping("/divide")
    public ResponseEntity<Double> divide(@RequestParam int num1, @RequestParam int num2) {
        try {
            double result = mathService.divide(num1, num2);
            return ResponseEntity.ok(result);
        } catch (ArithmeticException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Subtracts the second integer from the first and returns the result.
     *
     * @param num1 the minuend (number to subtract from)
     * @param num2 the subtrahend (number to be subtracted)
     * @return ResponseEntity containing the difference
     * 
     * <p><b>Example usage:</b> {@code GET /subtract?num1=10&num2=4} returns {@code 6}
     */
    @GetMapping("/subtract")
    public ResponseEntity<Integer> subtract(@RequestParam int num1, @RequestParam int num2) {
        int result = mathService.subtract(num1, num2);
        return ResponseEntity.ok(result);
    }

    /**
     * Calculates the square root of a number.
     * Validates that the number is non-negative before performing the operation.
     *
     * @param number the number to find the square root of (must be non-negative)
     * @return ResponseEntity containing the square root if successful, or 400 Bad Request if number is negative
     * 
     * <p><b>Example usage:</b> {@code GET /sqrt?number=16} returns {@code 4.0}
     */
    @GetMapping("/sqrt")
    public ResponseEntity<Double> sqrt(@RequestParam double number) {
        try {
            double result = mathService.sqrt(number);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Exception handler for NumberFormatException.
     * Returns a user-friendly error message when invalid number format is provided.
     *
     * @param e the NumberFormatException that was thrown
     * @return ResponseEntity with 400 Bad Request status and error message
     */
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> handleNumberFormatException(NumberFormatException e) {
        return ResponseEntity.badRequest().body("Formato de número inválido");
    }
}
