# 🧮 Spring Boot Math Service - Guía de Pruebas

Una aplicación Spring Boot que proporciona operaciones matemáticas básicas a través de endpoints REST, completamente probada con JUnit 5, Mockito y Jacoco para análisis de cobertura.

[![Java CI with Maven](https://github.com/accenture-amer-extending-copilot/poc-springboot-sum-integers/actions/workflows/maven.yml/badge.svg)](https://github.com/accenture-amer-extending-copilot/poc-springboot-sum-integers/actions/workflows/maven.yml)

## 📋 Contenido

- [Requisitos Previos](#-requisitos-previos)
- [Instalación](#-instalación)
- [Ejecución de Pruebas](#-ejecución-de-pruebas)
- [Tipos de Pruebas](#-tipos-de-pruebas)
- [Análisis de Cobertura](#-análisis-de-cobertura)
- [Endpoints Disponibles](#-endpoints-disponibles)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Resultados de Pruebas](#-resultados-de-pruebas)

## 🔧 Requisitos Previos

- **Java 11+** (Recomendado Java 17)
- **Maven 3.6+**
- **Git** (para clonar el repositorio)

## 📦 Instalación

1. **Clonar el repositorio** (si aplicable):
   ```bash
   git clone <repository-url>
   cd springboot-sum-integers
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
**Descripción:** Ejecuta todas las pruebas unitarias e de integración (46 pruebas total).

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

| Componente | Instrucciones | Ramas | Líneas | Métodos |
|------------|---------------|-------|--------|---------|
| **MathService** | 100% | 100% | 100% | 100% |
| **AdditionController** | 100% | N/A | 100% | 100% |
| **DemoApplication** | 37.5% | N/A | 33.3% | 50% |
| **TOTAL** | **95.1%** | **100%** | **92.9%** | **91.7%** |

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

## 📁 Estructura del Proyecto

```
springboot-sum-integers/
├── src/
│   ├── main/java/com/example/demo/
│   │   ├── DemoApplication.java          # Clase principal
│   │   ├── controller/
│   │   │   └── AdditionController.java   # Controlador REST
│   │   └── service/
│   │       └── MathService.java          # Lógica de negocio
│   └── test/java/com/example/demo/
│       ├── controller/
│       │   ├── AdditionControllerTest.java      # MockMvc tests
│       │   └── AdditionControllerUnitTest.java  # Unit tests
│       ├── integration/
│       │   └── AdditionIntegrationTest.java     # Integration tests
│       ├── service/
│       │   └── MathServiceTest.java             # Service tests
│       └── DemoApplicationTest.java             # Context tests
├── target/
│   ├── site/jacoco/                      # Reportes de cobertura
│   └── surefire-reports/                # Reportes de pruebas
├── pom.xml                               # Configuración Maven
├── README.md                             # Esta documentación
└── testresults.md                        # Informe completo de pruebas
```

## 📈 Resultados de Pruebas

### Resumen de Última Ejecución
- ✅ **Total de Pruebas:** 46
- ✅ **Pruebas Exitosas:** 46 (100%)
- ❌ **Pruebas Fallidas:** 0 (0%)
- ⏱️ **Tiempo Total:** ~15.2 segundos

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
