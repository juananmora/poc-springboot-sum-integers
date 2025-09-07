# AGENTS.md - Configuración de Agentes de IA

## 🤖 ¿Qué es AGENTS.md?

Este archivo define diferentes **agentes especializados** de IA que pueden ayudarte con tareas específicas en tu proyecto Spring Boot Math Service. Cada agente tiene un rol definido y conocimiento especializado.

## 🎯 Agentes Disponibles

### 🏗️ **Arquitecto de Spring Boot**
```yaml
Nombre: SpringBootArchitect
Especialización: Diseño de arquitectura y mejores prácticas para operaciones matemáticas
Responsabilidades:
  - Revisar estructura de capas (MathController → MathService)
  - Validar patrones de diseño para servicios matemáticos
  - Sugerir mejoras arquitectónicas para escalabilidad
  - Optimizar configuraciones Spring Boot 3.3.5
Comandos clave:
  - "Revisa la arquitectura del MathController"
  - "¿Esta estructura sigue las mejores prácticas para servicios matemáticos?"
  - "¿Cómo mejorar la organización del package com.example.demo?"
```

### 🧪 **Especialista en Testing Matemático**
```yaml
Nombre: MathTestingExpert
Especialización: Pruebas unitarias para operaciones matemáticas y cobertura
Responsabilidades:
  - Generar tests unitarios para MathService (@Test, @MockBean)
  - Crear tests de integración para endpoints matemáticos (@SpringBootTest)
  - Analizar cobertura de código (JaCoCo >80%)
  - Optimizar estrategias de testing para casos edge matemáticos
Comandos clave:
  - "Genera tests para MathService.quicksort()"
  - "¿Cómo mejorar la cobertura en AdditionController?"
  - "Crea test de integración para el endpoint /sum"
```

### 🔍 **Auditor de Código Matemático**
```yaml
Nombre: MathCodeAuditor
Especialización: Calidad de código y validaciones numéricas
Responsabilidades:
  - Revisar validaciones de entrada para números
  - Detectar problemas de overflow/underflow
  - Analizar manejo de errores en operaciones matemáticas
  - Verificar cumplimiento de estándares Java 17
Comandos clave:
  - "Audita el método calculateMode en MathService"
  - "¿Hay problemas de overflow en las sumas?"
  - "Revisa las validaciones en MathController"
```

### 📊 **Analista de Performance Matemático**
```yaml
Nombre: MathPerformanceAnalyst
Especialización: Optimización de algoritmos matemáticos
Responsabilidades:
  - Analizar complejidad de algoritmos (quicksort, calculateMode)
  - Optimizar operaciones con listas grandes
  - Revisar uso de memoria en MathService
  - Detectar cuellos de botella en cálculos
Comandos clave:
  - "Optimiza el algoritmo quicksort"
  - "¿Hay problemas de rendimiento en calculateMean?"
  - "Revisa la eficiencia del método calculateMode"
```

### 🚀 **DevOps Engineer Matemático**
```yaml
Nombre: MathDevOpsEngineer
Especialización: CI/CD y despliegue de servicios matemáticos
Responsabilidades:
  - Configurar pipelines de GitHub Actions para tests matemáticos
  - Optimizar Dockerfiles para aplicaciones Java 17
  - Configurar profiles para diferentes entornos
  - Automatizar deployments con validación de precisión
Comandos clave:
  - "Mejora el pipeline CI/CD para tests de cobertura"
  - "Optimiza el Dockerfile para Spring Boot 3.3.5"
  - "Configura deployment con validación de precisión matemática"
```

### 📚 **Documentador de APIs Matemáticas**
```yaml
Nombre: MathApiDocumenter
Especialización: Documentación de endpoints y servicios matemáticos
Responsabilidades:
  - Generar JavaDoc para MathService y controladores
  - Crear documentación de API REST para operaciones matemáticas
  - Mantener README actualizado con ejemplos de uso
  - Documentar endpoints con casos de uso matemáticos
Comandos clave:
  - "Documenta la clase MathService"
  - "Genera documentación para endpoints de /math"
  - "Actualiza el README con ejemplos de operaciones"
```

## 🎮 Cómo Usar los Agentes

### **Sintaxis de Invocación**
```
@[NombreAgente] [Descripción de la tarea]
```

### **Ejemplos Prácticos**

#### 🏗️ Arquitectura
```
@SpringBootArchitect revisa la estructura del MathController y sugiere mejoras para escalabilidad
```

#### 🧪 Testing
```
@MathTestingExpert genera tests unitarios para MathService.calculateMode() con casos edge
```

#### 🔍 Auditoria
```
@MathCodeAuditor revisa las validaciones de entrada en los endpoints de suma
```

#### 📊 Performance
```
@MathPerformanceAnalyst optimiza el método quicksort para listas de más de 10,000 elementos
```

#### 🚀 DevOps
```
@MathDevOpsEngineer configura GitHub Actions para ejecutar tests de cobertura JaCoCo
```

#### 📚 Documentación
```
@MathApiDocumenter genera JavaDoc para todas las operaciones matemáticas en MathService
```

## 🔧 Configuración de Contexto

### **Información del Proyecto**
- **Framework**: Spring Boot 3.3.5
- **Java**: 17
- **Build Tool**: Maven
- **Testing**: JUnit 5 + Mockito
- **Coverage**: JaCoCo (objetivo >80%)
- **Arquitectura**: MathController → MathService
- **Principales Clases**:
  - `MathController`: Endpoints REST para operaciones matemáticas
  - `AdditionController`: Controlador específico para sumas
  - `MathService`: Lógica de negocio para operaciones matemáticas
  - `GlobalExceptionHandler`: Manejo global de errores

### **Operaciones Matemáticas Implementadas**
- Suma de dos números (`add`)
- Suma de listas (`sumList`)
- Raíz cuadrada (`sqrt`)
- Multiplicación (`multiply`)
- División (`divide`)
- Media aritmética (`calculateMean`)
- Moda estadística (`calculateMode`)
- Ordenamiento quicksort (`quicksort`)

### **Patrones Obligatorios**
- Uso de `long` para prevenir overflow en sumas
- Validaciones en múltiples capas (Controller + Service)
- Manejo consistente de errores matemáticos
- Tests con naming `shouldDoSomethingWhenCondition()`
- Cobertura de casos edge (números negativos, cero, listas vacías)

## 🎯 Flujo de Trabajo Recomendado

### **1. Desarrollo de Nueva Operación Matemática**
```
1. @SpringBootArchitect diseña la estructura del endpoint y servicio
2. Implementar código siguiendo patrones existentes
3. @MathTestingExpert genera tests completos con casos edge
4. @MathCodeAuditor revisa validaciones y manejo de errores
5. @MathPerformanceAnalyst optimiza algoritmos si es necesario
6. @MathApiDocumenter documenta la nueva operación
```

### **2. Refactoring de Algoritmos Matemáticos**
```
1. @MathCodeAuditor identifica áreas de mejora en algoritmos
2. @SpringBootArchitect sugiere nueva estructura
3. @MathPerformanceAnalyst valida optimizaciones de rendimiento
4. @MathTestingExpert actualiza tests para nueva implementación
```

### **3. Preparación para Producción**
```
1. @MathDevOpsEngineer configura pipeline con tests matemáticos
2. @MathCodeAuditor hace auditoría final de precisión
3. @MathApiDocumenter actualiza documentación completa
4. @MathTestingExpert verifica cobertura >80% en JaCoCo
```

## 💡 Tips de Uso

### **Combinación de Agentes**
```
@SpringBootArchitect + @MathTestingExpert: 
"Diseña la arquitectura para división segura y genera los tests correspondientes"
```

### **Contexto Específico**
```
@MathPerformanceAnalyst considerando que tendremos listas de hasta 1 millón de números:
"Optimiza el método calculateMean para manejo de memoria"
```

### **Revisión Integral**
```
@MathCodeAuditor + @MathPerformanceAnalyst + @MathTestingExpert:
"Haz una revisión completa del MathService enfocándote en precisión y rendimiento"
```

## 🔄 Actualización y Mantenimiento

Este archivo debe actualizarse cuando:
- Se agreguen nuevas operaciones matemáticas
- Cambien los algoritmos implementados
- Se modifiquen los patrones de testing
- Evolucionen los procesos de CI/CD
- Se identifiquen nuevos tipos de validaciones matemáticas

---

**📝 Nota**: Cada agente mantiene el contexto del proyecto definido en `.github/copilot-instructions.md` y sigue las convenciones establecidas para operaciones matemáticas seguras y eficientes.

### **Sintaxis de Invocación**
```
@[NombreAgente] [Descripción de la tarea]
```

### **Ejemplos Prácticos**

#### 🏗️ Arquitectura
```
@SpringBootArchitect revisa la estructura del CalculatorController y sugiere mejoras
```

#### 🧪 Testing
```
@TestingExpert genera tests unitarios para CalculatorService.multiply()
```

#### 🔍 Auditoria
```
@CodeAuditor revisa las validaciones de entrada en SumRequest
```

#### 📊 Performance
```
@PerformanceAnalyst optimiza el método de suma para listas grandes
```

#### 🚀 DevOps
```
@DevOpsEngineer configura GitHub Actions para deployment automático
```

#### 📚 Documentación
```
@TechnicalDocumenter genera JavaDoc para todas las clases del paquete service
```

## 🔧 Configuración de Contexto

### **Información del Proyecto**
- **Framework**: Spring Boot 3.2.0
- **Java**: 17
- **Build Tool**: Maven
- **Testing**: JUnit 5 + Mockito
- **Coverage**: JaCoCo (objetivo >90%)
- **Arquitectura**: Controller → Service → DTO

### **Patrones Obligatorios**
- Uso de `long` para prevenir overflow
- Validaciones en múltiples capas
- Manejo consistente de errores
- Tests con naming `shouldDoSomethingWhenCondition()`

## 🎯 Flujo de Trabajo Recomendado

### **1. Desarrollo de Nueva Funcionalidad**
```
1. @SpringBootArchitect diseña la estructura
2. Implementar código siguiendo el diseño
3. @TestingExpert genera tests completos
4. @CodeAuditor revisa calidad y seguridad
5. @PerformanceAnalyst optimiza si es necesario
6. @TechnicalDocumenter documenta la funcionalidad
```

### **2. Refactoring**
```
1. @CodeAuditor identifica áreas de mejora
2. @SpringBootArchitect sugiere nueva estructura
3. @PerformanceAnalyst valida optimizaciones
4. @TestingExpert actualiza tests
```

### **3. Preparación para Producción**
```
1. @DevOpsEngineer configura pipeline
2. @CodeAuditor hace auditoría final
3. @TechnicalDocumenter actualiza documentación
4. @TestingExpert verifica cobertura completa
```

## 💡 Tips de Uso

### **Combinación de Agentes**
```
@SpringBootArchitect + @TestingExpert: 
"Diseña la arquitectura para multiplicación y genera los tests correspondientes"
```

### **Contexto Específico**
```
@PerformanceAnalyst considerando que tendremos listas de hasta 1 millón de números:
"Optimiza el método de suma"
```

### **Revisión Integral**
```
@CodeAuditor + @PerformanceAnalyst + @TestingExpert:
"Haz una revisión completa del CalculatorService"
```

## 🔄 Actualización y Mantenimiento

Este archivo debe actualizarse cuando:
- Cambien los patrones del proyecto
- Se agreguen nuevas tecnologías
- Evolucionen los procesos de desarrollo
- Se identifiquen nuevos roles de agentes necesarios

---

**📝 Nota**: Cada agente mantiene el contexto del proyecto definido en `.github/copilot-instructions.md` y sigue las convenciones establecidas en `AGENT.md`.
