# AGENTS.md

## Setup commands
- Install deps: `mvn clean install`
- Run tests: `mvn test`
- Generate coverage: `mvn clean test jacoco:report`
- Start app: `mvn spring-boot:run`

## Code style
- Java 17 features preferred
- Spring Boot 3.3.5 conventions
- Defensive programming with null checks
- Spanish error messages for user-facing errors
- Immutable operations (create copies for modifications)

## Testing instructions
- Use JUnit 5 with nested test classes
- Mock dependencies with Mockito
- Test HTTP endpoints with MockMvc
- Maintain >80% code coverage (JaCoCo)
- Run `mvn clean test` before committing
- Coverage reports available at `/target/site/jacoco/index.html`

## Architecture patterns
- Controllers: REST endpoints with consistent `Map<String, Object>` responses
- Services: Business logic with null validation
- Response format: `{"result": value, "operation": "name", "operands": [...]}`
- Error format: `{"error": "message"}`
- Graph algorithms use bidirectional adjacency lists

## Key conventions
- Controllers handle HTTP layer, delegate to services
- Services return primitives/collections, not ResponseEntity
- Always validate input parameters in services
- Copy collections before sorting/modifying
- Use `@ParameterizedTest` for multiple test cases
- Organize tests in nested classes by endpoint/method