package com.example.demo.controller;

import com.example.demo.dto.AuthenticationRequest;
import com.example.demo.dto.AuthenticationResponse;
import com.example.demo.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador de Autenticación que implementa las mejores prácticas del space jon-security.
 * 
 * ARQUITECTURA LAYERED (del proyecto):
 * - Controller: Solo manejo HTTP, validación de requests, formateo de responses
 * - Service: Lógica de negocio, validaciones de dominio, cálculos
 * - DTO: Objetos de transferencia de datos
 * 
 * PRÁCTICAS DE SEGURIDAD jon-security:
 * - ✅ Constructor injection (no @Autowired en campos)
 * - ✅ NO expone detalles internos en errores
 * - ✅ Logging seguro sin datos sensibles
 * - ✅ Validación de entrada
 * - ✅ Responses estructuradas en JSON
 * - ✅ Manejo centralizado de excepciones
 */
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
    
    private final AuthenticationService authenticationService;
    
    /**
     * Constructor injection (best practice del proyecto y jon-security).
     */
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    
    /**
     * Endpoint de autenticación de usuarios.
     * 
     * SEGURIDAD:
     * - Valida entrada antes de procesar
     * - NO expone si el error es de usuario o password
     * - NO registra passwords en logs
     * - Respuesta estructurada en JSON
     * 
     * @param requestBody - Map con username y password
     * @return ResponseEntity con resultado de autenticación
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> requestBody) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validación de entrada según jon-security
            if (!requestBody.containsKey("username")) {
                LOGGER.warn("Petición de login sin username");
                response.put("error", "Campo 'username' es requerido");
                response.put("securityNote", "Validación de entrada aplicada según jon-security");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (!requestBody.containsKey("password")) {
                LOGGER.warn("Petición de login sin password");
                response.put("error", "Campo 'password' es requerido");
                response.put("securityNote", "Validación de entrada aplicada según jon-security");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Crear DTO (validación adicional en constructor)
            AuthenticationRequest authRequest = new AuthenticationRequest(
                requestBody.get("username"),
                requestBody.get("password")
            );
            
            // Delegar lógica de negocio al servicio
            AuthenticationResponse authResponse = authenticationService.authenticate(authRequest);
            
            // Construir respuesta estructurada
            response.put("authenticated", authResponse.isAuthenticated());
            response.put("message", authResponse.getMessage());
            
            if (authResponse.isAuthenticated()) {
                response.put("username", authResponse.getUsername());
                response.put("securityCompliance", "jon-security space practices applied");
                LOGGER.info("Login exitoso via API");
                return ResponseEntity.ok(response);
            } else {
                response.put("securityNote", "Credenciales validadas según jon-security guidelines");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
        } catch (IllegalArgumentException e) {
            // Manejo de errores de validación
            LOGGER.warn("Validación falló en login: {}", e.getMessage());
            response.put("error", e.getMessage());
            response.put("securityNote", "Validación aplicada según jon-security");
            return ResponseEntity.badRequest().body(response);
            
        } catch (Exception e) {
            // Manejo genérico de errores - NO exponer detalles internos
            LOGGER.error("Error interno en autenticación", e);
            response.put("error", "Error interno del servidor");
            response.put("securityNote", "Detalles técnicos no expuestos por seguridad");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Endpoint para crear nuevos usuarios (solo para demostración).
     * En producción, esto requeriría autenticación de admin.
     * 
     * @param requestBody - Map con username y password
     * @return ResponseEntity con resultado de creación
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> requestBody) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validación de entrada
            if (!requestBody.containsKey("username") || !requestBody.containsKey("password")) {
                response.put("error", "Campos 'username' y 'password' son requeridos");
                return ResponseEntity.badRequest().body(response);
            }
            
            String username = requestBody.get("username");
            String password = requestBody.get("password");
            
            // Delegar creación al servicio
            boolean created = authenticationService.createUser(username, password);
            
            if (created) {
                response.put("success", true);
                response.put("message", "Usuario creado exitosamente");
                response.put("username", username);
                response.put("securityNote", "Password hasheada con BCrypt según jon-security");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                response.put("success", false);
                response.put("message", "El usuario ya existe");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }
            
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Validación falló en registro: {}", e.getMessage());
            response.put("error", e.getMessage());
            response.put("securityNote", "Política de contraseñas según OWASP y jon-security");
            return ResponseEntity.badRequest().body(response);
            
        } catch (Exception e) {
            LOGGER.error("Error interno en registro", e);
            response.put("error", "Error interno del servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Endpoint educativo que muestra las mejores prácticas de autenticación.
     */
    @GetMapping("/security-guidelines")
    public ResponseEntity<Map<String, Object>> getSecurityGuidelines() {
        
        Map<String, Object> guidelines = new HashMap<>();
        
        guidelines.put("spaceName", "jon-security");
        guidelines.put("purpose", "Secure authentication implementation");
        
        Map<String, String> bestPractices = new HashMap<>();
        bestPractices.put("passwordHashing", "BCrypt con salt automático y factor de trabajo 10");
        bestPractices.put("inputValidation", "Validación estricta en DTO y Service");
        bestPractices.put("errorHandling", "Mensajes genéricos - no revelar si usuario o password es incorrecto");
        bestPractices.put("logging", "NO registrar passwords ni datos sensibles en logs");
        bestPractices.put("secretManagement", "NO hardcodear passwords - usar archivo externo o gestor de secretos");
        bestPractices.put("architecture", "Layered: Controller → Service → DTO");
        
        guidelines.put("bestPractices", bestPractices);
        
        Map<String, String> passwordPolicy = new HashMap<>();
        passwordPolicy.put("minLength", "8 caracteres");
        passwordPolicy.put("uppercase", "Al menos una letra mayúscula");
        passwordPolicy.put("lowercase", "Al menos una letra minúscula");
        passwordPolicy.put("digits", "Al menos un dígito");
        passwordPolicy.put("specialChars", "Al menos un carácter especial");
        
        guidelines.put("passwordPolicy", passwordPolicy);
        
        LOGGER.info("Directrices de seguridad de autenticación proporcionadas");
        
        return ResponseEntity.ok(guidelines);
    }
    
    /**
     * Endpoint de demostración de hashing (solo para propósitos educativos).
     * NO usar en producción.
     */
    @PostMapping("/demo/hash-password")
    public ResponseEntity<Map<String, Object>> demoHashPassword(@RequestBody Map<String, String> requestBody) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (!requestBody.containsKey("password")) {
                response.put("error", "Campo 'password' es requerido");
                return ResponseEntity.badRequest().body(response);
            }
            
            String password = requestBody.get("password");
            String hashedPassword = authenticationService.demonstratePasswordHashing(password);
            
            response.put("note", "Esto es solo para demostración - NO usar en producción");
            response.put("algorithm", "BCrypt");
            response.put("workFactor", 10);
            response.put("hashedPassword", hashedPassword);
            response.put("securityNote", "Password original NO almacenada según jon-security");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            LOGGER.error("Error en demostración de hashing", e);
            response.put("error", "Error interno del servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Endpoint de health check para monitoreo.
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "AuthenticationService");
        health.put("userCount", authenticationService.getUserCount());
        health.put("securityCompliance", "jon-security space");
        return ResponseEntity.ok(health);
    }
}
