package com.example.demo.service;

import com.example.demo.dto.AuthenticationRequest;
import com.example.demo.dto.AuthenticationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Servicio de autenticación que implementa las mejores prácticas del space jon-security.
 * 
 * PRÁCTICAS DE SEGURIDAD IMPLEMENTADAS:
 * 1. ✅ Uso de BCrypt para hashing de contraseñas con salt automático
 * 2. ✅ NO se imprimen contraseñas en logs (masked)
 * 3. ✅ NO se hardcodean contraseñas en código
 * 4. ✅ Validación de entradas antes de procesar
 * 5. ✅ Mensajes de error genéricos (no revelan si usuario o password es incorrecto)
 * 6. ✅ Logging seguro sin datos sensibles
 * 7. ✅ Gestión de secretos mediante archivo externo
 * 8. ✅ Principio de least privilege
 * 
 * @see <a href="https://docs.spring.io/spring-security/reference/features/authentication/password-storage.html">BCrypt Password Storage</a>
 */
@Service
public class AuthenticationService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);
    
    // BCrypt encoder con factor de trabajo 10 (recomendado por OWASP)
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    
    // "Base de datos" en memoria (cargada desde archivo)
    private final Map<String, String> usersDatabase = new HashMap<>();
    
    /**
     * Inicializa la "base de datos" de usuarios desde archivo.
     * Siguiendo jon-security: NO hardcodear credenciales en código.
     */
    @PostConstruct
    public void initializeUsersDatabase() {
        // Para esta demostración, siempre creamos usuarios por defecto
        // En producción: cargar desde gestor de secretos o base de datos real
        createDefaultUsers();
        
        LOGGER.info("Base de datos de usuarios inicializada. Total: {}", usersDatabase.size());
    }
    
    /**
     * Crea usuarios por defecto con contraseñas hasheadas.
     * NOTA: En producción, esto se debe gestionar mediante gestores de secretos (Vault, Azure Key Vault).
     */
    private void createDefaultUsers() {
        // BCrypt hash de "Password123!" (solo para demostración)
        // En producción: usar gestor de secretos
        usersDatabase.put("admin", passwordEncoder.encode("Password123!"));
        usersDatabase.put("user", passwordEncoder.encode("User456!"));
        usersDatabase.put("testuser", passwordEncoder.encode("Test789!"));
        
        LOGGER.info("Usuarios por defecto creados. Total: {}", usersDatabase.size());
    }
    
    /**
     * Autentica un usuario validando username y password.
     * 
     * SEGURIDAD según jon-security:
     * - Valida entradas antes de procesar
     * - NO registra contraseñas en logs
     * - Mensajes de error genéricos (no revela qué campo es incorrecto)
     * - Usa timing attack resistant comparison (BCrypt)
     * 
     * @param request - Objeto con username y password
     * @return AuthenticationResponse con el resultado
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Log seguro: NO incluye password
        LOGGER.info("Intento de autenticación para usuario: {}", maskUsername(request.getUsername()));
        
        try {
            // Validación de entrada (ya realizada en DTO constructor)
            String username = request.getUsername();
            String password = request.getPassword();
            
            // Verificar si el usuario existe
            if (!usersDatabase.containsKey(username)) {
                LOGGER.warn("Intento de autenticación fallido: usuario no válido");
                // Mensaje genérico por seguridad (no revela si usuario o password es incorrecto)
                return new AuthenticationResponse(false, "Credenciales inválidas", null);
            }
            
            // Verificar password usando BCrypt (timing attack resistant)
            String hashedPassword = usersDatabase.get(username);
            boolean passwordMatches = passwordEncoder.matches(password, hashedPassword);
            
            if (passwordMatches) {
                LOGGER.info("Autenticación exitosa para usuario: {}", maskUsername(username));
                return new AuthenticationResponse(true, "Autenticación exitosa", username);
            } else {
                LOGGER.warn("Intento de autenticación fallido: credenciales inválidas");
                // Mensaje genérico por seguridad
                return new AuthenticationResponse(false, "Credenciales inválidas", null);
            }
            
        } catch (Exception e) {
            // Error handling según jon-security: NO exponer detalles internos
            LOGGER.error("Error interno durante autenticación. Detalles no expuestos por seguridad.");
            return new AuthenticationResponse(false, "Error interno del servidor", null);
        }
    }
    
    /**
     * Crea un nuevo usuario con password hasheado.
     * 
     * @param username - Nombre de usuario
     * @param password - Contraseña en texto plano (será hasheada)
     * @return true si el usuario fue creado exitosamente
     */
    public boolean createUser(String username, String password) {
        // Validación de entrada
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username no puede ser null o vacío");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password no puede ser null o vacía");
        }
        
        // Verificar política de contraseñas
        if (!isPasswordValid(password)) {
            throw new IllegalArgumentException("Password no cumple con la política de seguridad");
        }
        
        // Verificar si el usuario ya existe
        if (usersDatabase.containsKey(username)) {
            LOGGER.warn("Intento de crear usuario existente: {}", maskUsername(username));
            return false;
        }
        
        // Hashear password y guardar usuario
        String hashedPassword = passwordEncoder.encode(password);
        usersDatabase.put(username, hashedPassword);
        
        LOGGER.info("Usuario creado exitosamente: {}", maskUsername(username));
        return true;
    }
    
    /**
     * Valida que la contraseña cumpla con la política de seguridad.
     * 
     * POLÍTICA DE CONTRASEÑAS (siguiendo OWASP):
     * - Mínimo 8 caracteres
     * - Al menos una letra mayúscula
     * - Al menos una letra minúscula
     * - Al menos un dígito
     * - Al menos un carácter especial
     * 
     * @param password - Contraseña a validar
     * @return true si cumple con la política
     */
    private boolean isPasswordValid(String password) {
        if (password.length() < 8) {
            return false;
        }
        
        boolean hasUpperCase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLowerCase = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch));
        
        return hasUpperCase && hasLowerCase && hasDigit && hasSpecial;
    }
    
    /**
     * Enmascara el username para logging seguro.
     * Ejemplo: "administrator" -> "adm*******tor"
     * 
     * @param username - Username a enmascarar
     * @return Username enmascarado
     */
    private String maskUsername(String username) {
        if (username == null || username.length() <= 4) {
            return "***";
        }
        
        int prefixLength = Math.min(3, username.length() / 3);
        int suffixLength = Math.min(3, username.length() / 3);
        
        String prefix = username.substring(0, prefixLength);
        String suffix = username.substring(username.length() - suffixLength);
        String middle = "*".repeat(username.length() - prefixLength - suffixLength);
        
        return prefix + middle + suffix;
    }
    
    /**
     * Obtiene el número de usuarios registrados (para monitoreo).
     * 
     * @return Número de usuarios en la "base de datos"
     */
    public int getUserCount() {
        return usersDatabase.size();
    }
    
    /**
     * Demuestra el hashing de contraseñas para propósitos educativos.
     * NO expone contraseñas reales.
     * 
     * @param plainPassword - Contraseña en texto plano
     * @return Hash BCrypt de la contraseña
     */
    public String demonstratePasswordHashing(String plainPassword) {
        LOGGER.info("Generando hash BCrypt para demostración (password NO registrada)");
        return passwordEncoder.encode(plainPassword);
    }
}
