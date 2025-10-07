package com.example.demo.controller;

import com.example.demo.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador de Seguridad que implementa las mejores prácticas del space jon-security.
 * 
 * Este controlador demuestra cómo manejar datos sensibles de forma segura,
 * siguiendo las directrices establecidas en el space jon-security de GitHub Copilot.
 */
@RestController
@RequestMapping("/api/security")
public class SecurityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityController.class);
    
    @Autowired
    private MathService mathService;

    /**
     * Endpoint que demuestra el manejo seguro de credenciales.
     * 
     * IMPORTANTE: Este endpoint NO imprime passwords en terminal.
     * En su lugar, implementa las mejores prácticas de seguridad.
     * 
     * @param requestBody - Objeto con datos sensibles a procesar
     * @return ResponseEntity con resultado seguro
     */
    @PostMapping("/process-credential")
    public ResponseEntity<Map<String, Object>> processCredential(@RequestBody Map<String, String> requestBody) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 1. VALIDACIÓN DE ENTRADA según jon-security space
            if (!requestBody.containsKey("sensitiveData")) {
                LOGGER.warn("Petición sin datos sensibles requeridos");
                response.put("error", "Campo 'sensitiveData' es requerido");
                response.put("securityNote", "Validación de entrada aplicada según prácticas jon-security");
                return ResponseEntity.badRequest().body(response);
            }
            
            String sensitiveData = requestBody.get("sensitiveData");
            
            // 2. PROCESAMIENTO SEGURO usando el servicio
            String secureHash = mathService.processCredentialSecurely(sensitiveData);
            
            // 3. RESPUESTA SEGURA - NO incluir datos sensibles originales
            response.put("status", "success");
            response.put("message", "Datos procesados de forma segura");
            response.put("secureHashId", secureHash.substring(0, 12) + "...[TRUNCADO]");
            response.put("securityCompliance", "jon-security space practices applied");
            response.put("note", "Password NOT printed to terminal for security reasons");
            
            // 4. LOG SEGURO - Solo información técnica
            LOGGER.info("Procesamiento seguro de credencial completado via API");
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            // 5. MANEJO SEGURO DE ERRORES - No exponer detalles internos
            LOGGER.warn("Validación falló en procesamiento de credencial");
            response.put("error", "Datos no cumplen políticas de seguridad");
            response.put("securityNote", "Validación aplicada según jon-security guidelines");
            return ResponseEntity.badRequest().body(response);
            
        } catch (Exception e) {
            // 6. MANEJO GENÉRICO DE ERRORES - Proteger información interna
            LOGGER.error("Error interno en procesamiento seguro de credencial");
            response.put("error", "Error interno del servidor");
            response.put("securityNote", "Detalles técnicos no expuestos por seguridad");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Endpoint educativo que muestra las mejores prácticas de seguridad.
     */
    @GetMapping("/security-guidelines")
    public ResponseEntity<Map<String, Object>> getSecurityGuidelines() {
        
        Map<String, Object> guidelines = new HashMap<>();
        
        guidelines.put("spaceName", "jon-security");
        guidelines.put("purpose", "Secure coding expert guidelines");
        
        Map<String, String> bestPractices = new HashMap<>();
        bestPractices.put("secretManagement", "Nunca hardcodear contraseñas, usar gestores de secretos");
        bestPractices.put("inputValidation", "Siempre validar entradas, usar librerías seguras");
        bestPractices.put("errorHandling", "Generalizar mensajes de error, no exponer información interna");
        bestPractices.put("authentication", "Aplicar principios de least privilege");
        bestPractices.put("encryption", "Usar TLS/HTTPS, algoritmos criptográficos actuales");
        bestPractices.put("logging", "No registrar datos sensibles en logs");
        
        guidelines.put("bestPractices", bestPractices);
        
        Map<String, String> prohibitions = new HashMap<>();
        prohibitions.put("hardcodedPasswords", "No hardcodear credenciales en código");
        prohibitions.put("terminalPasswords", "No imprimir passwords en terminal");
        prohibitions.put("unsafeAlgorithms", "Evitar MD5, SHA1 - usar algoritmos seguros");
        prohibitions.put("sensitiveDataInLogs", "No registrar datos sensibles en logs");
        
        guidelines.put("prohibitions", prohibitions);
        
        // Demostrar configuración segura
        mathService.demonstrateSecureConfigHandling();
        
        LOGGER.info("Directrices de seguridad proporcionadas");
        
        return ResponseEntity.ok(guidelines);
    }
    
    /**
     * Endpoint que muestra por qué NO se debe imprimir passwords en terminal.
     */
    @GetMapping("/why-no-terminal-passwords")
    public ResponseEntity<Map<String, Object>> whyNoTerminalPasswords() {
        
        Map<String, Object> explanation = new HashMap<>();
        
        explanation.put("title", "¿Por qué NO imprimir passwords en terminal?");
        
        Map<String, String> risks = new HashMap<>();
        risks.put("logPersistence", "Pueden quedar almacenados en logs del sistema");
        risks.put("terminalHistory", "Pueden guardarse en historial de terminal");
        risks.put("base64NotEncryption", "Base64 es encoding, NO cifrado - password sigue visible");
        risks.put("accessByOthers", "Otros usuarios pueden ver la información");
        risks.put("auditTrail", "Crear rastro de auditoría inseguro");
        risks.put("compliance", "Viola normativas de protección de datos");
        
        explanation.put("securityRisks", risks);
        
        Map<String, String> alternatives = new HashMap<>();
        alternatives.put("secureHashing", "Generar hash seguro con salt");
        alternatives.put("environmentVars", "Usar variables de entorno");
        alternatives.put("secretManagers", "Usar gestores de secretos (Vault, Azure Key Vault)");
        alternatives.put("configFiles", "Archivos de configuración externos seguros");
        alternatives.put("encryption", "Cifrado real (AES, RSA) no solo encoding");
        
        explanation.put("secureAlternatives", alternatives);
        
        explanation.put("jonSecurityCompliance", "Este enfoque cumple con las prácticas del space jon-security");
        
        LOGGER.info("Explicación de seguridad sobre passwords en terminal proporcionada");
        
        return ResponseEntity.ok(explanation);
    }
}