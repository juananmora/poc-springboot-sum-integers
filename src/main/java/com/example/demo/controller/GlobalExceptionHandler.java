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
 * Manejador global de excepciones para la aplicación.
 * <p>
 * Esta clase centraliza el manejo de excepciones para todos los controladores,
 * proporcionando respuestas HTTP consistentes y estructuradas cuando ocurren errores.
 * </p>
 * 
 * <p><strong>Beneficios:</strong></p>
 * <ul>
 *   <li>Consistencia en el formato de respuestas de error</li>
 *   <li>Separación de lógica de manejo de errores de los controladores</li>
 *   <li>Facilita el debugging con mensajes claros</li>
 * </ul>
 * 
 * <p><strong>Formato de respuesta de error:</strong></p>
 * <pre>
 * {
 *   "error": "Descripción del error"
 * }
 * </pre>
 * 
 * @author Spring Boot Math Service Team
 * @version 1.0
 * @see ControllerAdvice
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Maneja excepciones cuando el cuerpo del request no puede ser leído o parseado.
     * <p>
     * Ocurre típicamente cuando:
     * <ul>
     *   <li>El JSON está malformado</li>
     *   <li>Los tipos de datos no coinciden</li>
     *   <li>Falta el Content-Type header</li>
     * </ul>
     * </p>
     * 
     * @param ex la excepción capturada
     * @return mapa con mensaje de error estructurado
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
     * Maneja excepciones de validación de argumentos del método.
     * <p>
     * Ocurre cuando las validaciones de Bean Validation (@Valid, @NotNull, etc.)
     * fallan en los parámetros de los métodos del controlador.
     * </p>
     * 
     * @param ex la excepción capturada con detalles de validación
     * @return mapa con mensaje de error estructurado
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
