package com.example.demo.dto;

/**
 * DTO para respuestas de autenticación.
 * 
 * Siguiendo las mejores prácticas del space jon-security:
 * - No expone detalles internos de errores
 * - Inmutable para mayor seguridad
 */
public class AuthenticationResponse {
    
    private final boolean authenticated;
    private final String message;
    private final String username;
    
    public AuthenticationResponse(boolean authenticated, String message, String username) {
        this.authenticated = authenticated;
        this.message = message;
        this.username = username;
    }
    
    public boolean isAuthenticated() {
        return authenticated;
    }
    
    public String getMessage() {
        return message;
    }
    
    public String getUsername() {
        return username;
    }
}
