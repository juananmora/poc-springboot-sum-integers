# Spring Boot Math Service - AI Coding Agent Instructions

## 🎯 Project Overview
This is a Spring Boot 3.3.5 (Java 17) application providing mathematical operations and graph algorithms via REST APIs. The project emphasizes **test-driven development** with 226+ tests and 95%+ code coverage using JUnit 5, Mockito, and Jacoco.

## 🏗️ Architecture Pattern: Strict Layer Separation

**CRITICAL:** Always follow the layered architecture pattern established in this codebase:

### Controller Layer (`com.example.demo.controller`)
- **Responsibility:** HTTP handling ONLY - request validation, type conversion, response formatting
- **Example:** See `AdditionController.java` - delegates all logic to `MathService`
- **Never:** Put business logic, calculations, or domain validations in controllers
- **Use:** Constructor injection for dependencies (not `@Autowired` fields)

### Service Layer (`com.example.demo.service`)
- **Responsibility:** Business logic, domain validations, calculations
- **Example:** See `MathService.java` - contains all mathematical operations and validations
- **Pattern:** Validate inputs, throw `IllegalArgumentException` for invalid data
- **Use:** `@Service` annotation and document public methods with Javadoc

### DTO Layer (`com.example.demo.dto`)
- **Responsibility:** Data transfer objects for complex request/response structures
- **Example:** `Graph.java`, `DijkstraResult.java` for graph algorithm endpoints
- **Pattern:** Immutable where possible, include validation in constructors

### Global Error Handling
- **Use:** `GlobalExceptionHandler.java` with `@ControllerAdvice` for centralized exception handling
- **Pattern:** Return structured error responses (Map/JSON), not plain strings

## ✅ Testing Standards (Non-Negotiable)

### Test Structure
All tests follow JUnit 5 conventions with `@Nested` classes and `@DisplayName`:
```java
@DisplayName("MathService Tests")
class MathServiceTest {
    @Nested
    @DisplayName("Addition Operation Tests")
    class AddTests {
        @Test
        @DisplayName("Should add two positive numbers correctly")
        void testAddPositiveNumbers() { /* ... */ }
    }
}
```

### Coverage Requirements
- **Minimum:** 80% overall (enforced in CI/CD)
- **Current:** 95%+ (instructions), 100% (branches), 96% (lines)
- **Target:** Services and controllers must have >90% coverage
- **Measure:** Run `mvn clean test` - report available at `target/site/jacoco/index.html`

### Test Types
1. **Unit Tests (Service Layer):** Test business logic in isolation
   - Use `@ExtendWith(MockitoExtension.class)`
   - Mock dependencies with `@Mock` and `@InjectMocks`
   - See: `MathServiceTest.java` - 126 tests for pure logic validation

2. **Integration Tests (Controller Layer):** Test HTTP endpoints with Spring context
   - Use `@SpringBootTest` and `@AutoConfigureMockMvc`
   - Use `MockMvc` to simulate HTTP requests
   - See: `AdditionControllerIntegrationTest.java` - tests full request/response cycle

3. **Parameterized Tests:** For multiple input scenarios
   - Use `@ParameterizedTest` with `@CsvSource` or `@ValueSource`
   - Example: Testing square root with multiple perfect squares

### Test Naming Convention
- Test classes: `{ClassName}Test.java` or `{ClassName}IntegrationTest.java`
- Test methods: Descriptive names like `testSqrtNegativeNumber()` or `shouldReturnBadRequestForNegativeNumber()`

## 🔄 GitHub/Jira Workflow

### Issue Processing
1. **Read GitHub Issue:** Extract Jira ticket URL (e.g., `JON-3394: https://jira.example.com/browse/JON-3394`)
2. **Consult `jon-space-documentation` Space:** Get coding patterns and best practices
3. **Implement:** Follow the layered architecture (Controller → Service → DTO)
4. **Test:** Create comprehensive unit and integration tests (>80% coverage)
5. **Report:** Generate `testresults.md` following the template below and post to Jira

### PR Requirements
- All PRs require the `unit-testing` label
- Tests must pass in CI/CD (see `.github/workflows/maven.yml`)
- Coverage badge auto-updates on merge to `main`

## 🛠️ Development Commands

### Essential Maven Commands
```bash
# Run all tests with coverage
mvn clean test

# Generate only Jacoco report (after tests)
mvn jacoco:report

# Run specific test class
mvn test -Dtest=MathServiceTest

# Run tests matching pattern
mvn test -Dtest=Addition*

# Package without tests (not recommended)
mvn package -DskipTests

# Verify code quality
mvn verify
```

### CI/CD Integration
- **Workflow:** `.github/workflows/maven.yml` runs on push/PR to `main`
- **Metrics:** Extracts test counts and coverage percentages
- **Failure Threshold:** >10% test failure rate blocks the build
- **Coverage Badge:** Auto-generated at `.github/badges/jacoco.svg`

## 📝 Code Examples from This Codebase

### Good: Layered Architecture
```java
// Service: Business logic
@Service
public class MathService {
    public long factorial(int number) {
        if (number < 0) throw new IllegalArgumentException("Must be non-negative");
        if (number > 20) throw new IllegalArgumentException("Overflow risk");
        return calculateFactorial(number);
    }
}

// Controller: HTTP handling
@RestController
public class MathController {
    private final MathService mathService;
    
    public MathController(MathService mathService) {
        this.mathService = mathService;
    }
    
    @PostMapping("/api/math/factorial")
    public ResponseEntity<?> factorial(@RequestBody Map<String, Object> request) {
        if (!request.containsKey("number")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing 'number'"));
        }
        try {
            int num = ((Number) request.get("number")).intValue();
            long result = mathService.factorial(num);
            return ResponseEntity.ok(Map.of("result", result));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
```

### Good: Comprehensive Testing
```java
@Nested
@DisplayName("Factorial Operation Tests")
class FactorialTests {
    @Test
    @DisplayName("Should throw IllegalArgumentException for negative numbers")
    void testFactorialNegative() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> mathService.factorial(-5)
        );
        assertEquals("Must be non-negative", ex.getMessage());
    }
    
    @ParameterizedTest
    @CsvSource({"0,1", "1,1", "5,120", "10,3628800"})
    @DisplayName("Should calculate correct factorial for valid inputs")
    void testFactorialValid(int input, long expected) {
        assertEquals(expected, mathService.factorial(input));
    }
}
```

## 📊 Test Results Template

When generating `testresults.md` or Jira comments, follow this structure (see existing `testresults.md` for reference):

```markdown
# Informe de Resultados de Pruebas Automatizadas

**Proyecto:** POC Spring Boot Sum Integers
**Fecha de ejecución:** [dd/mm/aaaa - HH:mm:ss UTC]
**Entorno:** [GitHub Actions / Visual Studio Code]
**Comando utilizado:** `mvn clean test`
**Issue Jira:** [TICKET-ID]

## 📊 Resumen General
- Total de pruebas ejecutadas: [número]
- Pruebas exitosas: [número]
- Pruebas fallidas: [número]

## 🔍 Cobertura de Código (Jacoco)
- Por instrucciones: [XX]%
- Por ramas: [XX]%
- Por líneas: [XX]%
- Por métodos: [XX]%
- Por clases: [XX]%

## 🧪 Detalles por Framework
### JUnit
- Total de pruebas: [número]
- Clases probadas: [lista]

### Mockito
- Componentes simulados: [lista]

## 🌐 Simulaciones HTTP (MockMvc)
- Endpoints probados: [lista con resultados esperados]
```

## 🔗 Key Files for Context
- `SPACE_PATTERNS_DEEP_DIVE.md`: Detailed examples of pattern application from `jon-space-documentation`
- `README.md`: Comprehensive testing guide and Maven commands
- `pom.xml`: Spring Boot 3.3.5, Java 17, Jacoco 0.8.11 configuration
- `testresults.md`: Latest test execution results template

## ⚠️ Common Pitfalls to Avoid
1. **Don't** mix business logic in controllers (see `SPACE_PATTERNS_DEEP_DIVE.md` for anti-patterns)
2. **Don't** skip integration tests - both unit and integration are required
3. **Don't** use `@Autowired` on fields - use constructor injection
4. **Don't** return generic HTTP errors - use structured JSON responses
5. **Don't** forget to document new operations in `testresults.md`