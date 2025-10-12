---
mode: agent
---

# Spring Boot Math Service - AI Agent Task Definition

## 🎯 Task Objective
Implement new mathematical operations or graph algorithms for the Spring Boot Math Service following strict test-driven development (TDD) and layered architecture patterns.

## 📋 Requirements

### Functional Requirements
1. **Read the GitHub Issue/Jira Ticket:**
   - Extract the Jira ticket ID (e.g., JON-XXXX, JUAN-X)
   - Identify the mathematical operation or algorithm to implement
   - Understand acceptance criteria and edge cases

2. **Implement Following Layered Architecture:**
   - **Service Layer:** Business logic, validations, calculations in `com.example.demo.service`
   - **Controller Layer:** HTTP handling only in `com.example.demo.controller`
   - **DTO Layer:** Complex request/response objects in `com.example.demo.dto`
   - **Global Error Handling:** Centralized exception handling via `GlobalExceptionHandler`

3. **Create Comprehensive Tests:**
   - **Unit Tests:** Service layer logic with Mockito (>90% coverage required)
   - **Integration Tests:** Controller endpoints with MockMvc and @SpringBootTest
   - Use `@Nested` classes and `@DisplayName` for test organization
   - Include parameterized tests for multiple input scenarios

### Technical Requirements
- **Language:** Java 17
- **Framework:** Spring Boot 3.3.5
- **Testing:** JUnit 5, Mockito, MockMvc
- **Coverage:** Minimum 80% (target 95%+)
- **Coverage Tool:** Jacoco 0.8.11
- **Build Tool:** Maven 3.x

### Code Quality Standards
1. **Dependency Injection:** Constructor injection (NO `@Autowired` fields)
2. **Error Handling:** Throw `IllegalArgumentException` for invalid inputs
3. **Documentation:** Javadoc for public methods in services
4. **Response Format:** Structured JSON (Map) for all endpoints
5. **Validation:** Input validation in service layer, not controllers

## 🚫 Constraints

### Architecture Constraints
- **NO business logic in controllers** - Controllers only handle HTTP concerns
- **NO direct database access** - Use service layer for all operations
- **NO plain string error responses** - Always return structured JSON
- **NO field injection** - Use constructor injection exclusively

### Testing Constraints
- **ALL new code must have tests** - No code without corresponding tests
- **Coverage cannot drop below 80%** - Enforced in CI/CD
- **Integration tests required** - Not just unit tests
- **Test naming convention:** `{ClassName}Test.java` or `{ClassName}IntegrationTest.java`

### Process Constraints
- **Follow GitHub/Jira workflow** - Tag PRs with `unit-testing` label
- **Generate test report** - Create `testresults.md` after implementation
- **Document in Jira** - Post results to the Jira ticket
- **Verify CI/CD** - All tests must pass in GitHub Actions

## ✅ Success Criteria

### Implementation Success
- [ ] Service layer class created with business logic
- [ ] Controller class created with HTTP endpoints
- [ ] DTO classes created if needed for complex structures
- [ ] Global exception handler updated if new exception types added
- [ ] Code follows existing patterns (see `MathService.java`, `AdditionController.java`)

### Testing Success
- [ ] Unit tests created with >90% coverage for service layer
- [ ] Integration tests created for all controller endpoints
- [ ] Tests use `@Nested` and `@DisplayName` annotations
- [ ] Parameterized tests included for edge cases
- [ ] All tests pass: `mvn clean test`
- [ ] Coverage report generated: `mvn jacoco:report`
- [ ] Overall coverage maintained at >80%

### Documentation Success
- [ ] `testresults.md` generated with complete metrics
- [ ] Test results posted to Jira ticket
- [ ] Code includes Javadoc for public methods
- [ ] README updated if new endpoints added

### CI/CD Success
- [ ] GitHub Actions workflow passes
- [ ] Coverage badge updates correctly
- [ ] No test failure rate >10%
- [ ] PR includes `unit-testing` label

## 📊 Deliverables

1. **Source Code:**
   - Service class: `src/main/java/com/example/demo/service/{Feature}Service.java`
   - Controller class: `src/main/java/com/example/demo/controller/{Feature}Controller.java`
   - DTOs (if needed): `src/main/java/com/example/demo/dto/{Feature}*.java`

2. **Test Code:**
   - Unit tests: `src/test/java/com/example/demo/service/{Feature}ServiceTest.java`
   - Integration tests: `src/test/java/com/example/demo/controller/{Feature}ControllerIntegrationTest.java`

3. **Documentation:**
   - `testresults.md` with execution metrics
   - Jira comment with results summary
   - Jacoco HTML report: `target/site/jacoco/index.html`

## 🔍 Validation Commands

```bash
# Run all tests with coverage
mvn clean test

# Generate coverage report
mvn jacoco:report

# Verify code quality
mvn verify

# Run specific test class
mvn test -Dtest={ClassName}Test

# View coverage report
open target/site/jacoco/index.html  # macOS
xdg-open target/site/jacoco/index.html  # Linux
```

## 📚 Reference Examples

### Good Service Layer Example
```java
@Service
public class MathService {
    /**
     * Calculates the factorial of a non-negative integer.
     * @param number The input number (0-20)
     * @return The factorial result
     * @throws IllegalArgumentException if number is negative or > 20
     */
    public long factorial(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Number must be non-negative");
        }
        if (number > 20) {
            throw new IllegalArgumentException("Number too large, risk of overflow");
        }
        return calculateFactorial(number);
    }
}
```

### Good Controller Layer Example
```java
@RestController
public class MathController {
    private final MathService mathService;
    
    public MathController(MathService mathService) {
        this.mathService = mathService;
    }
    
    @PostMapping("/api/math/factorial")
    public ResponseEntity<?> factorial(@RequestBody Map<String, Object> request) {
        if (!request.containsKey("number")) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Missing required field 'number'"));
        }
        try {
            int num = ((Number) request.get("number")).intValue();
            long result = mathService.factorial(num);
            return ResponseEntity.ok(Map.of("result", result));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }
}
```

### Good Test Example
```java
@Nested
@DisplayName("Factorial Operation Tests")
class FactorialTests {
    @Test
    @DisplayName("Should throw exception for negative numbers")
    void testFactorialNegative() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> mathService.factorial(-5)
        );
        assertEquals("Number must be non-negative", ex.getMessage());
    }
    
    @ParameterizedTest
    @CsvSource({"0,1", "1,1", "5,120", "10,3628800"})
    @DisplayName("Should calculate correct factorial for valid inputs")
    void testFactorialValid(int input, long expected) {
        assertEquals(expected, mathService.factorial(input));
    }
}
```

## 🔗 Key Reference Files
- `.github/copilot-instructions.md` - Complete project guidelines
- `SPACE_PATTERNS_DEEP_DIVE.md` - Detailed pattern examples
- `README.md` - Testing guide and Maven commands
- `testresults.md` - Test report template

## ⚠️ Common Anti-Patterns to Avoid
1. ❌ Putting calculations in controllers
2. ❌ Using field injection with `@Autowired`
3. ❌ Returning plain string error messages
4. ❌ Skipping integration tests
5. ❌ Not documenting public methods
6. ❌ Ignoring edge cases in tests
7. ❌ Forgetting to update test results documentation

---

**Remember:** This project prioritizes **test coverage** and **clean architecture**. Follow the established patterns, write comprehensive tests, and maintain the high quality standards (95%+ coverage).