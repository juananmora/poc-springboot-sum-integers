package com.example.demo.service;

import com.example.demo.dto.AuthenticationRequest;
import com.example.demo.dto.AuthenticationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests para AuthenticationService siguiendo las mejores prácticas del proyecto.
 * 
 * ESTRUCTURA:
 * - @Nested classes para agrupar tests relacionados
 * - @DisplayName para descripciones legibles
 * - Cobertura >90% como objetivo
 */
@DisplayName("AuthenticationService Tests")
class AuthenticationServiceTest {
    
    private AuthenticationService authenticationService;
    
    @BeforeEach
    void setUp() {
        authenticationService = new AuthenticationService();
        authenticationService.initializeUsersDatabase();
    }
    
    @Nested
    @DisplayName("Authentication Operation Tests")
    class AuthenticationTests {
        
        @Test
        @DisplayName("Should authenticate user with correct credentials")
        void testAuthenticateSuccessful() {
            // Arrange
            AuthenticationRequest request = new AuthenticationRequest("admin", "Password123!");
            
            // Act
            AuthenticationResponse response = authenticationService.authenticate(request);
            
            // Assert
            assertTrue(response.isAuthenticated());
            assertEquals("Autenticación exitosa", response.getMessage());
            assertEquals("admin", response.getUsername());
        }
        
        @Test
        @DisplayName("Should fail authentication with incorrect password")
        void testAuthenticateWrongPassword() {
            // Arrange
            AuthenticationRequest request = new AuthenticationRequest("admin", "WrongPassword!");
            
            // Act
            AuthenticationResponse response = authenticationService.authenticate(request);
            
            // Assert
            assertFalse(response.isAuthenticated());
            assertEquals("Credenciales inválidas", response.getMessage());
            assertNull(response.getUsername());
        }
        
        @Test
        @DisplayName("Should fail authentication with non-existent user")
        void testAuthenticateNonExistentUser() {
            // Arrange
            AuthenticationRequest request = new AuthenticationRequest("nonexistent", "Password123!");
            
            // Act
            AuthenticationResponse response = authenticationService.authenticate(request);
            
            // Assert
            assertFalse(response.isAuthenticated());
            assertEquals("Credenciales inválidas", response.getMessage());
            assertNull(response.getUsername());
        }
        
        @Test
        @DisplayName("Should authenticate all default users with correct passwords")
        void testAuthenticateAllDefaultUsers() {
            // Test admin
            AuthenticationResponse adminResponse = authenticationService.authenticate(
                new AuthenticationRequest("admin", "Password123!")
            );
            assertTrue(adminResponse.isAuthenticated());
            
            // Test user
            AuthenticationResponse userResponse = authenticationService.authenticate(
                new AuthenticationRequest("user", "User456!")
            );
            assertTrue(userResponse.isAuthenticated());
            
            // Test testuser
            AuthenticationResponse testuserResponse = authenticationService.authenticate(
                new AuthenticationRequest("testuser", "Test789!")
            );
            assertTrue(testuserResponse.isAuthenticated());
        }
    }
    
    @Nested
    @DisplayName("User Creation Tests")
    class UserCreationTests {
        
        @Test
        @DisplayName("Should create new user with valid credentials")
        void testCreateUserSuccessful() {
            // Arrange
            String username = "newuser";
            String password = "NewPass123!";
            
            // Act
            boolean created = authenticationService.createUser(username, password);
            
            // Assert
            assertTrue(created);
            
            // Verify user can authenticate
            AuthenticationResponse response = authenticationService.authenticate(
                new AuthenticationRequest(username, password)
            );
            assertTrue(response.isAuthenticated());
        }
        
        @Test
        @DisplayName("Should not create duplicate user")
        void testCreateDuplicateUser() {
            // Arrange - admin ya existe como usuario por defecto
            String username = "admin"; 
            String password = "NewPass123!";
            
            // Act
            boolean created = authenticationService.createUser(username, password);
            
            // Assert
            assertFalse(created, "No debería poder crear un usuario duplicado");
        }
        
        @Test
        @DisplayName("Should throw exception for null username")
        void testCreateUserNullUsername() {
            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authenticationService.createUser(null, "Password123!")
            );
            
            assertEquals("Username no puede ser null o vacío", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should throw exception for empty username")
        void testCreateUserEmptyUsername() {
            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authenticationService.createUser("   ", "Password123!")
            );
            
            assertEquals("Username no puede ser null o vacío", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should throw exception for null password")
        void testCreateUserNullPassword() {
            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authenticationService.createUser("newuser", null)
            );
            
            assertEquals("Password no puede ser null o vacía", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should throw exception for empty password")
        void testCreateUserEmptyPassword() {
            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authenticationService.createUser("newuser", "   ")
            );
            
            assertEquals("Password no puede ser null o vacía", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should throw exception for password not meeting policy")
        void testCreateUserWeakPassword() {
            // Act & Assert - Password demasiado corta
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authenticationService.createUser("newuser", "Pass1!")
            );
            
            assertEquals("Password no cumple con la política de seguridad", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should reject password without uppercase")
        void testPasswordPolicyNoUppercase() {
            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authenticationService.createUser("newuser", "password123!")
            );
            
            assertEquals("Password no cumple con la política de seguridad", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should reject password without lowercase")
        void testPasswordPolicyNoLowercase() {
            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authenticationService.createUser("newuser", "PASSWORD123!")
            );
            
            assertEquals("Password no cumple con la política de seguridad", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should reject password without digits")
        void testPasswordPolicyNoDigits() {
            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authenticationService.createUser("newuser", "Password!!")
            );
            
            assertEquals("Password no cumple con la política de seguridad", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should reject password without special characters")
        void testPasswordPolicyNoSpecialChars() {
            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authenticationService.createUser("newuser", "Password123")
            );
            
            assertEquals("Password no cumple con la política de seguridad", exception.getMessage());
        }
    }
    
    @Nested
    @DisplayName("Security and Utility Tests")
    class SecurityTests {
        
        @Test
        @DisplayName("Should return correct user count")
        void testGetUserCount() {
            // Act
            int count = authenticationService.getUserCount();
            
            // Assert
            assertEquals(3, count); // admin, user, testuser
        }
        
        @Test
        @DisplayName("Should generate different hashes for same password")
        void testPasswordHashingUniqueness() {
            // Act - Generar dos hashes de la misma password
            String hash1 = authenticationService.demonstratePasswordHashing("TestPass123!");
            String hash2 = authenticationService.demonstratePasswordHashing("TestPass123!");
            
            // Assert - Los hashes deben ser diferentes (BCrypt usa salt aleatorio)
            assertNotNull(hash1);
            assertNotNull(hash2);
            assertNotEquals(hash1, hash2);
            assertTrue(hash1.startsWith("$2a$"));
            assertTrue(hash2.startsWith("$2a$"));
        }
        
        @Test
        @DisplayName("Should generate BCrypt format hash")
        void testPasswordHashingFormat() {
            // Act
            String hash = authenticationService.demonstratePasswordHashing("TestPass123!");
            
            // Assert
            assertNotNull(hash);
            assertTrue(hash.startsWith("$2a$")); // BCrypt format
            assertTrue(hash.length() >= 59); // BCrypt hash length
        }
        
        @Test
        @DisplayName("Should update user count after creating user")
        void testUserCountAfterCreation() {
            // Arrange
            int initialCount = authenticationService.getUserCount();
            
            // Act
            authenticationService.createUser("newuser", "NewPass123!");
            int finalCount = authenticationService.getUserCount();
            
            // Assert
            assertEquals(initialCount + 1, finalCount);
        }
    }
    
    @Nested
    @DisplayName("DTO Validation Tests")
    class DTOValidationTests {
        
        @Test
        @DisplayName("AuthenticationRequest should throw exception for null username")
        void testAuthRequestNullUsername() {
            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new AuthenticationRequest(null, "Password123!")
            );
            
            assertEquals("Username no puede ser null o vacío", exception.getMessage());
        }
        
        @Test
        @DisplayName("AuthenticationRequest should throw exception for empty username")
        void testAuthRequestEmptyUsername() {
            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new AuthenticationRequest("   ", "Password123!")
            );
            
            assertEquals("Username no puede ser null o vacío", exception.getMessage());
        }
        
        @Test
        @DisplayName("AuthenticationRequest should throw exception for null password")
        void testAuthRequestNullPassword() {
            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new AuthenticationRequest("username", null)
            );
            
            assertEquals("Password no puede ser null o vacía", exception.getMessage());
        }
        
        @Test
        @DisplayName("AuthenticationRequest should throw exception for empty password")
        void testAuthRequestEmptyPassword() {
            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new AuthenticationRequest("username", "   ")
            );
            
            assertEquals("Password no puede ser null o vacía", exception.getMessage());
        }
        
        @Test
        @DisplayName("AuthenticationRequest should trim username")
        void testAuthRequestTrimUsername() {
            // Act
            AuthenticationRequest request = new AuthenticationRequest("  admin  ", "Password123!");
            
            // Assert
            assertEquals("admin", request.getUsername());
        }
        
        @Test
        @DisplayName("AuthenticationRequest toString should mask password")
        void testAuthRequestToStringMasksPassword() {
            // Act
            AuthenticationRequest request = new AuthenticationRequest("admin", "Password123!");
            String toString = request.toString();
            
            // Assert
            assertTrue(toString.contains("admin"));
            assertFalse(toString.contains("Password123!"));
            assertTrue(toString.contains("[PROTECTED]"));
        }
    }
}
