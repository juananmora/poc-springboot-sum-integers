# 🏗️ Arquitectura del Sistema - Spring Boot Math Service

## Tabla de Contenidos

- [Visión General](#visión-general)
- [Arquitectura en Capas](#arquitectura-en-capas)
- [Componentes del Sistema](#componentes-del-sistema)
- [Flujo de Datos](#flujo-de-datos)
- [Patrones de Diseño](#patrones-de-diseño)
- [Seguridad](#seguridad)
- [Testing Strategy](#testing-strategy)

---

## Visión General

Spring Boot Math Service es una aplicación RESTful que sigue una arquitectura de 3 capas estrictamente separadas. El proyecto implementa operaciones matemáticas y algoritmos de grafos con énfasis en calidad de código, testing exhaustivo y seguridad.

### Principios Arquitectónicos

- **Separación de Responsabilidades**: Cada capa tiene responsabilidades claramente definidas
- **Inyección de Dependencias**: Constructor injection para todas las dependencias
- **Inversión de Control**: Spring Boot gestiona el ciclo de vida de los componentes
- **Testabilidad**: Diseño que facilita unit testing y mocking
- **Inmutabilidad**: DTOs inmutables donde sea posible

---

## Arquitectura en Capas

```
┌─────────────────────────────────────────────────────────────┐
│                     Cliente (HTTP/REST)                      │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                   CONTROLLER LAYER                           │
│  - Manejo de HTTP requests/responses                        │
│  - Validación de parámetros                                 │
│  - Conversión de tipos                                      │
│  - Formateo de respuestas                                   │
│                                                             │
│  Controllers:                                               │
│  • AdditionController                                       │
│  • MathController                                          │
│  • DijkstraController                                      │
│  • AuthenticationController                                │
│  • SecurityController                                      │
│  • GlobalExceptionHandler (manejo centralizado errores)    │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    SERVICE LAYER                             │
│  - Lógica de negocio                                        │
│  - Validaciones de dominio                                  │
│  - Cálculos y algoritmos                                    │
│  - Orquestación de operaciones                             │
│                                                             │
│  Services:                                                  │
│  • MathService (operaciones matemáticas)                   │
│  • DijkstraService (algoritmo de camino más corto)         │
│  • AuthenticationService (gestión de usuarios y JWT)       │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                      DTO LAYER                               │
│  - Data Transfer Objects                                     │
│  - Estructuras de datos inmutables                          │
│  - Validación de datos en constructores                     │
│                                                             │
│  DTOs:                                                      │
│  • Graph, GraphEdge (representación de grafos)             │
│  • DijkstraResult (resultados de algoritmo)                │
│  • AuthenticationRequest/Response (auth data)              │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    CONFIG LAYER                              │
│  - Configuración de Spring Security                         │
│  - Bean definitions                                         │
│  - Configuración de CORS                                    │
└─────────────────────────────────────────────────────────────┘
```

---

## Componentes del Sistema

### 1. Controller Layer

**Responsabilidad**: Capa de presentación que maneja la interacción HTTP.

#### 1.1 AdditionController
```java
@RestController
public class AdditionController {
    private final MathService mathService;
    
    // Constructor injection - NO @Autowired
    public AdditionController(MathService mathService) {
        this.mathService = mathService;
    }
}
```

**Responsabilidades**:
- Recibir parámetros de query/body
- Validar formato de HTTP
- Delegar al service layer
- Formatear respuesta HTTP
- **NO contiene lógica de negocio**

#### 1.2 MathController
**Endpoints**: `/api/math/*`
**Operaciones**: 
- Suma de múltiples números
- Cálculo de media
- Quicksort (enteros y decimales)
- Cálculo de moda
- Factorial
- Verificación de propiedades de números

#### 1.3 DijkstraController
**Endpoints**: `/api/dijkstra/*`
**Operaciones**:
- Obtener grafo por defecto
- Calcular camino más corto (GET/POST)
- Camino específico A→F

#### 1.4 AuthenticationController
**Endpoints**: `/api/auth/*`
**Operaciones**:
- Registro de usuarios (`/register`)
- Login con JWT (`/login`)
- Validación de credenciales

#### 1.5 GlobalExceptionHandler
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {
        // Manejo centralizado de excepciones
    }
}
```

**Responsabilidades**:
- Captura excepciones globalmente
- Convierte excepciones Java a respuestas HTTP apropiadas
- Formato consistente de errores

---

### 2. Service Layer

**Responsabilidad**: Capa de negocio que contiene la lógica de la aplicación.

#### 2.1 MathService
```java
@Service
public class MathService {
    /**
     * Suma dos números no negativos.
     * 
     * @param a primer número (>= 0)
     * @param b segundo número (>= 0)
     * @return suma de a y b
     * @throws IllegalArgumentException si algún número es negativo
     */
    public int add(int a, int b) {
        if (a < 0 || b < 0) {
            throw new IllegalArgumentException("Los números no pueden ser negativos");
        }
        return a + b;
    }
    
    /**
     * Calcula el factorial de un número.
     * 
     * @param number número (0 <= n <= 20)
     * @return factorial de number
     * @throws IllegalArgumentException si number < 0 o number > 20
     */
    public long factorial(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Factorial no definido para números negativos");
        }
        if (number > 20) {
            throw new IllegalArgumentException("Factorial > 20 causa overflow");
        }
        return calculateFactorial(number);
    }
}
```

**Características**:
- Todas las operaciones matemáticas
- Validación de entrada de negocio
- Algoritmos de ordenamiento (Quicksort)
- Cálculos estadísticos (media, moda)

#### 2.2 DijkstraService
```java
@Service
public class DijkstraService {
    /**
     * Encuentra el camino más corto entre dos nodos usando Dijkstra.
     * 
     * @param graph el grafo a analizar
     * @param source nodo origen
     * @param destination nodo destino
     * @return DijkstraResult con el camino y distancia
     */
    public DijkstraResult findShortestPath(Graph graph, String source, String destination) {
        // Implementación del algoritmo de Dijkstra
    }
}
```

**Características**:
- Implementación completa del algoritmo de Dijkstra
- Soporte para grafos bidireccionales
- Manejo de grafos con pesos
- Detección de caminos no encontrados

#### 2.3 AuthenticationService
```java
@Service
public class AuthenticationService {
    private final Map<String, User> userDatabase = new ConcurrentHashMap<>();
    
    /**
     * Autentica un usuario y genera un token JWT.
     * 
     * @param username nombre de usuario
     * @param password contraseña
     * @return token JWT si la autenticación es exitosa
     * @throws IllegalArgumentException si las credenciales son inválidas
     */
    public String authenticate(String username, String password) {
        // Validación y generación de JWT
    }
}
```

**Características**:
- Registro de usuarios con validación
- Autenticación con BCrypt
- Generación de tokens JWT
- Base de datos en memoria (ConcurrentHashMap)
- Usuarios por defecto para testing

---

### 3. DTO Layer

**Responsabilidad**: Objetos para transferencia de datos entre capas.

#### 3.1 Graph
```java
public class Graph {
    private final Map<String, List<GraphEdge>> adjacencyList;
    
    public Graph(Map<String, List<GraphEdge>> adjacencyList) {
        this.adjacencyList = new HashMap<>(adjacencyList);
    }
    
    // Solo getters - inmutable
}
```

#### 3.2 DijkstraResult
```java
public class DijkstraResult {
    private final String source;
    private final String destination;
    private final List<String> path;
    private final int distance;
    private final boolean pathFound;
    
    // Constructor con validación
    // Solo getters - inmutable
}
```

#### 3.3 AuthenticationRequest/Response
```java
public class AuthenticationRequest {
    private String username;
    private String password;
    // Getters y setters
}

public class AuthenticationResponse {
    private final String token;
    private final String username;
    private final String email;
    // Solo getters - inmutable
}
```

---

### 4. Config Layer

#### 4.1 SecurityConfig
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }
}
```

**Configuraciones**:
- Spring Security
- CORS configuration
- Endpoints públicos vs protegidos
- Password encoding (BCrypt)

---

## Flujo de Datos

### Flujo de Request - Operación Matemática

```
1. Cliente HTTP
   │
   │ POST /api/math/sum
   │ Body: {"a": 5, "b": 3}
   │
   ▼
2. MathController
   │
   │ - Valida content-type
   │ - Deserializa JSON
   │ - Extrae parámetros
   │
   ▼
3. MathService
   │
   │ - Valida a >= 0, b >= 0
   │ - Calcula: result = a + b
   │ - Retorna resultado
   │
   ▼
4. MathController
   │
   │ - Crea response DTO
   │ - Serializa a JSON
   │
   ▼
5. Cliente HTTP
   Response: {"result": 8, "operation": "sum", "operands": [5, 3]}
```

### Flujo de Request - Algoritmo de Dijkstra

```
1. Cliente HTTP
   │
   │ GET /api/dijkstra/shortest-path?source=A&destination=F
   │
   ▼
2. DijkstraController
   │
   │ - Valida parámetros source y destination
   │ - Obtiene grafo por defecto
   │
   ▼
3. DijkstraService
   │
   │ - Inicializa estructuras de datos (PriorityQueue, distancias)
   │ - Ejecuta algoritmo de Dijkstra
   │ - Reconstruye camino desde destino a origen
   │ - Crea DijkstraResult
   │
   ▼
4. DijkstraController
   │
   │ - Serializa DijkstraResult a JSON
   │
   ▼
5. Cliente HTTP
   Response: {
     "source": "A",
     "destination": "F",
     "path": ["A", "C", "B", "D", "E", "F"],
     "distance": 12,
     "pathFound": true
   }
```

### Flujo de Manejo de Errores

```
1. Request inválido
   │
   ▼
2. Controller detecta error
   │
   │ - Service lanza IllegalArgumentException
   │
   ▼
3. GlobalExceptionHandler
   │
   │ @ExceptionHandler captura excepción
   │ - Crea respuesta de error estructurada
   │ - Establece código HTTP 400
   │
   ▼
4. Cliente HTTP
   Response: {"error": "Los números no pueden ser negativos"}
   Status: 400 Bad Request
```

---

## Patrones de Diseño

### 1. Dependency Injection (DI)
```java
// Constructor injection - Patrón recomendado
public class MathController {
    private final MathService mathService;
    
    public MathController(MathService mathService) {
        this.mathService = mathService;
    }
}
```

**Beneficios**:
- Fácil testing con mocks
- Dependencias explícitas
- Inmutabilidad de dependencias

### 2. Service Layer Pattern
- Separación clara entre presentación (Controllers) y lógica (Services)
- Services contienen toda la lógica de negocio
- Controllers son thin wrappers sobre Services

### 3. DTO Pattern
- Objetos dedicados para transferencia de datos
- Desacoplamiento entre capa de presentación y dominio
- Validación encapsulada

### 4. Exception Handling Pattern
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    // Manejo centralizado de todas las excepciones
}
```

### 5. Builder Pattern (Implícito en DTOs)
- Construcción de objetos complejos
- Inmutabilidad de resultados

---

## Seguridad

### 1. Spring Security
- Configuración en `SecurityConfig.java`
- Endpoints públicos: `/api/auth/**`
- Endpoints protegidos: Resto de la API

### 2. Password Hashing
```java
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
String hashedPassword = encoder.encode(password);
```

### 3. JWT Token Authentication
- Tokens generados en login
- Validación en cada request protegido
- Expiración configurable

### 4. Input Validation
- Validación en Service Layer
- IllegalArgumentException para entradas inválidas
- Respuestas HTTP 400 para errores de validación

### 5. CORS Configuration
- Configurado para permitir requests desde orígenes específicos
- Headers permitidos definidos explícitamente

---

## Testing Strategy

### Pirámide de Testing

```
        ┌─────────────┐
       /   E2E Tests   \      8 tests
      /─────────────────\     (Full HTTP, TestRestTemplate)
     /  Integration Tests \   8 tests
    /─────────────────────\   (MockMvc, Spring Context)
   /      Unit Tests       \  311 tests
  /─────────────────────────\ (Mockito, JUnit 5)
 └─────────────────────────┘
```

### 1. Unit Tests (311 tests)
**Archivos**: `*Test.java` (no Integration en nombre)

```java
@ExtendWith(MockitoExtension.class)
class MathServiceTest {
    @Test
    @DisplayName("Should add two positive numbers correctly")
    void testAddPositiveNumbers() {
        // Arrange
        MathService service = new MathService();
        
        // Act
        int result = service.add(5, 3);
        
        // Assert
        assertEquals(8, result);
    }
}
```

**Cobertura**:
- MathService: 100%
- DijkstraService: 95%+
- AuthenticationService: 84%

### 2. Integration Tests (8 tests)
**Archivos**: `*IntegrationTest.java`

```java
@SpringBootTest
@AutoConfigureMockMvc
class MathControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void shouldReturnCorrectSum() throws Exception {
        mockMvc.perform(post("/api/math/sum")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"a\":5,\"b\":3}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value(8));
    }
}
```

### 3. End-to-End Tests (8 tests)
- TestRestTemplate
- Servidor Tomcat embebido
- HTTP requests reales

### Coverage Metrics
- **Instrucciones**: 77% (2,251/2,889)
- **Ramas**: 94% (166/176)
- **Líneas**: 78% (481/636)
- **Métodos**: 89% (104/116)
- **Total Tests**: 327

---

## Tecnologías y Dependencias

### Core Framework
- **Spring Boot**: 3.3.5
- **Java**: 17
- **Maven**: Build tool

### Dependencies
```xml
<dependencies>
    <!-- Web Framework -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- Security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    
    <!-- Testing -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
    </dependency>
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-core</artifactId>
    </dependency>
</dependencies>
```

### Build Plugins
- **spring-boot-maven-plugin**: Empaquetado
- **jacoco-maven-plugin**: Cobertura de código (v0.8.11)

---

## Escalabilidad y Rendimiento

### Consideraciones Actuales
1. **Stateless API**: No mantiene sesión entre requests
2. **JWT Tokens**: Autenticación sin estado
3. **In-Memory Storage**: Base de datos de usuarios en memoria (ConcurrentHashMap)

### Limitaciones Conocidas
1. **Usuarios en memoria**: Se pierden al reiniciar
2. **Sin persistencia**: No hay base de datos real
3. **Sin caching**: Cada request recalcula

### Oportunidades de Mejora
1. **Agregar base de datos**: PostgreSQL/MySQL para persistencia
2. **Implementar caching**: Redis para resultados frecuentes
3. **Añadir métricas**: Actuator para monitoring
4. **Agregar rate limiting**: Protección contra abuso

---

## Diagrama de Componentes Completo

```
┌──────────────────────────────────────────────────────────────────────┐
│                          External Client                              │
│                         (Browser, curl, API)                          │
└──────────────────────────────────────────────────────────────────────┘
                                    │
                                    │ HTTP/REST
                                    │
┌──────────────────────────────────────────────────────────────────────┐
│                        Spring Boot Application                        │
│                                                                       │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │               SECURITY LAYER (SecurityConfig)                │   │
│  │  - JWT Validation                                           │   │
│  │  - CORS Configuration                                       │   │
│  │  - Endpoint Protection                                      │   │
│  └─────────────────────────────────────────────────────────────┘   │
│                                    │                                 │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │                    CONTROLLER LAYER                          │   │
│  │  ┌───────────────┐  ┌──────────────┐  ┌─────────────────┐  │   │
│  │  │   Addition    │  │     Math     │  │    Dijkstra     │  │   │
│  │  │  Controller   │  │  Controller  │  │   Controller    │  │   │
│  │  └───────────────┘  └──────────────┘  └─────────────────┘  │   │
│  │  ┌───────────────┐  ┌──────────────┐  ┌─────────────────┐  │   │
│  │  │Authentication │  │   Security   │  │     Global      │  │   │
│  │  │  Controller   │  │  Controller  │  │Exception Handler│  │   │
│  │  └───────────────┘  └──────────────┘  └─────────────────┘  │   │
│  └─────────────────────────────────────────────────────────────┘   │
│                                    │                                 │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │                     SERVICE LAYER                            │   │
│  │  ┌───────────────┐  ┌──────────────┐  ┌─────────────────┐  │   │
│  │  │     Math      │  │   Dijkstra   │  │Authentication   │  │   │
│  │  │   Service     │  │   Service    │  │    Service      │  │   │
│  │  │               │  │              │  │                 │  │   │
│  │  │ - Operations  │  │ - Algorithm  │  │ - User DB      │  │   │
│  │  │ - Validation  │  │ - Graphs     │  │ - JWT Gen      │  │   │
│  │  └───────────────┘  └──────────────┘  └─────────────────┘  │   │
│  └─────────────────────────────────────────────────────────────┘   │
│                                    │                                 │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │                       DTO LAYER                              │   │
│  │  ┌───────────┐  ┌─────────────┐  ┌────────────────────┐    │   │
│  │  │   Graph   │  │  Dijkstra   │  │   Authentication   │    │   │
│  │  │ GraphEdge │  │   Result    │  │   Request/Response │    │   │
│  │  └───────────┘  └─────────────┘  └────────────────────┘    │   │
│  └─────────────────────────────────────────────────────────────┘   │
│                                                                       │
└──────────────────────────────────────────────────────────────────────┘
```

---

## Conclusión

Esta arquitectura proporciona:
- ✅ **Separación clara de responsabilidades**
- ✅ **Alta testabilidad** (327 tests, 77% coverage)
- ✅ **Mantenibilidad** gracias a la estructura en capas
- ✅ **Escalabilidad** con diseño stateless
- ✅ **Seguridad** con Spring Security y JWT
- ✅ **Documentación completa** con Javadoc

Para más información sobre contribución y estándares de código, ver [CONTRIBUTING.md](CONTRIBUTING.md).

---

*Última actualización: Diciembre 2025*
