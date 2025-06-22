package com.example.demo.controller;

import com.example.demo.service.MathService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestController
public class AdditionController {

    private final MathService mathService;

    public AdditionController(MathService mathService) {
        this.mathService = mathService;
    }

    @GetMapping("/add")
    public ResponseEntity<Integer> add(@RequestParam int num1, @RequestParam int num2) {
        try {
            int result = mathService.add(num1, num2);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/multiply")
    public ResponseEntity<Integer> multiply(@RequestParam int num1, @RequestParam int num2) {
        int result = mathService.multiply(num1, num2);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/divide")
    public ResponseEntity<Double> divide(@RequestParam int num1, @RequestParam int num2) {
        try {
            double result = mathService.divide(num1, num2);
            return ResponseEntity.ok(result);
        } catch (ArithmeticException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/subtract")
    public ResponseEntity<Integer> subtract(@RequestParam int num1, @RequestParam int num2) {
        int result = mathService.subtract(num1, num2);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/sqrt")
    public ResponseEntity<Double> sqrt(@RequestParam double number) {
        try {
            double result = mathService.sqrt(number);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> handleNumberFormatException(NumberFormatException e) {
        return ResponseEntity.badRequest().body("Formato de número inválido");
    }
}
