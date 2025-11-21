package com.example.demo.controller;

import com.example.demo.service.MathService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Controlador REST para operaciones matemáticas básicas.
 * <p>
 * Este controlador proporciona endpoints GET para realizar operaciones
 * matemáticas simples como suma, multiplicación, división, resta y raíz cuadrada.
 * Todos los endpoints validan las entradas y retornan respuestas HTTP apropiadas.
 * </p>
 * 
 * <p><strong>Arquitectura:</strong></p>
 * <ul>
 *   <li>Delega toda la lógica de negocio a {@link MathService}</li>
 *   <li>Maneja validación de parámetros HTTP</li>
 *   <li>Convierte excepciones de negocio en respuestas HTTP apropiadas</li>
 * </ul>
 * 
 * @author Spring Boot Math Service Team
 * @version 1.0
 * @see MathService
 */
@RestController
public class AdditionController {

    private final MathService mathService;

    /**
     * Constructor para inyección de dependencias.
     * 
     * @param mathService servicio que contiene la lógica matemática
     */
    public AdditionController(MathService mathService) {
        this.mathService = mathService;
    }

    /**
     * Suma dos números enteros.
     * <p>
     * Endpoint: GET /add?num1={num1}&num2={num2}
     * </p>
     * 
     * @param num1 primer número a sumar (debe ser no negativo)
     * @param num2 segundo número a sumar (debe ser no negativo)
     * @return ResponseEntity con el resultado de la suma (HTTP 200) o
     *         HTTP 400 si los números son negativos
     * 
     * @apiNote Los números negativos no están permitidos y retornarán HTTP 400
     * 
     * <p><strong>Ejemplos:</strong></p>
     * <pre>
     * GET /add?num1=5&num2=3  → 200 OK, body: 8
     * GET /add?num1=-5&num2=3 → 400 Bad Request
     * </pre>
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
     * Multiplica dos números enteros.
     * <p>
     * Endpoint: GET /multiply?num1={num1}&num2={num2}
     * </p>
     * 
     * @param num1 primer número a multiplicar
     * @param num2 segundo número a multiplicar
     * @return ResponseEntity con el resultado de la multiplicación (HTTP 200)
     * 
     * <p><strong>Ejemplos:</strong></p>
     * <pre>
     * GET /multiply?num1=4&num2=6 → 200 OK, body: 24
     * GET /multiply?num1=0&num2=5 → 200 OK, body: 0
     * </pre>
     */
    @GetMapping("/multiply")
    public ResponseEntity<Integer> multiply(@RequestParam int num1, @RequestParam int num2) {
        int result = mathService.multiply(num1, num2);
        return ResponseEntity.ok(result);
    }

    /**
     * Divide dos números enteros retornando un resultado decimal.
     * <p>
     * Endpoint: GET /divide?num1={num1}&num2={num2}
     * </p>
     * 
     * @param num1 dividendo
     * @param num2 divisor (no puede ser cero)
     * @return ResponseEntity con el resultado de la división (HTTP 200) o
     *         HTTP 400 si el divisor es cero
     * 
     * @apiNote La división por cero retorna HTTP 400 Bad Request
     * 
     * <p><strong>Ejemplos:</strong></p>
     * <pre>
     * GET /divide?num1=15&num2=3 → 200 OK, body: 5.0
     * GET /divide?num1=10&num2=0 → 400 Bad Request
     * </pre>
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
     * Resta dos números enteros.
     * <p>
     * Endpoint: GET /subtract?num1={num1}&num2={num2}
     * </p>
     * 
     * @param num1 minuendo
     * @param num2 sustraendo
     * @return ResponseEntity con el resultado de la resta (HTTP 200)
     * 
     * <p><strong>Ejemplos:</strong></p>
     * <pre>
     * GET /subtract?num1=10&num2=4 → 200 OK, body: 6
     * GET /subtract?num1=5&num2=8  → 200 OK, body: -3
     * </pre>
     */
    @GetMapping("/subtract")
    public ResponseEntity<Integer> subtract(@RequestParam int num1, @RequestParam int num2) {
        int result = mathService.subtract(num1, num2);
        return ResponseEntity.ok(result);
    }

    /**
     * Calcula la raíz cuadrada de un número.
     * <p>
     * Endpoint: GET /sqrt?number={number}
     * </p>
     * 
     * @param number número del cual calcular la raíz cuadrada (debe ser no negativo)
     * @return ResponseEntity con el resultado de la raíz cuadrada (HTTP 200) o
     *         HTTP 400 si el número es negativo
     * 
     * @apiNote No se puede calcular la raíz cuadrada de números negativos
     * 
     * <p><strong>Ejemplos:</strong></p>
     * <pre>
     * GET /sqrt?number=9  → 200 OK, body: 3.0
     * GET /sqrt?number=16 → 200 OK, body: 4.0
     * GET /sqrt?number=-4 → 400 Bad Request
     * </pre>
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
     * Maneja excepciones de formato de número inválido.
     * 
     * @param e excepción de formato de número
     * @return ResponseEntity con mensaje de error (HTTP 400)
     */
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> handleNumberFormatException(NumberFormatException e) {
        return ResponseEntity.badRequest().body("Formato de número inválido");
    }
}
