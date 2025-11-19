# Contributing to Spring Boot Math Service

Thank you for your interest in contributing to the Spring Boot Math Service! This document provides guidelines and best practices for contributing to this project.

## 📋 Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Setup](#development-setup)
- [Coding Standards](#coding-standards)
- [Testing Requirements](#testing-requirements)
- [Pull Request Process](#pull-request-process)
- [Architecture Guidelines](#architecture-guidelines)
- [Documentation Standards](#documentation-standards)

## Code of Conduct

This project adheres to professional standards of conduct. Contributors are expected to:
- Be respectful and inclusive
- Accept constructive criticism gracefully
- Focus on what is best for the community
- Show empathy towards other contributors

## Getting Started

### Prerequisites

Before you begin, ensure you have:
- **Java 17** or higher installed
- **Maven 3.6+** for dependency management
- **Git** for version control
- An IDE with Java support (IntelliJ IDEA, Eclipse, or VS Code recommended)

### Fork and Clone

1. Fork the repository on GitHub
2. Clone your fork locally:
   ```bash
   git clone https://github.com/your-username/poc-springboot-sum-integers.git
   cd poc-springboot-sum-integers
   ```
3. Add the upstream repository:
   ```bash
   git remote add upstream https://github.com/juananmora/poc-springboot-sum-integers.git
   ```

## Development Setup

### Build the Project

```bash
# Clean and compile
mvn clean compile

# Run all tests
mvn test

# Generate test coverage report
mvn clean test
# View report at: target/site/jacoco/index.html
```

### Run the Application

```bash
# Start the Spring Boot application
mvn spring-boot:run

# Application will be available at http://localhost:8080
```

## Coding Standards

### Layered Architecture

This project follows a strict layered architecture pattern. Always respect the separation of concerns:

#### Controller Layer (`com.example.demo.controller`)
- **Responsibility:** HTTP request/response handling ONLY
- **DO:** 
  - Validate HTTP parameters
  - Convert request data to appropriate types
  - Format responses
  - Handle HTTP status codes
- **DON'T:**
  - Put business logic here
  - Perform calculations
  - Directly access databases
  
**Example:**
```java
@RestController
public class MathController {
    private final MathService mathService;
    
    public MathController(MathService mathService) {
        this.mathService = mathService;
    }
    
    @GetMapping("/add")
    public ResponseEntity<Integer> add(@RequestParam int a, @RequestParam int b) {
        int result = mathService.add(a, b);
        return ResponseEntity.ok(result);
    }
}
```

#### Service Layer (`com.example.demo.service`)
- **Responsibility:** Business logic, domain validations, calculations
- **DO:**
  - Implement business rules
  - Validate domain constraints
  - Perform calculations
  - Throw meaningful exceptions
- **DON'T:**
  - Handle HTTP concerns
  - Return HTTP status codes
  
**Example:**
```java
@Service
public class MathService {
    public int add(int num1, int num2) {
        if (num1 < 0 || num2 < 0) {
            throw new IllegalArgumentException("Numbers must be non-negative");
        }
        return num1 + num2;
    }
}
```

#### DTO Layer (`com.example.demo.dto`)
- **Responsibility:** Data transfer objects for complex structures
- **DO:**
  - Keep DTOs immutable when possible
  - Include validation in constructors
  - Use meaningful names

### Java Code Style

1. **Constructor Injection**: Always use constructor injection, not field injection
   ```java
   // ✅ GOOD
   public class MyController {
       private final MyService service;
       
       public MyController(MyService service) {
           this.service = service;
       }
   }
   
   // ❌ BAD
   public class MyController {
       @Autowired
       private MyService service;
   }
   ```

2. **Exception Handling**: Use specific exception types and meaningful messages
   ```java
   // ✅ GOOD
   if (divisor == 0) {
       throw new ArithmeticException("Cannot divide by zero");
   }
   
   // ❌ BAD
   if (divisor == 0) {
       throw new Exception("Error");
   }
   ```

3. **Method Naming**: Use clear, descriptive names
   ```java
   // ✅ GOOD
   public double calculateMean(List<Integer> numbers)
   
   // ❌ BAD
   public double calc(List<Integer> n)
   ```

## Testing Requirements

### Coverage Requirements

- **Minimum Overall Coverage:** 80% (enforced in CI/CD)
- **Target Coverage:** 90%+ for services and controllers
- **Current Coverage:** 95%+ (maintain or improve)

### Test Structure

All tests must follow JUnit 5 conventions with nested classes and display names:

```java
@DisplayName("MathService Tests")
class MathServiceTest {
    
    @Nested
    @DisplayName("Addition Operation Tests")
    class AddTests {
        
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
        
        @Test
        @DisplayName("Should throw exception for negative numbers")
        void testAddNegativeNumbers() {
            MathService service = new MathService();
            
            assertThrows(IllegalArgumentException.class, 
                () -> service.add(-5, 3));
        }
    }
}
```

### Test Types Required

1. **Unit Tests (Service Layer)**
   - Test business logic in isolation
   - Use Mockito for dependencies
   - File pattern: `*Test.java`

2. **Integration Tests (Controller Layer)**
   - Test HTTP endpoints with Spring context
   - Use MockMvc or TestRestTemplate
   - File pattern: `*IntegrationTest.java`

3. **Parameterized Tests**
   - For testing multiple input scenarios
   - Use `@ParameterizedTest` with `@CsvSource` or `@ValueSource`

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=MathServiceTest

# Run tests with pattern
mvn test -Dtest=Addition*

# Generate coverage report
mvn clean test
open target/site/jacoco/index.html
```

## Pull Request Process

### Before Submitting

1. **Update from upstream:**
   ```bash
   git fetch upstream
   git rebase upstream/main
   ```

2. **Run all tests:**
   ```bash
   mvn clean test
   ```

3. **Check code coverage:**
   - Ensure coverage meets minimum 80%
   - View report: `target/site/jacoco/index.html`

4. **Run the application:**
   ```bash
   mvn spring-boot:run
   # Test endpoints manually
   ```

### Pull Request Guidelines

1. **Title:** Use clear, descriptive titles
   - ✅ "Add factorial calculation endpoint with validation"
   - ❌ "Update code"

2. **Description:** Include:
   - What changes were made
   - Why the changes were needed
   - How to test the changes
   - Related issue numbers

3. **Tests:** All PRs must include:
   - Unit tests for new service methods
   - Integration tests for new endpoints
   - Maintain or improve coverage percentage

4. **Documentation:**
   - Add Javadoc for all public methods
   - Update README.md if adding new features
   - Include API usage examples

5. **Labels:** Add appropriate labels:
   - `unit-testing` - Required for all PRs
   - `enhancement` - New features
   - `bug` - Bug fixes
   - `documentation` - Documentation updates

### Code Review Process

All PRs will be reviewed for:
- Adherence to layered architecture
- Test coverage (minimum 80%)
- Code quality and style
- Documentation completeness
- Security best practices

## Architecture Guidelines

### Adding New Endpoints

When adding a new endpoint, follow this pattern:

1. **Create/Update Service Method:**
   ```java
   @Service
   public class MathService {
       /**
        * Calculates the factorial of a number.
        * @param n the number (must be non-negative)
        * @return the factorial
        * @throws IllegalArgumentException if n is negative
        */
       public long factorial(int n) {
           if (n < 0) {
               throw new IllegalArgumentException("Cannot calculate factorial of negative number");
           }
           // Implementation
       }
   }
   ```

2. **Create Controller Endpoint:**
   ```java
   @RestController
   @RequestMapping("/api/math")
   public class MathController {
       private final MathService mathService;
       
       @PostMapping("/factorial")
       public ResponseEntity<Map<String, Object>> factorial(@RequestBody Map<String, Object> request) {
           try {
               int number = (Integer) request.get("number");
               long result = mathService.factorial(number);
               return ResponseEntity.ok(Map.of("result", result));
           } catch (IllegalArgumentException e) {
               return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
           }
       }
   }
   ```

3. **Add Unit Tests:**
   ```java
   @Test
   @DisplayName("Should calculate factorial of positive number")
   void testFactorialPositive() {
       assertEquals(120, mathService.factorial(5));
   }
   ```

4. **Add Integration Tests:**
   ```java
   @Test
   void testFactorialEndpoint() throws Exception {
       mockMvc.perform(post("/api/math/factorial")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{\"number\": 5}"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.result").value(120));
   }
   ```

### Error Handling Pattern

Always use the global exception handler for consistency. For domain-specific errors, throw appropriate exceptions in the service layer:

```java
// Service layer
public double divide(int a, int b) {
    if (b == 0) {
        throw new ArithmeticException("Cannot divide by zero");
    }
    return (double) a / b;
}

// Controller layer - let GlobalExceptionHandler handle it,
// or catch and return appropriate HTTP response
```

## Documentation Standards

### Javadoc Requirements

All public methods must have Javadoc that includes:

```java
/**
 * Brief description of what the method does.
 * 
 * More detailed explanation if needed, including:
 * - Algorithm complexity
 * - Special behaviors
 * - Edge cases
 * 
 * @param paramName description of parameter (include constraints)
 * @return description of return value
 * @throws ExceptionType when this exception is thrown
 * 
 * @apiNote Optional usage notes or examples
 */
public ReturnType methodName(ParamType paramName) {
    // Implementation
}
```

### README Updates

When adding new features:
1. Update the "Endpoints Disponibles" section
2. Add usage examples with curl commands
3. Update the project structure if adding new packages
4. Add to test results if significant testing added

### API Documentation

For new endpoints, document:
- HTTP method and path
- Request parameters/body format
- Response format (success and error)
- Example curl commands
- Possible error codes

## Security Best Practices

1. **Never log sensitive data:**
   ```java
   // ✅ GOOD
   LOGGER.info("User authentication attempt");
   
   // ❌ BAD
   LOGGER.info("Login with password: " + password);
   ```

2. **Validate all inputs:**
   ```java
   // Always validate at service layer
   if (input == null || input.isEmpty()) {
       throw new IllegalArgumentException("Input cannot be empty");
   }
   ```

3. **Use BCrypt for passwords:**
   ```java
   // ✅ GOOD
   String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
   
   // ❌ BAD
   String encoded = Base64.encode(password);
   ```

## Questions?

If you have questions about contributing:
1. Check existing documentation in the repository
2. Review closed PRs for examples
3. Open an issue with the `question` label

## Thank You!

Your contributions make this project better. We appreciate your time and effort! 🎉
