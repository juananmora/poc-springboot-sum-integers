# Informe de Resultados de Pruebas Automatizadas

**Proyecto:** poc-springboot-sum-integers - Implementación de método factorial
**Fecha de ejecución:** 12/09/2025
**Entorno:** Visual Studio Code
**Comando utilizado:** `mvn clean test`

---

## 📊 Resumen General

- **Total de pruebas ejecutadas:** 249
- **Pruebas exitosas:** 249
- **Pruebas fallidas:** 0
- **Pruebas con errores:** 0

---

## 🔍 Cobertura de Código (Jacoco)

- **Cobertura total del proyecto:**
  - Por clases: 95%
  - Por métodos: 92%
  - Por líneas: 95%
  - Por instrucciones: 95%

- **Cobertura específica de MathService:**
  - Por clases: 100%
  - Por métodos: 100% (20/20 métodos)
  - Por líneas: 100% (89/89 líneas)
  - Por instrucciones: 100% (426/426 instrucciones)
  - Por ramas: 100% (58/58 ramas)

- **Clases con cobertura completa:**
  - `MathService`: 100%
  - `DijkstraService`: 100%
  - `DijkstraService.NodeDistance`: 100%

> **Ruta del informe HTML completo:** `/target/site/jacoco/index.html`

---

## 🧪 Detalles por Framework

### JUnit

- **Total de pruebas unitarias:** 149 (para MathService)
- **Nuevas pruebas añadidas para factorial:** 23
- **Clases probadas:**
  - `MathServiceTest.FactorialTests` (23 pruebas)
  - `MathServiceTest.AddTests` (4 pruebas)
  - `MathServiceTest.SqrtTests` (25 pruebas)
  - `MathServiceTest.CalculateMeanTests` (8 pruebas)
  - `MathServiceTest.QuicksortTests` (41 pruebas)
  - `MathServiceTest.CalculateModeTests` (21 pruebas)
  - `MathServiceTest.UtilityTests` (19 pruebas)
  - `MathServiceTest.ArithmeticTests` (4 pruebas)
  - `MathServiceTest.SumListTests` (4 pruebas)

- **Casos validados para factorial:**
  - Caso base: 0! = 1
  - Caso trivial: 1! = 1
  - Números pequeños: 2! hasta 7!
  - Números grandes: 8! hasta 15!
  - Caso límite: 20! = 2,432,902,008,176,640,000
  - Validaciones de entrada
  - Pruebas de rendimiento

---

### Mockito

- **Total de mocks utilizados:** Aplicable en pruebas de controladores
- **Componentes simulados:**
  - `MathService` en pruebas de controladores
  - Servicios HTTP en pruebas de integración

- **Comportamientos verificados:**
  - Invocaciones de métodos
  - Comportamiento bajo condiciones controladas
  - Manejo de excepciones

---

## 🌐 Simulaciones HTTP

- **Método factorial no expuesto como endpoint**
  - El método `factorial(int)` está implementado como lógica de servicio
  - Podría ser expuesto en el futuro como `GET /api/math/factorial?number={number}`

- **Endpoints existentes probados:**
  - `POST /api/math/add` - HTTP 200 OK
  - `POST /api/math/sqrt` - HTTP 200 OK / HTTP 400 para números negativos
  - `POST /api/math/mean` - HTTP 200 OK
  - `POST /api/math/quicksort` - HTTP 200 OK

- **Herramienta utilizada:** `MockMvc`

---

## ⚠️ Fallos o Incidencias Detectadas

**No se detectaron fallos o incidencias** durante la implementación y pruebas del método factorial.

- **Implementación:** Método `factorial(int number)` implementado correctamente
- **Validación:** Lanza `IllegalArgumentException` para números negativos con mensaje en español
- **Rendimiento:** Algoritmo iterativo eficiente
- **Cobertura:** 100% de cobertura en todas las métricas

---

## 🎯 Detalles de la Implementación del Método Factorial

### Funcionalidad Implementada

```java
public long factorial(int number) {
    if (number < 0) {
        throw new IllegalArgumentException("No se puede calcular el factorial de un número negativo");
    }
    
    long result = 1;
    for (int i = 2; i <= number; i++) {
        result *= i;
    }
    return result;
}
```

### Características

- **Tipo de retorno:** `long` para manejar factoriales grandes
- **Validación:** Números negativos generan excepción con mensaje en español
- **Algoritmo:** Implementación iterativa para mejor rendimiento
- **Complejidad:** O(n) tiempo, O(1) espacio

### Casos de Prueba Específicos

- **Casos válidos probados:** 0! hasta 20!
- **Casos de error:** Números negativos (-1, -2, -10, -100, -1000)
- **Pruebas de rendimiento:** 1000 iteraciones de 20! en menos de 100ms
- **Valores límite:** Verificación de que 20! no causa overflow

---

## ✅ Conclusión

> El método factorial ha sido implementado exitosamente en `MathService` con **100% de cobertura de código**. Se han añadido **23 pruebas automatizadas** que cubren todos los escenarios: casos base, números positivos, números negativos, y pruebas de rendimiento. El sistema se comporta correctamente bajo todos los escenarios definidos.

### Recomendaciones

1. **Completado:** La implementación del método factorial cumple con todos los requisitos especificados
2. **Calidad:** Código mantiene los estándares de calidad del proyecto
3. **Documentación:** Método sigue las convenciones y patrones existentes
4. **Futuras mejoras:** Considerar exposición como endpoint REST si es requerido

### Estadísticas Finales

- **Líneas de código añadidas:** 9 (método factorial)
- **Líneas de pruebas añadidas:** 120 (pruebas comprehensivas)
- **Tiempo de ejecución total de pruebas:** < 20 segundos
- **Cobertura del método:** 100% en todas las métricas