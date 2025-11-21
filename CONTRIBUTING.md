# Guía de Contribución

¡Gracias por tu interés en contribuir a Spring Boot Math Service! Este documento proporciona guías y estándares para contribuir al proyecto.

## 📋 Tabla de Contenidos

- [Código de Conducta](#código-de-conducta)
- [Cómo Empezar](#cómo-empezar)
- [Proceso de Desarrollo](#proceso-de-desarrollo)
- [Estándares de Código](#estándares-de-código)
- [Estándares de Testing](#estándares-de-testing)
- [Proceso de Pull Request](#proceso-de-pull-request)
- [Reportar Issues](#reportar-issues)

## 🤝 Código de Conducta

Este proyecto se adhiere a un código de conducta profesional. Al participar, se espera que mantengas un ambiente respetuoso y colaborativo.

## 🚀 Cómo Empezar

### Requisitos Previos

- **Java 17+** (OpenJDK o Oracle JDK)
- **Maven 3.6+**
- **Git**
- IDE recomendado: IntelliJ IDEA, Eclipse, o VS Code con extensiones Java

### Configurar el Entorno de Desarrollo

1. **Fork y clonar el repositorio:**
   ```bash
   git clone https://github.com/YOUR-USERNAME/poc-springboot-sum-integers.git
   cd poc-springboot-sum-integers
   ```

2. **Instalar dependencias:**
   ```bash
   mvn clean install
   ```

3. **Ejecutar tests para verificar la configuración:**
   ```bash
   mvn test
   ```

4. **Ejecutar la aplicación:**
   ```bash
   mvn spring-boot:run
   ```

## 🔄 Proceso de Desarrollo

### 1. Crear una Rama

```bash
git checkout -b feature/nombre-descriptivo
# o
git checkout -b fix/descripcion-del-bug
```

Convenciones de nombres de rama:
- `feature/` - Nueva funcionalidad
- `fix/` - Corrección de bug
- `docs/` - Cambios en documentación
- `refactor/` - Refactorización de código
- `test/` - Añadir o mejorar tests

### 2. Hacer Cambios

- Sigue los [estándares de código](#estándares-de-código)
- Escribe tests para tus cambios
- Mantén commits pequeños y descriptivos
- Ejecuta tests frecuentemente

### 3. Commits

Usa mensajes de commit descriptivos siguiendo la convención:

```
tipo(scope): descripción breve

Descripción más detallada si es necesario.

Fixes #123
```

**Tipos de commit:**
- `feat`: Nueva funcionalidad
- `fix`: Corrección de bug
- `docs`: Cambios en documentación
- `style`: Formato, espacios en blanco (no cambios de código)
- `refactor`: Refactorización de código
- `test`: Añadir o corregir tests
- `chore`: Cambios en build, herramientas, etc.

**Ejemplos:**
```bash
git commit -m "feat(math): add power operation endpoint"
git commit -m "fix(dijkstra): correct path calculation for single node"
git commit -m "docs(readme): update installation instructions"
git commit -m "test(auth): add integration tests for registration"
```

## 💻 Estándares de Código

### Arquitectura en Capas (CRÍTICO)

**SIEMPRE** sigue la separación de capas establecida:

#### Controller Layer
- **Responsabilidad:** Solo manejo de HTTP (validación de parámetros, conversión de tipos, formateo de respuesta)
- **Nunca:** Poner lógica de negocio, cálculos o validaciones de dominio
- **Usar:** Constructor injection para dependencias

```java
@RestController
public class MathController {
    private final MathService mathService;
    
    // Constructor injection
    public MathController(MathService mathService) {
        this.mathService = mathService;
    }
    
    @PostMapping("/api/math/calculate")
    public ResponseEntity<?> calculate(@RequestBody Request request) {
        // 1. Validación HTTP
        // 2. Delegación al service
        // 3. Formateo de respuesta
    }
}
```

#### Service Layer
- **Responsabilidad:** Lógica de negocio, validaciones de dominio, cálculos
- **Pattern:** Validar entradas, lanzar `IllegalArgumentException` para datos inválidos
- **Usar:** `@Service` annotation y documentar métodos públicos con Javadoc

```java
@Service
public class MathService {
    /**
     * Javadoc describiendo el método, parámetros y excepciones
     */
    public int calculate(int a, int b) {
        if (a < 0 || b < 0) {
            throw new IllegalArgumentException("Los números deben ser positivos");
        }
        return a + b;
    }
}
```

#### DTO Layer
- **Responsabilidad:** Objetos para transferencia de datos
- **Pattern:** Inmutables donde sea posible, incluir validación en constructores

### Convenciones de Código Java

1. **Naming:**
   - Clases: `PascalCase` (ej: `MathService`)
   - Métodos: `camelCase` (ej: `calculateSum`)
   - Constantes: `UPPER_SNAKE_CASE` (ej: `MAX_VALUE`)

2. **Formato:**
   - Indentación: 4 espacios
   - Llaves: estilo K&R (opening brace en la misma línea)
   - Longitud de línea: máximo 120 caracteres

3. **Javadoc:**
   - Todas las clases públicas deben tener Javadoc
   - Todos los métodos públicos deben tener Javadoc
   - Incluir `@param`, `@return`, `@throws` según corresponda

```java
/**
 * Calcula el factorial de un número.
 * 
 * @param n el número (debe ser >= 0 y <= 20)
 * @return el factorial de n
 * @throws IllegalArgumentException si n < 0 o n > 20
 */
public long factorial(int n) {
    // implementación
}
```

## ✅ Estándares de Testing

### Requisitos de Cobertura

- **Mínimo:** 80% cobertura global (enforced en CI/CD)
- **Target:** 90%+ para Services y Controllers
- **Actual:** 95%+ (mantener o mejorar)

### Estructura de Tests

Usa JUnit 5 con `@Nested` y `@DisplayName`:

```java
@DisplayName("MathService Tests")
class MathServiceTest {
    
    @Nested
    @DisplayName("Addition Operation Tests")
    class AdditionTests {
        
        @Test
        @DisplayName("Should add two positive numbers correctly")
        void shouldAddPositiveNumbers() {
            // Arrange
            MathService service = new MathService();
            
            // Act
            int result = service.add(5, 3);
            
            // Assert
            assertEquals(8, result);
        }
    }
}
```

### Tipos de Tests Requeridos

#### 1. Unit Tests (Service Layer)
- Probar lógica de negocio en aislamiento
- Usar Mockito para mockear dependencias
- Archivo: `{ClassName}Test.java`

```java
@ExtendWith(MockitoExtension.class)
class MathServiceTest {
    @Mock
    private DependencyService dependency;
    
    @InjectMocks
    private MathService mathService;
    
    @Test
    void testBusinessLogic() {
        // Test implementation
    }
}
```

#### 2. Integration Tests (Controller Layer)
- Probar endpoints con contexto Spring completo
- Usar `@SpringBootTest` y `@AutoConfigureMockMvc`
- Archivo: `{ClassName}IntegrationTest.java`

```java
@SpringBootTest
@AutoConfigureMockMvc
class MathControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void shouldReturnCorrectResult() throws Exception {
        mockMvc.perform(post("/api/math/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"a\":5,\"b\":3}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value(8));
    }
}
```

#### 3. Parameterized Tests
- Para múltiples escenarios de entrada

```java
@ParameterizedTest
@CsvSource({
    "0, 1",
    "1, 1",
    "5, 120",
    "10, 3628800"
})
@DisplayName("Should calculate correct factorial for valid inputs")
void testFactorial(int input, long expected) {
    assertEquals(expected, mathService.factorial(input));
}
```

### Ejecutar Tests

```bash
# Todos los tests
mvn test

# Test específico
mvn test -Dtest=MathServiceTest

# Tests con patrón
mvn test -Dtest=Addition*

# Con cobertura
mvn clean test
# Ver reporte: target/site/jacoco/index.html
```

## 🔍 Proceso de Pull Request

### Antes de Crear el PR

1. **Asegúrate que todos los tests pasan:**
   ```bash
   mvn clean test
   ```

2. **Verifica la cobertura de código:**
   - Debe ser >= 80% global
   - Servicios y controladores >= 90%

3. **Ejecuta el build completo:**
   ```bash
   mvn clean verify
   ```

4. **Actualiza la documentación si es necesario:**
   - README.md para cambios de usuario
   - Javadoc para cambios de API
   - CONTRIBUTING.md para cambios de proceso

### Crear el Pull Request

1. **Push tu rama:**
   ```bash
   git push origin feature/tu-feature
   ```

2. **Crea el PR en GitHub:**
   - Título descriptivo
   - Descripción detallada de los cambios
   - Referencia issues relacionados
   - Incluye screenshots si hay cambios UI

3. **Template de PR:**
   ```markdown
   ## Descripción
   Breve descripción de los cambios realizados.
   
   ## Tipo de Cambio
   - [ ] Bug fix
   - [ ] Nueva funcionalidad
   - [ ] Breaking change
   - [ ] Documentación
   
   ## Testing
   - [ ] Tests unitarios añadidos/actualizados
   - [ ] Tests de integración añadidos/actualizados
   - [ ] Cobertura >= 80%
   - [ ] Todos los tests pasan
   
   ## Checklist
   - [ ] Código sigue los estándares del proyecto
   - [ ] Javadoc actualizado
   - [ ] README actualizado (si aplica)
   - [ ] No hay warnings de compilación
   - [ ] Labels apropiados añadidos (requiere `unit-testing`)
   
   ## Issues Relacionados
   Closes #123
   ```

### Revisión del PR

- El PR será revisado por mantenedores del proyecto
- Pueden solicitar cambios o mejoras
- Asegúrate de responder a los comentarios
- El CI/CD debe pasar (tests + cobertura)

### Merge del PR

- Requiere aprobación de al menos un mantenedor
- Todos los tests de CI/CD deben pasar
- Sin conflictos de merge

## 🐛 Reportar Issues

### Antes de Reportar

1. Busca issues existentes para evitar duplicados
2. Verifica que estás usando la última versión
3. Intenta reproducir el problema

### Template de Issue

#### Bug Report
```markdown
**Descripción del Bug**
Descripción clara y concisa del problema.

**Para Reproducir**
Pasos para reproducir el comportamiento:
1. Ejecutar '...'
2. Con parámetros '...'
3. Ver error

**Comportamiento Esperado**
Qué esperabas que sucediera.

**Comportamiento Actual**
Qué sucedió en realidad.

**Logs/Screenshots**
Si aplica, añade logs o screenshots.

**Entorno:**
- OS: [ej. Ubuntu 20.04]
- Java Version: [ej. 17.0.1]
- Maven Version: [ej. 3.8.1]
```

#### Feature Request
```markdown
**¿El feature request está relacionado con un problema?**
Descripción clara del problema. Ej: "Siempre es frustrante cuando [...]"

**Describe la solución que te gustaría**
Descripción clara de lo que quieres que suceda.

**Describe alternativas que has considerado**
Otras soluciones o features que has considerado.

**Contexto Adicional**
Cualquier otro contexto sobre el feature request.
```

## 📚 Recursos Adicionales

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Maven Documentation](https://maven.apache.org/guides/)

## 💬 Preguntas

Si tienes preguntas sobre cómo contribuir:
1. Revisa la documentación existente
2. Busca en issues cerrados
3. Abre un nuevo issue con la etiqueta `question`

---

**¡Gracias por contribuir! 🎉**
