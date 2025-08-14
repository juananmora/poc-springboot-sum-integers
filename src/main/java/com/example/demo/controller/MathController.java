package com.example.demo.controller;

import com.example.demo.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/math")
public class MathController {

    @Autowired
    private MathService mathService;

    @PostMapping("/sum")
    public ResponseEntity<Map<String, Object>> sum(@RequestBody Map<String, Object> request) {
        try {
            int a = (Integer) request.get("a");
            int b = (Integer) request.get("b");
            int result = mathService.add(a, b);
            
            return ResponseEntity.ok(Map.of(
                "result", result,
                "operation", "sum",
                "operands", List.of(a, b)
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid input"));
        }
    }

    @PostMapping("/sum-list")
    public ResponseEntity<Map<String, Object>> sumList(@RequestBody Map<String, List<Integer>> request) {
        try {
            List<Integer> numbers = request.get("numbers");
            int result = mathService.sumList(numbers);
            
            return ResponseEntity.ok(Map.of(
                "result", result,
                "operation", "sum-list",
                "operands", numbers
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid input"));
        }
    }

    @GetMapping("/check/{number}")
    public ResponseEntity<Map<String, Object>> checkNumber(@PathVariable int number) {
        return ResponseEntity.ok(Map.of(
            "number", number,
            "isEven", mathService.isEven(number),
            "isPositive", mathService.isPositive(number)
        ));
    }

    @PostMapping("/multiply")
    public ResponseEntity<Map<String, Object>> multiply(@RequestBody Map<String, Object> request) {
        try {
            int a = (Integer) request.get("a");
            int b = (Integer) request.get("b");
            int result = mathService.multiply(a, b);
            
            return ResponseEntity.ok(Map.of(
                "result", result,
                "operation", "multiply",
                "operands", List.of(a, b)
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid input"));
        }
    }

    @PostMapping("/mean")
    public ResponseEntity<Map<String, Object>> calculateMean(@RequestBody Map<String, List<Integer>> request) {
        try {
            List<Integer> numbers = request.get("numbers");
            double result = mathService.calculateMean(numbers);
            
            return ResponseEntity.ok(Map.of(
                "result", result,
                "operation", "mean",
                "operands", numbers != null ? numbers : List.of()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid input"));
        }
    }

    @PostMapping("/quicksort")
    public ResponseEntity<Map<String, Object>> quicksort(@RequestBody Map<String, List<Integer>> request) {
        try {
            List<Integer> numbers = request.get("numbers");
            List<Integer> result = mathService.quicksort(numbers);
            
            return ResponseEntity.ok(Map.of(
                "result", result,
                "operation", "quicksort",
                "operands", numbers != null ? numbers : List.of()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid input"));
        }
    }

    @PostMapping("/quicksort-rational")
    public ResponseEntity<Map<String, Object>> quicksortRational(@RequestBody Map<String, List<Double>> request) {
        try {
            List<Double> numbers = request.get("numbers");
            List<Double> result = mathService.quicksortRational(numbers);
            
            return ResponseEntity.ok(Map.of(
                "result", result,
                "operation", "quicksort-rational",
                "operands", numbers != null ? numbers : List.of()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid input"));
        }
    }

    @PostMapping("/mode")
    public ResponseEntity<Map<String, Object>> calculateMode(@RequestBody Map<String, List<Integer>> request) {
        try {
            List<Integer> numbers = request.get("numbers");
            List<Integer> result = mathService.calculateMode(numbers);
            
            return ResponseEntity.ok(Map.of(
                "result", result,
                "operation", "mode",
                "operands", numbers != null ? numbers : List.of()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid input"));
        }
    }
}