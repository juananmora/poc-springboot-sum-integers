# 📚 Resumen de Documentación del Proyecto

## Spring Boot Math Service - Documentación Completa

**Repositorio:** [juananmora/poc-springboot-sum-integers](https://github.com/juananmora/poc-springboot-sum-integers)  
**Versión:** 0.0.1-SNAPSHOT  
**Framework:** Spring Boot 3.3.5  
**Java:** 17  
**Licencia:** Apache License 2.0

---

## 📑 Tabla de Contenidos

1. [Descripción General](#-descripción-general)
2. [Arquitectura del Proyecto](#-arquitectura-del-proyecto)
3. [Estructura de Directorios](#-estructura-de-directorios)
4. [Funcionalidades Principales](#-funcionalidades-principales)
5. [APIs y Endpoints](#-apis-y-endpoints)
6. [Estándares de Testing](#-estándares-de-testing)
7. [CI/CD y Automatización](#-cicd-y-automatización)
8. [Guía de Contribución](#-guía-de-contribución)
9. [Configuración y Desarrollo](#-configuración-y-desarrollo)
10. [Documentación Adicional](#-documentación-adicional)

---

## 🎯 Descripción General

Spring Boot Math Service es una aplicación REST API que proporciona:

- **Operaciones matemáticas básicas** (suma, resta, multiplicación, división, raíz cuadrada)
- **Operaciones avanzadas** (factorial, media, moda, quicksort)
- **Algoritmos de grafos** (Dijkstra para caminos más cortos)
- **Sistema de autenticación** (registro, login, JWT)

### Características Destacadas

✅ **226+ pruebas unitarias e integración** con JUnit 5 y Mockito  
✅ **95%+ cobertura de código** con Jacoco  
✅ **Arquitectura en capas estricta** (Controller → Service → DTO)  
✅ **CI/CD automatizado** con GitHub Actions  
✅ **Seguridad implementada** con Spring Security y BCrypt  
✅ **Documentación completa** con Javadoc

---

## 🏗️ Arquitectura del Proyecto

### Patrón de Arquitectura en Capas

El proyecto sigue una **separación estricta de responsabilidades** en tres capas:

```
┌─────────────────────────────────────────┐
│        CONTROLLER LAYER                 │
│  (HTTP handling, validación, formato)   │
│                                         │
│  - AdditionController                   │
│  - MathController                       │
│  - DijkstraController                   │
│  - AuthenticationController             │
│  - GlobalExceptionHandler               │
└─────────────────┬───────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────┐
│         SERVICE LAYER                   │
│  (Lógica de negocio, validaciones)      │
│                                         │
│  - MathService                          │
│  - DijkstraService                      │
│  - AuthenticationService                │
└─────────────────┬───────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────┐
│          DTO LAYER                      │
│  (Data Transfer Objects)                │
│                                         │
│  - Graph, DijkstraResult                │
│  - AuthenticationRequest/Response       │
└─────────────────────────────────────────┘
```

### Principios Arquitectónicos

1. **Controller:** Solo manejo HTTP (NO lógica de negocio)
2. **Service:** Toda la lógica de negocio y validaciones
3. **DTO:** Objetos inmutables para transferencia de datos
4. **Exception Handling:** Centralizado con `@ControllerAdvice`

---

## 📁 Estructura de Directorios

```
poc-springboot-sum-integers/
├── .github/
│   ├── workflows/
│   │   └── maven.yml                    # CI/CD pipeline
│   ├── badges/
│   │   └── jacoco.svg                   # Badge de cobertura
│   ├── instructions/
│   │   ├── quality-sonar.instructions.md
│   │   └── prevent-prompt-injection.md
│   ├── agents/                          # Agentes especializados
│   └── copilot-instructions.md          # Instrucciones para AI
│
├── src/
│   ├── main/java/com/example/demo/
│   │   ├── DemoApplication.java         # Clase principal
│   │   ├── controller/                  # Capa de controladores
│   │   │   ├── AdditionController.java
│   │   │   ├── MathController.java
│   │   │   ├── DijkstraController.java
│   │   │   ├── AuthenticationController.java
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── service/                     # Capa de servicios
│   │   │   ├── MathService.java
│   │   │   ├── DijkstraService.java
│   │   │   └── AuthenticationService.java
│   │   ├── dto/                         # Data Transfer Objects
│   │   │   ├── Graph.java
│   │   │   ├── GraphEdge.java
│   │   │   ├── DijkstraResult.java
│   │   │   ├── AuthenticationRequest.java
│   │   │   └── AuthenticationResponse.java
│   │   └── config/
│   │       └── SecurityConfig.java      # Configuración de seguridad
│   │
│   └── test/java/com/example/demo/      # 226+ pruebas
│       ├── controller/                   # Tests de controladores
│       ├── service/                      # Tests de servicios
│       └── DemoApplicationTest.java
│
├── target/
│   ├── site/jacoco/                     # Reportes de cobertura
│   └── surefire-reports/               # Reportes de pruebas
│
├── pom.xml                              # Configuración Maven
├── README.md                            # Guía principal
├── API.md                               # Documentación de API
├── CONTRIBUTING.md                      # Guía de contribución
├── AUTHENTICATION_IMPLEMENTATION.md     # Sistema de autenticación
├── grafo.jpg                            # Diagrama del grafo
├── testresults*.md                      # Reportes de pruebas
└── LICENSE                              # Apache License 2.0
```

---

## 🚀 Funcionalidades Principales

### 1. Operaciones Matemáticas Básicas

#### Endpoints GET
- **Suma:** `/add?num1=X&num2=Y`
- **Resta:** `/subtract?num1=X&num2=Y`
- **Multiplicación:** `/multiply?num1=X&num2=Y`
- **División:** `/divide?num1=X&num2=Y`
- **Raíz Cuadrada:** `/sqrt?number=X`

**Características:**
- Validación de números negativos
- Manejo de división por cero
- Respuestas en formato JSON

### 2. Operaciones Matemáticas Avanzadas

#### Endpoints POST (`/api/math`)
- **Suma de dos números:** `POST /api/math/sum`
- **Suma de lista:** `POST /api/math/sum-list`
- **Factorial:** `POST /api/math/factorial` (0-20)
- **Media aritmética:** `POST /api/math/mean`
- **Moda:** `POST /api/math/mode`
- **Quicksort (enteros):** `POST /api/math/quicksort`
- **Quicksort (decimales):** `POST /api/math/quicksort-rational`
- **Verificar propiedades:** `GET /api/math/check/{number}`

**Características:**
- Manejo de listas de números
- Validaciones de dominio (ej: factorial <= 20)
- Respuestas estructuradas con operandos

### 3. Algoritmo de Dijkstra (Grafos)

#### Endpoints (`/api/dijkstra`)
- **Camino más corto (GET):** `/api/dijkstra/shortest-path?source=A&destination=F`
- **Camino más corto (POST):** Con grafo personalizado
- **Camino A→F específico:** `/api/dijkstra/path-a-to-f`
- **Obtener grafo por defecto:** `/api/dijkstra/default-graph`

**Características:**
- Grafos bidireccionales ponderados
- Camino óptimo A→F = 12 (A→C→B→D→E→F)
- Soporte para grafos personalizados
- Respuesta con path completo y distancia

### 4. Sistema de Autenticación

#### Endpoints (`/api/auth`)
- **Registro:** `POST /api/auth/register`
- **Login:** `POST /api/auth/login`

**Características de Seguridad:**
- Contraseñas hasheadas con BCrypt
- Tokens JWT para autenticación
- Validación de formato de usuario/email
- Sin credenciales hardcodeadas
- Mensajes de error genéricos (prevención de information leakage)

**Usuarios por Defecto:**
- `admin / admin123` (ADMIN)
- `user / user123` (USER)
- `testuser / test123` (USER)

---

## 📡 APIs y Endpoints

Para documentación completa de endpoints, consultar:
- **[API.md](API.md)** - Documentación detallada de todos los endpoints
  - Parámetros de entrada
  - Respuestas exitosas y de error
  - Ejemplos con curl
  - Códigos de estado HTTP

### Ejemplo de Uso

```bash
# Operación matemática básica
curl "http://localhost:8080/add?num1=5&num2=3"
# Respuesta: 8

# Algoritmo de Dijkstra
curl "http://localhost:8080/api/dijkstra/shortest-path?source=A&destination=F"
# Respuesta: {"source":"A","destination":"F","path":["A","C","B","D","E","F"],"distance":12}

# Autenticación
curl -X POST "http://localhost:8080/api/auth/login" \
     -H "Content-Type: application/json" \
     -d '{"username":"admin","password":"admin123"}'
```

---

## ✅ Estándares de Testing

### Cobertura de Pruebas

| Métrica | Valor | Estado |
|---------|-------|--------|
| **Total de Pruebas** | 226+ | ✅ |
| **Cobertura de Instrucciones** | 95.1% | ✅ |
| **Cobertura de Ramas** | 100% | ✅ |
| **Cobertura de Líneas** | 96% | ✅ |
| **Cobertura de Métodos** | 91.7% | ✅ |

### Tipos de Pruebas

#### 1. **Pruebas Unitarias (Service Layer)**
- **Archivo ejemplo:** `MathServiceTest.java` (126 pruebas)
- **Tecnologías:** JUnit 5, Mockito
- **Patrón:** `@ExtendWith(MockitoExtension.class)`
- **Objetivo:** Lógica de negocio en aislamiento

```java
@DisplayName("MathService Tests")
class MathServiceTest {
    @Nested
    @DisplayName("Addition Operation Tests")
    class AdditionTests {
        @Test
        @DisplayName("Should add two positive numbers correctly")
        void shouldAddPositiveNumbers() {
            // Arrange, Act, Assert
        }
    }
}
```

#### 2. **Pruebas de Integración (Controller Layer)**
- **Archivo ejemplo:** `AdditionControllerIntegrationTest.java`
- **Tecnologías:** `@SpringBootTest`, `MockMvc`
- **Objetivo:** Endpoints HTTP con contexto Spring

```java
@SpringBootTest
@AutoConfigureMockMvc
class AdditionControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void shouldReturnCorrectSum() throws Exception {
        mockMvc.perform(get("/add?num1=5&num2=3"))
            .andExpect(status().isOk())
            .andExpect(content().string("8"));
    }
}
```

#### 3. **Pruebas Parametrizadas**
```java
@ParameterizedTest
@CsvSource({
    "0, 1",
    "5, 120",
    "10, 3628800"
})
void testFactorial(int input, long expected) {
    assertEquals(expected, mathService.factorial(input));
}
```

### Comandos de Testing

```bash
# Ejecutar todas las pruebas
mvn test

# Pruebas con cobertura
mvn clean test

# Prueba específica
mvn test -Dtest=MathServiceTest

# Ver reporte de cobertura
open target/site/jacoco/index.html
```

---

## 🔄 CI/CD y Automatización

### GitHub Actions Workflow

**Archivo:** `.github/workflows/maven.yml`

#### Pipeline Automatizado

1. **Build and Test:**
   - Compilación con Maven
   - Ejecución de 226+ pruebas
   - Generación de reportes Jacoco

2. **Extract Metrics:**
   - Total de pruebas ejecutadas
   - Errores y fallos
   - Porcentaje de cobertura

3. **Generate Badge:**
   - Badge de cobertura auto-actualizado
   - Guardado en `.github/badges/jacoco.svg`

4. **Quality Gate:**
   - ❌ Falla si >10% de pruebas fallan
   - ✅ Pasa si cobertura >= 80%

#### Triggers

- **Push** a rama `main`
- **Pull Request** a rama `main`
- **Workflow Dispatch** (manual)

#### Badges del Proyecto

```markdown
[![Java CI with Maven](badge-url)](workflow-url)
![Cobertura Jacoco](.github/badges/jacoco.svg)
```

---

## 👥 Guía de Contribución

Para contribuir al proyecto, consultar **[CONTRIBUTING.md](CONTRIBUTING.md)**

### Proceso de Desarrollo

1. **Fork y clonar el repositorio**
2. **Crear rama con convención:**
   - `feature/nombre-descriptivo`
   - `fix/descripcion-bug`
   - `docs/cambio-documentacion`

3. **Seguir estándares de código:**
   - Arquitectura en capas estricta
   - Javadoc en métodos públicos
   - Cobertura >= 80% (target 90%+)

4. **Commits descriptivos:**
   ```
   tipo(scope): descripción breve
   
   feat(math): add power operation endpoint
   fix(dijkstra): correct path calculation
   ```

5. **Pull Request Requirements:**
   - ✅ Todos los tests pasan
   - ✅ Cobertura >= 80%
   - ✅ Label `unit-testing` añadido
   - ✅ CI/CD pasa

### Estándares de Código

```java
// ✅ CORRECTO: Controller delega a Service
@RestController
public class MathController {
    private final MathService mathService;
    
    public MathController(MathService mathService) {
        this.mathService = mathService;
    }
    
    @PostMapping("/api/math/calculate")
    public ResponseEntity<?> calculate(@RequestBody Request request) {
        int result = mathService.calculate(request.getA(), request.getB());
        return ResponseEntity.ok(result);
    }
}

// ❌ INCORRECTO: Lógica de negocio en Controller
@RestController
public class MathController {
    @PostMapping("/api/math/calculate")
    public ResponseEntity<?> calculate(@RequestBody Request request) {
        int result = request.getA() + request.getB(); // ❌ NO HACER ESTO
        return ResponseEntity.ok(result);
    }
}
```

---

## ⚙️ Configuración y Desarrollo

### Requisitos Previos

- **Java 17+** (OpenJDK o Oracle JDK)
- **Maven 3.6+**
- **Git**
- IDE recomendado: IntelliJ IDEA, Eclipse, VS Code

### Instalación

```bash
# Clonar repositorio
git clone https://github.com/juananmora/poc-springboot-sum-integers.git
cd poc-springboot-sum-integers

# Instalar dependencias
mvn clean install

# Ejecutar pruebas
mvn test

# Iniciar aplicación
mvn spring-boot:run
```

### Dependencias Principales

**pom.xml** configurado con:
- `spring-boot-starter-web` - API REST
- `spring-boot-starter-security` - Autenticación
- `spring-boot-starter-test` - Testing
- `mockito-core` y `mockito-junit-jupiter` - Mocking
- `jacoco-maven-plugin` - Cobertura de código

### Comandos Maven Útiles

```bash
# Compilar sin tests
mvn compile test-compile

# Empaquetar aplicación
mvn package

# Limpiar y reconstruir
mvn clean compile

# Verificar calidad
mvn verify

# Ver árbol de dependencias
mvn dependency:tree

# Ejecutar con debug
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug ..."
```

---

## 📚 Documentación Adicional

### Documentos del Proyecto

| Documento | Descripción |
|-----------|-------------|
| **README.md** | Guía principal con comandos de testing |
| **API.md** | Documentación completa de endpoints REST |
| **CONTRIBUTING.md** | Estándares y proceso de contribución |
| **AUTHENTICATION_IMPLEMENTATION.md** | Sistema de autenticación y seguridad |
| **testresults.md** | Informe detallado de pruebas |
| **testresults_paths.md** | Análisis del algoritmo de Dijkstra |
| **.github/copilot-instructions.md** | Instrucciones para agentes AI |

### Archivos de Configuración

| Archivo | Propósito |
|---------|-----------|
| **pom.xml** | Configuración Maven y dependencias |
| **.github/workflows/maven.yml** | Pipeline CI/CD |
| **.gitignore** | Archivos excluidos de git |
| **LICENSE** | Apache License 2.0 |

### Recursos Externos

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Maven Documentation](https://maven.apache.org/guides/)

---

## 🎯 Conclusión

Spring Boot Math Service es un proyecto de referencia que demuestra:

✅ **Arquitectura sólida** con separación de capas  
✅ **Testing exhaustivo** con 95%+ cobertura  
✅ **Seguridad implementada** siguiendo mejores prácticas  
✅ **CI/CD automatizado** con GitHub Actions  
✅ **Documentación completa** para desarrolladores  
✅ **Código mantenible** con estándares claros

### Métricas del Proyecto

- **226+ pruebas** automatizadas
- **95.1%** cobertura de instrucciones
- **100%** cobertura de ramas
- **29 archivos Java** en src/main
- **16 archivos Java** en src/test
- **37+ archivos** de documentación

### Para Empezar

1. Lee **[README.md](README.md)** para comandos básicos
2. Consulta **[API.md](API.md)** para usar los endpoints
3. Revisa **[CONTRIBUTING.md](CONTRIBUTING.md)** antes de contribuir
4. Ejecuta `mvn test` para verificar tu entorno

---

**Última actualización:** 2025-12-06  
**Mantenedores:** Ver [CONTRIBUTING.md](CONTRIBUTING.md)  
**Licencia:** Apache 2.0 - Ver [LICENSE](LICENSE)
