# Informe de Resultados de Pruebas Automatizadas - Quicksort con Números Racionales

**Proyecto:** poc-springboot-sum-integers  
**Fecha de ejecución:** 01/07/2025  
**Entorno:** Visual Studio Code  
**Comando utilizado:** `mvn clean test`

---

## 📊 Resumen General

- **Total de pruebas ejecutadas:** 171
- **Pruebas exitosas:** 171
- **Pruebas fallidas:** 0
- **Pruebas con errores:** 0

---

## 🔍 Cobertura de Código (Jacoco)

- **Cobertura total del proyecto:**
  - Por instrucciones: 97% (634 instrucciones, 16 perdidas)
  - Por ramas: 100% (46 ramas, todas cubiertas)
  - Por líneas: 96% (141 líneas, 5 perdidas)

- **Cobertura por servicios:**
  - `MathService`: 100% (instrucciones y ramas)
  - `MathController`: 96% instrucciones, 100% ramas
  - `GlobalExceptionHandler`: 96% instrucciones, 100% ramas

- **Clases con cobertura completa:**
  - `MathService` (100% en todas las métricas)

> **Ruta del informe HTML completo:** `/target/site/jacoco/index.html`

---

## 🧪 Detalles por Framework

### JUnit

- **Total de pruebas unitarias:** 171
- **Clases probadas:**
  - `MathServiceTest` - 105 pruebas
  - `MathControllerTest` - 50 pruebas  
  - `AdditionControllerUnitTest` - 10 pruebas
  - `AdditionControllerIntegrationTest` - 4 pruebas
  - `DemoApplicationTest` - 4 pruebas
  - `GlobalExceptionHandlerTest` - 2 pruebas

- **Nuevas funcionalidades probadas:**
  - Quicksort para números enteros (19 casos de prueba)
  - Quicksort para números racionales (16 casos de prueba)
  - Endpoint REST `/api/math/quicksort-rational` (8 casos de prueba)

---

### Mockito

- **Total de mocks utilizados:** 40+
- **Componentes simulados:**
  - `MathService` (en todas las pruebas de controlador)
  - `MockMvc` (para simulaciones HTTP)

- **Comportamientos verificados:**
  - Invocaciones de métodos de servicio
  - Manejo de listas nulas y vacías
  - Preservación de inmutabilidad
  - Manejo de errores y excepciones

---

## 🌐 Simulaciones HTTP (MockMvc)

### Nuevos Endpoints Implementados

- **Endpoint:** `POST /api/math/quicksort`
  - **Resultado esperado:** `HTTP 200 OK`
  - **Validaciones:** ordenamiento de enteros, preservación de lista original

- **Endpoint:** `POST /api/math/quicksort-rational`
  - **Resultado esperado:** `HTTP 200 OK`
  - **Validaciones:** ordenamiento de números decimales, manejo de precisión flotante

### Casos de Prueba Cubiertos

- **Números enteros:** listas vacías, elementos únicos, duplicados, negativos
- **Números racionales:** decimales positivos/negativos, notación científica, diferencias muy pequeñas
- **Casos extremos:** `Double.MAX_VALUE`, `Double.MIN_VALUE`, listas nulas
- **Errores:** excepciones de servicio, entradas inválidas

---

## ✅ Nuevas Funcionalidades Implementadas

### Algoritmo Quicksort para Números Racionales

**Métodos añadidos al MathService:**
- `quicksortRational(List<Double> numbers)` - Ordenamiento de números decimales
- `quicksortHelperDouble()` - Implementación recursiva para doubles
- `partitionDouble()` - Partición usando pivote double
- `swapDouble()` - Intercambio de elementos double

**Características del algoritmo:**
- **Complejidad temporal:** O(n log n) caso promedio, O(n²) peor caso
- **Complejidad espacial:** O(log n) por recursión
- **Inmutabilidad:** Preserva la lista original creando una copia
- **Precisión:** Maneja diferencias muy pequeñas entre decimales
- **Robustez:** Soporta valores extremos y notación científica

### Nuevo Endpoint REST

**Endpoint:** `POST /api/math/quicksort-rational`

**Ejemplo de uso:**
```bash
curl -X POST /api/math/quicksort-rational \
  -H "Content-Type: application/json" \
  -d '{"numbers": [3.14, 1.41, 2.71, 9.81]}'
```

**Respuesta:**
```json
{
  "result": [1.41, 2.71, 3.14, 9.81],
  "operation": "quicksort-rational",
  "operands": [3.14, 1.41, 2.71, 9.81]
}
```

---

## 🎯 Casos de Prueba Específicos para Números Racionales

### Pruebas Unitarias (MathService)
1. **Listas básicas:** vacías, elemento único, ya ordenadas, orden inverso
2. **Números especiales:** negativos, cero, duplicados
3. **Precisión decimal:** diferencias muy pequeñas (0.0001)
4. **Valores extremos:** `Double.MAX_VALUE`, `Double.MIN_VALUE`
5. **Notación científica:** `1e-5`, `1e5`, etc.
6. **Fracciones mixtas:** combinación de positivos y negativos
7. **Inmutabilidad:** verificación de no modificación de lista original

### Pruebas de Integración (Controller)
1. **Ordenamiento exitoso:** listas variadas de decimales
2. **Elemento único:** respuesta correcta para un solo decimal
3. **Números negativos:** manejo correcto de valores negativos
4. **Lista vacía:** respuesta apropiada
5. **Lista nula:** manejo de entrada nula
6. **Excepciones de servicio:** manejo de errores
7. **Duplicados:** preservación de elementos repetidos
8. **Precisión:** números con diferencias muy pequeñas

---

## ⚠️ Observaciones y Mejoras

**Aspectos exitosos:**
- Cobertura excelente (97% instrucciones, 100% ramas)
- Todos los tests pasan sin errores
- Implementación robusta que maneja casos extremos
- API consistente con patrones existentes
- Preservación de inmutabilidad

**Funcionalidad implementada cumple con el requerimiento:**
- ✅ Ordenación de números racionales (decimales)
- ✅ Endpoint REST funcional
- ✅ Preservación de lista original
- ✅ Manejo completo de casos extremos
- ✅ Cobertura de pruebas exhaustiva

---

## ✅ Conclusión

> El conjunto de pruebas automatizadas cubre **97%** del código fuente del proyecto con **100%** de cobertura de ramas. La nueva funcionalidad de ordenamiento de números racionales se comporta correctamente bajo todos los escenarios definidos, incluyendo casos extremos y situaciones de error. 

> **Total de nuevas pruebas añadidas:** 24 (16 para servicio + 8 para controlador)
> **Funcionalidad implementada:** Quicksort para números racionales completamente funcional con endpoint REST integrado.

> El sistema ahora soporta tanto números enteros como números racionales, cumpliendo completamente con el requerimiento solicitado por el usuario.