# 🤖 AI Agent Instructions for Spring Boot Math Service

## 🎯 Project Overview
This is a **Spring Boot 3.3.5** application providing mathematical operations and graph algorithms via REST endpoints. The project emphasizes **comprehensive testing** (225+ tests) with JUnit 5, Mockito, and JaCoCo coverage analysis.

## 🏗️ Architecture & Patterns

### Core Components
- **Controllers**: `MathController`, `AdditionController`, `DijkstraController`
- **Services**: `MathService` (math ops), `DijkstraService` (graph algorithms)
- **DTOs**: `DijkstraResult`, `Graph`, `GraphEdge`
- **Error Handling**: `GlobalExceptionHandler` with consistent error responses

### Key Architectural Patterns
```java
// Controller pattern - consistent REST responses
@PostMapping("/sum")
public ResponseEntity<Map<String, Object>> sum(@RequestBody Map<String, Object> request) {
    try {
        int result = mathService.add(a, b);
        return ResponseEntity.ok(Map.of(
            "result", result,
            "operation", "sum",
            "operands", List.of(a, b)
        ));
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(Map.of("error", "Invalid input"));
    }
}
```

### Service Layer Patterns
```java
// Defensive programming with null checks
public int sumList(List<Integer> numbers) {
    if (numbers == null || numbers.isEmpty()) {
        return 0;
    }
    return numbers.stream().mapToInt(Integer::intValue).sum();
}

// Immutable operations - create copies for sorting
public List<Integer> quicksort(List<Integer> numbers) {
    if (numbers == null || numbers.isEmpty()) {
        return new ArrayList<>();
    }
    List<Integer> sortedNumbers = new ArrayList<>(numbers); // Copy
    quicksortHelperInteger(sortedNumbers, 0, sortedNumbers.size() - 1);
    return sortedNumbers;
}
```

# Detalles Específicos
- **Lectura de Input:**
    - Leer e interpretar la issue de GitHub asignada a ti (Copilot).
    - Extraer la URL de la issue de Jira mencionada en el cuerpo de la issue de GitHub.
- **Desarrollo del Código:**
    - Consultar el **MCP de context7** para obtener las directrices y buenas prácticas de programación aplicables al framework del proyecto (Spring Boot o el que corresponda).
    - Generar el código fuente completo (controladores, servicios, repositorios, DTOs, etc.) necesario para resolver la funcionalidad descrita.
- **Proceso de Pruebas:**
    - Generar clases de prueba para los nuevos servicios, controladores y repositorios.
    - Utilizar JUnit para validar la lógica de negocio.
    - Utilizar Mockito para simular dependencias (beans de servicios, repositorios, etc.).
    - Simular llamadas HTTP a los endpoints del controlador utilizando `MockMvc`.
    - Integrar Jacoco en el proyecto para medir la cobertura del código. La cobertura debe ser **superior al 80%** para considerarse aceptable.
- **Generación de Informes y Publicación:**
    - Ejecutar todas las pruebas (`mvn clean test`).
    - Generar un informe detallado con los resultados en formato Markdown (siguiendo la plantilla `testresults.md`).
    - **Publicar el contenido completo del informe Markdown como un comentario en la issue de Jira** identificada previamente.

# Contexto
Formas parte de un equipo que utiliza un flujo de trabajo integrado entre GitHub para el control de versiones y la gestión de tareas de desarrollo, y Jira para el seguimiento de incidencias y el aseguramiento de la calidad (QA). El desarrollo se realiza en Visual Studio Code. El objetivo es automatizar tanto la generación de código como su validación para mantener un alto nivel de calidad y agilidad.

# Ejemplos
- **Pregunta:** *Se te asigna una issue en GitHub titulada "Feature: Add endpoint to get user by ID". El cuerpo de la issue contiene: "Implementar la lógica para recuperar un usuario por su ID. Hacer seguimiento en JIRA-123: https://jira.example.com/browse/JIRA-123".*
  **Respuesta:** *Entendido. Procedo a:
  1. Leer la issue de GitHub.
  2. Extraer la URL de la issue JIRA-123.
  3. Consultar el MCP de context7 para las prácticas de Spring Boot.
  4. Generar el `UsuarioController`, `UsuarioService` y modificar el `UsuarioRepository`.
  5. Crear las pruebas unitarias y de integración correspondientes con Mockito y MockMvc.
  6. Ejecutar `mvn clean test` y generar el informe de resultados y cobertura.
  7. Publicar el informe completo como un comentario en la issue JIRA-123.*

- **Pregunta:** *¿Cómo obtienes las directrices de codificación?*
  **Respuesta:** *Consulto el MCP de context7, que es nuestra fuente de verdad única para patrones de diseño, guías de estilo y buenas prácticas de programación para cualquier framework que estemos utilizando.*

# Notas
- Se asume que se tienen los permisos y tokens de acceso necesarios para leer issues de GitHub y comentar en issues de Jira.
- El **MCP de context7** es la fuente de verdad única para todas las guías de estilo y patrones. Siempre debe ser consultado antes de generar código.
- Asegúrate de que el archivo `pom.xml` esté configurado con todas las dependencias necesarias: JUnit, Mockito, Spring Test y Jacoco.
- Recuerda limpiar y reconstruir el proyecto antes de ejecutar las pruebas (`mvn clean test`).
- El informe generado por Jacoco debe estar disponible en formato HTML (`/target/site/jacoco/index.html`) y su resumen debe incluirse en el comentario de Jira.

---

## 📘 Plantilla base para el informe de resultados (a publicar en Jira)

*El contenido generado en `testresults.md`, y que será publicado como comentario en Jira, debe seguir la siguiente plantilla:*

```markdown
# Informe de Resultados de Pruebas Automatizadas

**Proyecto:** [Nombre del proyecto]
**Fecha de ejecución:** [dd/mm/aaaa]
**Entorno:** Visual Studio Code
**Comando utilizado:** `mvn clean test`

---

## 📊 Resumen General

- **Total de pruebas ejecutadas:** [número]
- **Pruebas exitosas:** [número]
- **Pruebas fallidas:** [número]
- **Pruebas con errores:** [número]

---

## 🔍 Cobertura de Código (Jacoco)

- **Cobertura total del proyecto:**
  - Por clases: [porcentaje]%
  - Por métodos: [porcentaje]%
  - Por líneas: [porcentaje]%

- **Clases con menor cobertura:**
  - `[NombreClase]`: [porcentaje]%
  - `[NombreClase]`: [porcentaje]%

- **Clases con cobertura completa:**
  - `[NombreClase]`
  - `[NombreClase]`

> **Ruta del informe HTML completo:** `/target/site/jacoco/index.html`

---

## 🧪 Detalles por Framework

### JUnit

- **Total de pruebas unitarias:** [número]
- **Clases probadas:**
  - `[NombreServicio]Test`
  - `[NombreControlador]Test`

- **Casos validados:**
  - Lógica de negocio
  - Validaciones de entrada
  - Cálculo de resultados esperados

---

### Mockito

- **Total de mocks utilizados:** [número]
- **Componentes simulados:**
  - `[RepositorioX]`
  - `[ServicioY]`

- **Comportamientos verificados:**
  - Invocaciones de métodos
  - Comportamiento bajo condiciones controladas

---

## 🌐 Simulaciones HTTP

- **Endpoint:** `POST /usuarios/crear`
  - **Resultado esperado:** `HTTP 201 Created`
  - **Validaciones:** datos obligatorios, formato correcto

- **Endpoint:** `GET /productos/{id}`
  - **Resultado esperado:** `HTTP 200 OK`
  - **Validaciones:** ID existente, estructura de respuesta

- **Herramienta utilizada:** `MockMvc`

---

## ⚠️ Fallos o Incidencias Detectadas

- **[Descripción breve del error 1]**
  - **Clase:** `[NombreClase]`
  - **Método:** `[nombreMetodo]`
  - **Análisis:** [posible causa / solución sugerida]

- **[Descripción breve del error 2]**

---

## ✅ Conclusión

> El conjunto de pruebas automatizadas cubre **[porcentaje]%** del código fuente generado. El sistema se comporta correctamente bajo los escenarios definidos. Se recomienda seguir ampliando la cobertura y revisar los módulos con bajo porcentaje.
```

---

## 📘 Plantilla base para el informe de resultados (a publicar en Jira)

*El contenido generado en `testresults.md`, y que será publicado como comentario en Jira, debe seguir la siguiente plantilla:*

```markdown
# Informe de Resultados de Pruebas Automatizadas

**Proyecto:** [Nombre del proyecto]
**Fecha de ejecución:** [dd/mm/aaaa]
**Entorno:** Visual Studio Code
**Comando utilizado:** `mvn clean test`

---

## 📊 Resumen General

- **Total de pruebas ejecutadas:** [número]
- **Pruebas exitosas:** [número]
- **Pruebas fallidas:** [número]
- **Pruebas con errores:** [número]

---

## 🔍 Cobertura de Código (Jacoco)

- **Cobertura total del proyecto:**
  - Por clases: [porcentaje]%
  - Por métodos: [porcentaje]%
  - Por líneas: [porcentaje]%

- **Clases con menor cobertura:**
  - `[NombreClase]`: [porcentaje]%
  - `[NombreClase]`: [porcentaje]%

- **Clases con cobertura completa:**
  - `[NombreClase]`
  - `[NombreClase]`

> **Ruta del informe HTML completo:** `/target/site/jacoco/index.html`

---

## 🧪 Detalles por Framework

### JUnit

- **Total de pruebas unitarias:** [número]
- **Clases probadas:**
  - `[NombreServicio]Test`
  - `[NombreControlador]Test`

- **Casos validados:**
  - Lógica de negocio
  - Validaciones de entrada
  - Cálculo de resultados esperados

---

### Mockito

- **Total de mocks utilizados:** [número]
- **Componentes simulados:**
  - `[RepositorioX]`
  - `[ServicioY]`

- **Comportamientos verificados:**
  - Invocaciones de métodos
  - Comportamiento bajo condiciones controladas

---

## 🌐 Simulaciones HTTP

- **Endpoint:** `POST /usuarios/crear`
  - **Resultado esperado:** `HTTP 201 Created`
  - **Validaciones:** datos obligatorios, formato correcto

- **Endpoint:** `GET /productos/{id}`
  - **Resultado esperado:** `HTTP 200 OK`
  - **Validaciones:** ID existente, estructura de respuesta

- **Herramienta utilizada:** `MockMvc`

---

## ⚠️ Fallos o Incidencias Detectadas

- **[Descripción breve del error 1]**
  - **Clase:** `[NombreClase]`
  - **Método:** `[nombreMetodo]`
  - **Análisis:** [posible causa / solución sugerida]

- **[Descripción breve del error 2]**

---

## ✅ Conclusión

> El conjunto de pruebas automatizadas cubre **[porcentaje]%** del código fuente generado. El sistema se comporta correctamente bajo los escenarios definidos. Se recomienda seguir ampliando la cobertura y revisar los módulos con bajo porcentaje.