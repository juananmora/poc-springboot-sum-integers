package com.example.demo.controller;

import com.example.demo.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Integration tests para AuthenticationController siguiendo las mejores prácticas del proyecto.
 * 
 * CARACTERÍSTICAS:
 * - Usa @SpringBootTest para cargar contexto completo
 * - Usa MockMvc para simular requests HTTP
 * - Tests de flujo completo: Request → Controller → Service → Response
 * - Cobertura de casos exitosos y de error
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("AuthenticationController Integration Tests")
class AuthenticationControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private AuthenticationService authenticationService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @BeforeEach
    void setUp() {
        // Asegurar que el servicio tiene los usuarios por defecto
        authenticationService.initializeUsersDatabase();
    }
    
    @Nested
    @DisplayName("POST /api/auth/login - Login Endpoint Tests")
    class LoginEndpointTests {
        
        @Test
        @DisplayName("Should return 200 OK for valid credentials")
        void testLoginSuccessful() throws Exception {
            // Arrange
            Map<String, String> loginRequest = new HashMap<>();
            loginRequest.put("username", "admin");
            loginRequest.put("password", "Password123!");
            
            // Act & Assert
            mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.authenticated", is(true)))
                    .andExpect(jsonPath("$.message", is("Autenticación exitosa")))
                    .andExpect(jsonPath("$.username", is("admin")))
                    .andExpect(jsonPath("$.securityCompliance", is("jon-security space practices applied")));
        }
        
        @Test
        @DisplayName("Should return 401 UNAUTHORIZED for invalid password")
        void testLoginInvalidPassword() throws Exception {
            // Arrange
            Map<String, String> loginRequest = new HashMap<>();
            loginRequest.put("username", "admin");
            loginRequest.put("password", "WrongPassword!");
            
            // Act & Assert
            mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginRequest)))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.authenticated", is(false)))
                    .andExpect(jsonPath("$.message", is("Credenciales inválidas")))
                    .andExpect(jsonPath("$.securityNote", containsString("jon-security")));
        }
        
        @Test
        @DisplayName("Should return 401 UNAUTHORIZED for non-existent user")
        void testLoginNonExistentUser() throws Exception {
            // Arrange
            Map<String, String> loginRequest = new HashMap<>();
            loginRequest.put("username", "nonexistent");
            loginRequest.put("password", "Password123!");
            
            // Act & Assert
            mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginRequest)))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.authenticated", is(false)))
                    .andExpect(jsonPath("$.message", is("Credenciales inválidas")));
        }
        
        @Test
        @DisplayName("Should return 400 BAD REQUEST for missing username")
        void testLoginMissingUsername() throws Exception {
            // Arrange
            Map<String, String> loginRequest = new HashMap<>();
            loginRequest.put("password", "Password123!");
            
            // Act & Assert
            mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error", is("Campo 'username' es requerido")))
                    .andExpect(jsonPath("$.securityNote", containsString("jon-security")));
        }
        
        @Test
        @DisplayName("Should return 400 BAD REQUEST for missing password")
        void testLoginMissingPassword() throws Exception {
            // Arrange
            Map<String, String> loginRequest = new HashMap<>();
            loginRequest.put("username", "admin");
            
            // Act & Assert
            mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error", is("Campo 'password' es requerido")))
                    .andExpect(jsonPath("$.securityNote", containsString("jon-security")));
        }
        
        @Test
        @DisplayName("Should return 400 BAD REQUEST for empty username")
        void testLoginEmptyUsername() throws Exception {
            // Arrange
            Map<String, String> loginRequest = new HashMap<>();
            loginRequest.put("username", "   ");
            loginRequest.put("password", "Password123!");
            
            // Act & Assert
            mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error", containsString("Username no puede ser null o vacío")));
        }
        
        @Test
        @DisplayName("Should authenticate all default users")
        void testLoginAllDefaultUsers() throws Exception {
            // Test admin
            Map<String, String> adminRequest = new HashMap<>();
            adminRequest.put("username", "admin");
            adminRequest.put("password", "Password123!");
            
            mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(adminRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.authenticated", is(true)));
            
            // Test user
            Map<String, String> userRequest = new HashMap<>();
            userRequest.put("username", "user");
            userRequest.put("password", "User456!");
            
            mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.authenticated", is(true)));
            
            // Test testuser
            Map<String, String> testuserRequest = new HashMap<>();
            testuserRequest.put("username", "testuser");
            testuserRequest.put("password", "Test789!");
            
            mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(testuserRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.authenticated", is(true)));
        }
    }
    
    @Nested
    @DisplayName("POST /api/auth/register - Register Endpoint Tests")
    class RegisterEndpointTests {
        
        @Test
        @DisplayName("Should return 201 CREATED for new user with valid credentials")
        void testRegisterSuccessful() throws Exception {
            // Arrange
            Map<String, String> registerRequest = new HashMap<>();
            registerRequest.put("username", "newuser");
            registerRequest.put("password", "NewPass123!");
            
            // Act & Assert
            mockMvc.perform(post("/api/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(registerRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.success", is(true)))
                    .andExpect(jsonPath("$.message", is("Usuario creado exitosamente")))
                    .andExpect(jsonPath("$.username", is("newuser")))
                    .andExpect(jsonPath("$.securityNote", containsString("BCrypt")));
        }
        
        @Test
        @DisplayName("Should return 409 CONFLICT for duplicate user")
        void testRegisterDuplicateUser() throws Exception {
            // Arrange
            Map<String, String> registerRequest = new HashMap<>();
            registerRequest.put("username", "admin"); // Usuario ya existente
            registerRequest.put("password", "NewPass123!");
            
            // Act & Assert
            mockMvc.perform(post("/api/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(registerRequest)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.success", is(false)))
                    .andExpect(jsonPath("$.message", is("El usuario ya existe")));
        }
        
        @Test
        @DisplayName("Should return 400 BAD REQUEST for weak password")
        void testRegisterWeakPassword() throws Exception {
            // Arrange
            Map<String, String> registerRequest = new HashMap<>();
            registerRequest.put("username", "newuser");
            registerRequest.put("password", "weak"); // Password débil
            
            // Act & Assert
            mockMvc.perform(post("/api/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(registerRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error", is("Password no cumple con la política de seguridad")))
                    .andExpect(jsonPath("$.securityNote", containsString("OWASP")));
        }
        
        @Test
        @DisplayName("Should return 400 BAD REQUEST for missing fields")
        void testRegisterMissingFields() throws Exception {
            // Arrange
            Map<String, String> registerRequest = new HashMap<>();
            registerRequest.put("username", "newuser");
            // Falta password
            
            // Act & Assert
            mockMvc.perform(post("/api/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(registerRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error", containsString("requeridos")));
        }
        
        @Test
        @DisplayName("Should allow login after successful registration")
        void testLoginAfterRegistration() throws Exception {
            // Arrange - Registrar nuevo usuario
            Map<String, String> registerRequest = new HashMap<>();
            registerRequest.put("username", "newuser2");
            registerRequest.put("password", "NewPass456!");
            
            mockMvc.perform(post("/api/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(registerRequest)))
                    .andExpect(status().isCreated());
            
            // Act & Assert - Intentar login con el nuevo usuario
            Map<String, String> loginRequest = new HashMap<>();
            loginRequest.put("username", "newuser2");
            loginRequest.put("password", "NewPass456!");
            
            mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.authenticated", is(true)))
                    .andExpect(jsonPath("$.username", is("newuser2")));
        }
    }
    
    @Nested
    @DisplayName("GET /api/auth/security-guidelines - Guidelines Endpoint Tests")
    class GuidelinesEndpointTests {
        
        @Test
        @DisplayName("Should return 200 OK with security guidelines")
        void testGetSecurityGuidelines() throws Exception {
            // Act & Assert
            mockMvc.perform(get("/api/auth/security-guidelines"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.spaceName", is("jon-security")))
                    .andExpect(jsonPath("$.purpose", containsString("authentication")))
                    .andExpect(jsonPath("$.bestPractices").exists())
                    .andExpect(jsonPath("$.bestPractices.passwordHashing", containsString("BCrypt")))
                    .andExpect(jsonPath("$.bestPractices.logging", containsString("NO registrar passwords")))
                    .andExpect(jsonPath("$.passwordPolicy").exists())
                    .andExpect(jsonPath("$.passwordPolicy.minLength", is("8 caracteres")));
        }
    }
    
    @Nested
    @DisplayName("POST /api/auth/demo/hash-password - Demo Hashing Endpoint Tests")
    class DemoHashingEndpointTests {
        
        @Test
        @DisplayName("Should return 200 OK with hashed password")
        void testDemoHashPassword() throws Exception {
            // Arrange
            Map<String, String> demoRequest = new HashMap<>();
            demoRequest.put("password", "TestPass123!");
            
            // Act & Assert
            mockMvc.perform(post("/api/auth/demo/hash-password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(demoRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.algorithm", is("BCrypt")))
                    .andExpect(jsonPath("$.workFactor", is(10)))
                    .andExpect(jsonPath("$.hashedPassword").exists())
                    .andExpect(jsonPath("$.hashedPassword", startsWith("$2a$")))
                    .andExpect(jsonPath("$.securityNote", containsString("NO almacenada")));
        }
        
        @Test
        @DisplayName("Should return 400 BAD REQUEST for missing password")
        void testDemoHashPasswordMissing() throws Exception {
            // Arrange
            Map<String, String> demoRequest = new HashMap<>();
            
            // Act & Assert
            mockMvc.perform(post("/api/auth/demo/hash-password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(demoRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error", is("Campo 'password' es requerido")));
        }
        
        @Test
        @DisplayName("Should generate different hashes for same password")
        void testDemoHashPasswordUniqueness() throws Exception {
            // Arrange
            Map<String, String> demoRequest = new HashMap<>();
            demoRequest.put("password", "TestPass123!");
            
            // Act - Primera llamada
            String response1 = mockMvc.perform(post("/api/auth/demo/hash-password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(demoRequest)))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            
            // Act - Segunda llamada
            String response2 = mockMvc.perform(post("/api/auth/demo/hash-password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(demoRequest)))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            
            // Assert - Los hashes deben ser diferentes (BCrypt usa salt aleatorio)
            Map<?, ?> map1 = objectMapper.readValue(response1, Map.class);
            Map<?, ?> map2 = objectMapper.readValue(response2, Map.class);
            
            String hash1 = (String) map1.get("hashedPassword");
            String hash2 = (String) map2.get("hashedPassword");
            
            assertNotEquals(hash1, hash2);
        }
    }
    
    @Nested
    @DisplayName("GET /api/auth/health - Health Endpoint Tests")
    class HealthEndpointTests {
        
        @Test
        @DisplayName("Should return 200 OK with health status")
        void testHealthEndpoint() throws Exception {
            // Act & Assert
            mockMvc.perform(get("/api/auth/health"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status", is("UP")))
                    .andExpect(jsonPath("$.service", is("AuthenticationService")))
                    .andExpect(jsonPath("$.userCount").isNumber())
                    .andExpect(jsonPath("$.securityCompliance", is("jon-security space")));
        }
    }
}
