package com.example.demo.controller;

import com.example.demo.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para operaciones matemáticas avanzadas.
 * <p>
 * Este controlador proporciona endpoints POST para operaciones matemáticas
 * más complejas que requieren cuerpos de petición JSON, incluyendo:
 * <ul>
 *   <li>Operaciones aritméticas básicas (suma, multiplicación)</li>
 *   <li>Operaciones estadísticas (media, moda)</li>
 *   <li>Algoritmos de ordenamiento (quicksort)</li>
 *   <li>Operaciones sobre listas de números</li>
 *   <li>Cálculo de factorial</li>
 * </ul>
 * </p>
 * 
 * <p><strong>Arquitectura:</strong></p>
 * <ul>
 *   <li>Todos los endpoints aceptan peticiones POST con cuerpo JSON</li>
 *   <li>Delega toda la lógica de negocio a {@link MathService}</li>
 *   <li>Retorna respuestas estructuradas con resultado, operación y operandos</li>
 * </ul>
 * 
 * <p><strong>Base URL:</strong> /api/math</p>
 * 
 * @author Spring Boot Math Service Team
 * @version 1.0
 * @see MathService
 */
@RestController
@RequestMapping("/api/math")
public class MathController {

    @Autowired
    private MathService mathService;

    /**
     * Suma dos números enteros.
     * <p>
     * Endpoint: POST /api/math/sum
     * </p>
     * 
     * @param request Mapa con claves "a" y "b" (enteros)
     * @return ResponseEntity con resultado estructurado o error
     * 
     * <p><strong>Request body ejemplo:</strong></p>
     * <pre>
     * {
     *   "a": 5,
     *   "b": 3
     * }
     * </pre>
     * 
     * <p><strong>Response exitosa (200 OK):</strong></p>
     * <pre>
     * {
     *   "result": 8,
     *   "operation": "sum",
     *   "operands": [5, 3]
     * }
     * </pre>
     */
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

    /**
     * Suma una lista de números enteros.
     * <p>
     * Endpoint: POST /api/math/sum-list
     * </p>
     * 
     * @param request Mapa con clave "numbers" (lista de enteros)
     * @return ResponseEntity con resultado estructurado o error
     * 
     * <p><strong>Request body ejemplo:</strong></p>
     * <pre>
     * {
     *   "numbers": [1, 2, 3, 4, 5]
     * }
     * </pre>
     * 
     * <p><strong>Response exitosa (200 OK):</strong></p>
     * <pre>
     * {
     *   "result": 15,
     *   "operation": "sum-list",
     *   "operands": [1, 2, 3, 4, 5]
     * }
     * </pre>
     */
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

    /**
     * Verifica propiedades de un número (si es par y si es positivo).
     * <p>
     * Endpoint: GET /api/math/check/{number}
     * </p>
     * 
     * @param number número a verificar
     * @return ResponseEntity con las propiedades del número
     * 
     * <p><strong>Ejemplo:</strong></p>
     * <pre>
     * GET /api/math/check/4 → 200 OK
     * {
     *   "number": 4,
     *   "isEven": true,
     *   "isPositive": true
     * }
     * </pre>
     */
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

    /**
     * Endpoint POST para calcular el factorial de un número entero.
     * 
     * Request body formato JSON:
     * {
     *   "number": 5
     * }
     * 
     * Respuesta exitosa (200 OK):
     * {
     *   "result": 120,
     *   "operation": "factorial",
     *   "operand": 5
     * }
     * 
     * Respuesta error (400 Bad Request):
     * {
     *   "error": "Mensaje de error descriptivo"
     * }
     * 
     * @param request Map con la clave "number" (Integer)
     * @return ResponseEntity con el resultado o mensaje de error
     */
    @PostMapping("/factorial")
    public ResponseEntity<Map<String, Object>> factorial(@RequestBody Map<String, Object> request) {
        try {
            // Validación de entrada: número debe estar presente
            if (!request.containsKey("number")) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "El campo 'number' es obligatorio"
                ));
            }
            
            // Extraer número del request
            Object numberObj = request.get("number");
            int number;
            
            // Manejar diferentes tipos numéricos
            if (numberObj instanceof Integer) {
                number = (Integer) numberObj;
            } else if (numberObj instanceof Number) {
                number = ((Number) numberObj).intValue();
            } else {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "El campo 'number' debe ser un número entero"
                ));
            }
            
            // Calcular factorial
            long result = mathService.factorial(number);
            
            // Respuesta exitosa
            return ResponseEntity.ok(Map.of(
                "result", result,
                "operation", "factorial",
                "operand", number
            ));
            
        } catch (IllegalArgumentException e) {
            // Errores de validación (número negativo o overflow)
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        } catch (Exception e) {
            // Errores generales
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Entrada inválida: " + e.getMessage()
            ));
        }
    }
}