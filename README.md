# 🧮 Spring Boot Math Service - Guía de Pruebas Unitarias

Una aplicación Spring Boot que proporciona operaciones matemáticas básicas y **algoritmos de grafos** a través de endpoints REST, completamente probada con JUnit 5, Mockito y Jacoco para análisis de cobertura.

[![Java CI with Maven](https://github.com/accenture-amer-extending-copilot/poc-springboot-sum-integers/actions/workflows/maven.yml/badge.svg)](https://github.com/accenture-amer-extending-copilot/poc-springboot-sum-integers/actions/workflows/maven.yml)

![Cobertura Jacoco](.github/badges/jacoco.svg)

## 📋 Contenido

- [Requisitos Previos](#-requisitos-previos)
- [Instalación](#-instalación)
- [Ejecución de Pruebas](#-ejecución-de-pruebas)
- [Tipos de Pruebas](#-tipos-de-pruebas)
- [Algoritmo de Dijkstra](#-algoritmo-de-dijkstra)
- [Análisis de Cobertura](#-análisis-de-cobertura)
- [Endpoints Disponibles](#-endpoints-disponibles)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Resultados de Pruebas](#-resultados-de-pruebas)

## 🔧 Requisitos Previos

- **Java 17+** (Recomendado Java 17 LTS)
- **Maven 3.6+**
- **Git** (para clonar el repositorio)

## 📦 Instalación

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/juananmora/poc-springboot-sum-integers.git
   cd poc-springboot-sum-integers
   ```

2. **Verificar la instalación de Java y Maven**:
   ```bash
   java -version
   mvn -version
   ```

## 🧪 Ejecución de Pruebas

### Comandos Principales

#### 1. Ejecutar Todas las Pruebas
```bash
mvn test
```
**Descripción:** Ejecuta todas las pruebas unitarias e de integración (327 pruebas total).

#### 2. Compilar y Ejecutar Pruebas con Reporte de Cobertura
```bash
mvn clean test
```
**Descripción:** Limpia el proyecto, compila el código y ejecuta todas las pruebas generando reportes de cobertura con Jacoco.

#### 3. Solo Compilar sin Ejecutar Pruebas
```bash
mvn compile test-compile
```
**Descripción:** Compila el código principal y las pruebas sin ejecutarlas.

#### 4. Ejecutar Pruebas con Información Detallada
```bash
mvn test -X
```
**Descripción:** Ejecuta las pruebas en modo verbose para debugging.

#### 5. Ejecutar Solo un Tipo Específico de Pruebas
```bash
# Solo pruebas unitarias del servicio
mvn test -Dtest=MathServiceTest

# Solo pruebas del controlador
mvn test -Dtest=AdditionController*

# Solo pruebas de integración
mvn test -Dtest=AdditionIntegrationTest
```

### Comandos Avanzados

#### Generar Solo Reporte de Cobertura (después de ejecutar pruebas)
```bash
mvn jacoco:report
```

#### Ejecutar Pruebas sin Maven (con JAR compilado)
```bash
# Primero compilar
mvn package -DskipTests

# Luego ejecutar la aplicación
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

#### Verificar Calidad del Código
```bash
mvn verify
```

## 🧪 Tipos de Pruebas

### 1. Pruebas Unitarias del Servicio (19 pruebas)
**Archivo:** `src/test/java/com/example/demo/service/MathServiceTest.java`

```bash
mvn test -Dtest=MathServiceTest
```

**Cobertura:**
- Pruebas parametrizadas para todas las operaciones matemáticas
- Validación de números negativos
- Validación de división por cero
- Casos extremos y valores límite

### 2. Pruebas Unitarias del Controlador (7 pruebas)
**Archivo:** `src/test/java/com/example/demo/controller/AdditionControllerUnitTest.java`

```bash
mvn test -Dtest=AdditionControllerUnitTest
```

**Características:**
- Uso de Mockito para mocking del servicio
- Verificación de interacciones
- Aislamiento completo de dependencias

### 3. Pruebas de Integración MockMvc (8 pruebas)
**Archivo:** `src/test/java/com/example/demo/controller/AdditionControllerTest.java`

```bash
mvn test -Dtest=AdditionControllerTest
```

**Características:**
- Contexto Spring completo sin servidor HTTP
- Validación de códigos de estado HTTP
- Verificación de parámetros de request

### 4. Pruebas de Integración Completa (8 pruebas)
**Archivo:** `src/test/java/com/example/demo/integration/AdditionIntegrationTest.java`

```bash
mvn test -Dtest=AdditionIntegrationTest
```

**Características:**
- Servidor embebido Tomcat
- TestRestTemplate para requests HTTP reales
- Pruebas end-to-end completas

### 5. Pruebas de Contexto Spring (4 pruebas)
**Archivo:** `src/test/java/com/example/demo/DemoApplicationTest.java`

```bash
mvn test -Dtest=DemoApplicationTest
```

**Características:**
- Verificación de carga del contexto
- Smoke tests de inicialización

### 6. Pruebas del Algoritmo de Dijkstra (18 pruebas)
**Archivos:** 
- `src/test/java/com/example/demo/service/DijkstraServiceTest.java`
- `src/test/java/com/example/demo/controller/DijkstraControllerTest.java`
- `src/test/java/com/example/demo/controller/DijkstraControllerIntegrationTest.java`

```bash
mvn test -Dtest=DijkstraServiceTest
mvn test -Dtest=DijkstraControllerTest
mvn test -Dtest=DijkstraControllerIntegrationTest
```

**Características:**
- Algoritmo de búsqueda de caminos más cortos
- Pruebas de grafos bidireccionales
- Validación de casos edge y rendimiento
- Endpoints REST para consulta de rutas

## 🗺️ Algoritmo de Dijkstra

### Funcionalidad
La aplicación incluye una implementación completa del **algoritmo de Dijkstra** para encontrar caminos más cortos en grafos ponderados.

### Endpoints Disponibles
```bash
# Camino más corto específico A → F
GET /api/dijkstra/path-a-to-f

# Camino más corto genérico
GET /api/dijkstra/shortest-path?source=A&destination=F
POST /api/dijkstra/shortest-path

# Obtener grafo por defecto
GET /api/dijkstra/default-graph
```

### Casos de Prueba Principales
- **A → F:** `A → C → B → D → E → F` (distancia: 12)
- **A → D:** `A → C → B → D` (distancia: 8)
- **B → F:** `B → D → E → F` (distancia: 9)

📋 **Ver análisis completo:** [testresults_paths.md](testresults_paths.md)

## 📊 Análisis de Cobertura

### Ver Reportes de Cobertura

1. **Ejecutar pruebas para generar cobertura:**
   ```bash
   mvn clean test
   ```

2. **Abrir reporte HTML:**
   ```bash
   # En Linux/Mac
   open target/site/jacoco/index.html
   
   # En Windows
   start target/site/jacoco/index.html
   
   # Con navegador específico
   firefox target/site/jacoco/index.html
   ```

3. **Ver datos en formato CSV:**
   ```bash
   cat target/site/jacoco/jacoco.csv
   ```

### Métricas de Cobertura Actuales

| Componente | Instrucciones | Ramas | Estado |
|------------|---------------|-------|--------|
| **com.example.demo.controller** | 69% | 90% | ✅ Bueno |
| **com.example.demo.service** | 84% | 94% | ✅ Excelente |
| **com.example.demo.dto** | 87% | 100% | ✅ Excelente |
| **com.example.demo.config** | 100% | N/A | ✅ Perfecto |
| **com.example.demo** (main) | 37% | N/A | ⚠️ Esperado (bootstrap) |
| **TOTAL** | **77%** | **94%** | ✅ Saludable |

## 🌐 Endpoints Disponibles

### Iniciar la Aplicación
```bash
mvn spring-boot:run
```
La aplicación se ejecutará en `http://localhost:8080`

### Probar Endpoints con curl

#### ✅ Operaciones Exitosas
```bash
# Suma
curl "http://localhost:8080/add?num1=5&num2=3"
# Respuesta: 8

# Multiplicación
curl "http://localhost:8080/multiply?num1=4&num2=6"
# Respuesta: 24

# División
curl "http://localhost:8080/divide?num1=15&num2=3"
# Respuesta: 5.0

# Resta
curl "http://localhost:8080/subtract?num1=10&num2=4"
# Respuesta: 6
```

#### ❌ Casos de Error (HTTP 400)
```bash
# Números negativos
curl -i "http://localhost:8080/add?num1=-5&num2=3"

# División por cero
curl -i "http://localhost:8080/divide?num1=10&num2=0"

# Parámetros faltantes
curl -i "http://localhost:8080/add?num1=5"
```

### 🗺️ Endpoints del Algoritmo de Dijkstra
```bash
# Camino más corto específico A → F
curl "http://localhost:8080/api/dijkstra/path-a-to-f"
# Respuesta: {"source":"A","destination":"F","path":["A","C","B","D","E","F"],"distance":12,"pathFound":true}

# Camino más corto genérico
curl "http://localhost:8080/api/dijkstra/shortest-path?source=A&destination=D"
# Respuesta: {"source":"A","destination":"D","path":["A","C","B","D"],"distance":8,"pathFound":true}

# Obtener estructura del grafo
curl "http://localhost:8080/api/dijkstra/default-graph"
# Respuesta: JSON con la estructura completa del grafo

# POST para caminos personalizados
curl -X POST "http://localhost:8080/api/dijkstra/shortest-path" \
     -H "Content-Type: application/json" \
     -d '{"source":"B","destination":"F"}'
# Respuesta: {"source":"B","destination":"F","path":["B","D","E","F"],"distance":9,"pathFound":true}
```

## 📁 Estructura del Proyecto

```
poc-springboot-sum-integers/
├── src/
│   ├── main/java/com/example/demo/
│   │   ├── DemoApplication.java              # Clase principal
│   │   ├── controller/
│   │   │   ├── AdditionController.java       # Controlador REST matemáticas
│   │   │   ├── DijkstraController.java       # Controlador REST Dijkstra
│   │   │   ├── MathController.java           # Controlador REST operaciones
│   │   │   └── GlobalExceptionHandler.java  # Manejo global de errores
│   │   ├── service/
│   │   │   ├── MathService.java              # Lógica matemática
│   │   │   └── DijkstraService.java          # Algoritmo de Dijkstra
│   │   └── dto/
│   │       ├── Graph.java                    # DTO para grafos
│   │       ├── GraphEdge.java                # DTO para aristas
│   │       └── DijkstraResult.java           # DTO para resultados
│   └── test/java/com/example/demo/
│       ├── controller/
│       │   ├── AdditionController*Test.java          # Tests controlador matemáticas
│       │   ├── DijkstraController*Test.java          # Tests controlador Dijkstra
│       │   ├── MathControllerTest.java               # Tests controlador operaciones
│       │   └── GlobalExceptionHandlerTest.java      # Tests manejo errores
│       ├── service/
│       │   ├── MathServiceTest.java                  # Tests servicio matemáticas
│       │   ├── DijkstraServiceTest.java              # Tests algoritmo Dijkstra
│       │   └── PathAnalysisTest.java                 # Análisis caminos óptimos
│       └── DemoApplicationTest.java                  # Tests contexto Spring
├── target/
│   ├── site/jacoco/                          # Reportes de cobertura
│   └── surefire-reports/                    # Reportes de pruebas
├── grafo.jpg                                 # Imagen del grafo analizado
├── testresults_paths.md                      # Análisis detallado de caminos
├── pom.xml                                   # Configuración Maven
├── README.md                                 # Esta documentación
└── testresults.md                            # Informe completo de pruebas
```

## 📈 Resultados de Pruebas

### Resumen de Última Ejecución
- ✅ **Total de Pruebas:** 327
- ✅ **Pruebas Exitosas:** 327 (100%)
- ❌ **Pruebas Fallidas:** 0
- ⏱️ **Tiempo Total:** ~38 segundos
- 🎯 **Cobertura:** 77% (instrucciones), 94% (ramas)

### Hitos Importantes
- ✅ **Algoritmo de Dijkstra** implementado y funcionando correctamente
- ✅ **Camino A→F = 12** (predicción correcta confirmada)
- ✅ **Grafos bidireccionales** correctamente implementados
- ✅ **Endpoints REST** funcionando para consultas de rutas
- ✅ **Todas las pruebas unitarias** pasando exitosamente

### Ver Reportes Detallados
```bash
# Ver resumen de todas las pruebas
find target/surefire-reports -name "*.txt" -exec cat {} \;

# Ver informe completo
cat testresults.md

# Ver reportes XML (para CI/CD)
ls target/surefire-reports/TEST-*.xml
```

## 🛠️ Comandos de Desarrollo

### Limpiar y Reconstruir
```bash
mvn clean compile
```

### Ejecutar Solo Compilación
```bash
mvn compile
```

### Verificar Dependencias
```bash
mvn dependency:tree
```

### Ejecutar en Modo de Desarrollo
```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```

## 🚀 CI/CD Integration

### Para Jenkins/GitHub Actions
```bash
mvn clean verify
```

### Para SonarQube
```bash
mvn clean verify sonar:sonar
```

### Generar Artefacto para Deployment
```bash
mvn clean package
```

## 🔍 Troubleshooting

### Problemas Comunes

1. **Error de compilación de Java:**
   ```bash
   mvn clean compile
   ```

2. **Fallo de pruebas por puerto ocupado:**
   ```bash
   lsof -ti:8080 | xargs kill -9
   mvn test
   ```

3. **Problemas de dependencias:**
   ```bash
   mvn dependency:purge-local-repository
   mvn clean install
   ```

4. **Limpiar cache de Maven:**
   ```bash
   mvn dependency:purge-local-repository
   ```

## 📝 Notas Importantes

- Las pruebas de integración pueden tomar más tiempo debido al servidor embebido
- El puerto 8080 debe estar libre para las pruebas de integración completa
- Los reportes de Jacoco se generan automáticamente con `mvn test`
- Para ver cobertura en tiempo real, usar IDEs como IntelliJ IDEA o VSCode con extensiones apropiadas

## 📞 Contacto y Contribuciones

Para reportar problemas o contribuir al proyecto:
1. Ejecutar todas las pruebas antes de hacer cambios
2. Mantener la cobertura de código por encima del 90%
3. Añadir pruebas para nueva funcionalidad
4. Seguir las convenciones de naming establecidas

---

**¡Feliz Testing! 🧪✅**
