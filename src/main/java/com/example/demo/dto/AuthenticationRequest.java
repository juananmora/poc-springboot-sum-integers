package com.example.demo.dto;

/**
 * DTO para peticiones de autenticación.
 * 
 * Siguiendo las mejores prácticas del space jon-security:
 * - No expone información sensible en toString()
 * - Valida entradas en el constructor
 * - Inmutable para mayor seguridad
 */
public class AuthenticationRequest {
    
    private final String username;
    private final String password;
    
    /**
     * Constructor con validación de entrada según jon-security.
     * 
     * @param username - Nombre de usuario (no puede ser null o vacío)
     * @param password - Contraseña (no puede ser null o vacía)
     * @throws IllegalArgumentException si los parámetros son inválidos
     */
    public AuthenticationRequest(String username, String password) {
        // Validación de entrada según jon-security space
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username no puede ser null o vacío");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password no puede ser null o vacía");
        }
        
        this.username = username.trim();
        this.password = password;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    /**
     * NO expone la contraseña por seguridad (jon-security best practice).
     */
    @Override
    public String toString() {
        return "AuthenticationRequest{username='" + username + "', password='[PROTECTED]'}";
    }
}
