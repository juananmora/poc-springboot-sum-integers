# 📡 API Documentation

Documentación completa de los endpoints REST disponibles en Spring Boot Math Service.

## 🔗 Base URL

```
http://localhost:8080
```

## 📋 Índice

- [Operaciones Matemáticas Básicas](#operaciones-matemáticas-básicas)
- [Operaciones Matemáticas Avanzadas](#operaciones-matemáticas-avanzadas)
- [Algoritmo de Dijkstra](#algoritmo-de-dijkstra)
- [Autenticación](#autenticación)
- [Códigos de Estado HTTP](#códigos-de-estado-http)
- [Manejo de Errores](#manejo-de-errores)

---

## 🧮 Operaciones Matemáticas Básicas

Endpoints GET para operaciones matemáticas simples.

### Sumar Dos Números

Suma dos números enteros no negativos.

**Endpoint:** `GET /add`

**Parámetros de Query:**
- `num1` (int, requerido): Primer número (>= 0)
- `num2` (int, requerido): Segundo número (>= 0)

**Respuesta Exitosa (200 OK):**
```json
8
```

**Ejemplo:**
```bash
curl "http://localhost:8080/add?num1=5&num2=3"
```

**Errores:**
- `400 Bad Request`: Números negativos o parámetros faltantes

---

### Multiplicar Dos Números

Multiplica dos números enteros.

**Endpoint:** `GET /multiply`

**Parámetros de Query:**
- `num1` (int, requerido): Primer número
- `num2` (int, requerido): Segundo número

**Respuesta Exitosa (200 OK):**
```json
24
```

**Ejemplo:**
```bash
curl "http://localhost:8080/multiply?num1=4&num2=6"
```

---

### Dividir Dos Números

Divide dos números enteros, retornando resultado decimal.

**Endpoint:** `GET /divide`

**Parámetros de Query:**
- `num1` (int, requerido): Dividendo
- `num2` (int, requerido): Divisor (≠ 0)

**Respuesta Exitosa (200 OK):**
```json
5.0
```

**Ejemplo:**
```bash
curl "http://localhost:8080/divide?num1=15&num2=3"
```

**Errores:**
- `400 Bad Request`: División por cero

---

### Restar Dos Números

Resta dos números enteros.

**Endpoint:** `GET /subtract`

**Parámetros de Query:**
- `num1` (int, requerido): Minuendo
- `num2` (int, requerido): Sustraendo

**Respuesta Exitosa (200 OK):**
```json
6
```

**Ejemplo:**
```bash
curl "http://localhost:8080/subtract?num1=10&num2=4"
```

---

### Raíz Cuadrada

Calcula la raíz cuadrada de un número.

**Endpoint:** `GET /sqrt`

**Parámetros de Query:**
- `number` (double, requerido): Número (>= 0)

**Respuesta Exitosa (200 OK):**
```json
3.0
```

**Ejemplo:**
```bash
curl "http://localhost:8080/sqrt?number=9"
```

**Errores:**
- `400 Bad Request`: Número negativo

---

## 🔢 Operaciones Matemáticas Avanzadas

Endpoints POST para operaciones más complejas. Base URL: `/api/math`

### Sumar Dos Números

**Endpoint:** `POST /api/math/sum`

**Request Body:**
```json
{
  "a": 5,
  "b": 3
}
```

**Respuesta Exitosa (200 OK):**
```json
{
  "result": 8,
  "operation": "sum",
  "operands": [5, 3]
}
```

**Ejemplo:**
```bash
curl -X POST "http://localhost:8080/api/math/sum" \
     -H "Content-Type: application/json" \
     -d '{"a":5,"b":3}'
```

---

### Sumar Lista de Números

**Endpoint:** `POST /api/math/sum-list`

**Request Body:**
```json
{
  "numbers": [1, 2, 3, 4, 5]
}
```

**Respuesta Exitosa (200 OK):**
```json
{
  "result": 15,
  "operation": "sum-list",
  "operands": [1, 2, 3, 4, 5]
}
```

**Ejemplo:**
```bash
curl -X POST "http://localhost:8080/api/math/sum-list" \
     -H "Content-Type: application/json" \
     -d '{"numbers":[1,2,3,4,5]}'
```

---

### Verificar Propiedades de Número

**Endpoint:** `GET /api/math/check/{number}`

**Parámetros de Path:**
- `number` (int, requerido): Número a verificar

**Respuesta Exitosa (200 OK):**
```json
{
  "number": 4,
  "isEven": true,
  "isPositive": true
}
```

**Ejemplo:**
```bash
curl "http://localhost:8080/api/math/check/4"
```

---

### Calcular Media

**Endpoint:** `POST /api/math/mean`

**Request Body:**
```json
{
  "numbers": [1, 2, 3, 4, 5]
}
```

**Respuesta Exitosa (200 OK):**
```json
{
  "result": 3.0,
  "operation": "mean",
  "operands": [1, 2, 3, 4, 5]
}
```

---

### Ordenar con Quicksort (Enteros)

**Endpoint:** `POST /api/math/quicksort`

**Request Body:**
```json
{
  "numbers": [5, 2, 8, 1, 9]
}
```

**Respuesta Exitosa (200 OK):**
```json
{
  "result": [1, 2, 5, 8, 9],
  "operation": "quicksort",
  "operands": [5, 2, 8, 1, 9]
}
```

---

### Ordenar con Quicksort (Decimales)

**Endpoint:** `POST /api/math/quicksort-rational`

**Request Body:**
```json
{
  "numbers": [5.5, 2.1, 8.9, 1.3]
}
```

**Respuesta Exitosa (200 OK):**
```json
{
  "result": [1.3, 2.1, 5.5, 8.9],
  "operation": "quicksort-rational",
  "operands": [5.5, 2.1, 8.9, 1.3]
}
```

---

### Calcular Moda

**Endpoint:** `POST /api/math/mode`

**Request Body:**
```json
{
  "numbers": [1, 2, 2, 3, 3, 3, 4]
}
```

**Respuesta Exitosa (200 OK):**
```json
{
  "result": [3],
  "operation": "mode",
  "operands": [1, 2, 2, 3, 3, 3, 4]
}
```

---

### Calcular Factorial

**Endpoint:** `POST /api/math/factorial`

**Request Body:**
```json
{
  "number": 5
}
```

**Respuesta Exitosa (200 OK):**
```json
{
  "result": 120,
  "operation": "factorial",
  "operand": 5
}
```

**Restricciones:**
- Número debe ser >= 0
- Número debe ser <= 20 (para evitar overflow)

**Errores:**
- `400 Bad Request`: Número negativo o mayor a 20

**Ejemplo:**
```bash
curl -X POST "http://localhost:8080/api/math/factorial" \
     -H "Content-Type: application/json" \
     -d '{"number":5}'
```

---

## 🗺️ Algoritmo de Dijkstra

Endpoints para encontrar caminos más cortos en grafos. Base URL: `/api/dijkstra`

### Obtener Grafo por Defecto

Retorna la estructura del grafo predefinido usado en las pruebas.

**Endpoint:** `GET /api/dijkstra/default-graph`

**Respuesta Exitosa (200 OK):**
```json
{
  "adjacencyList": {
    "A": [
      {"to": "B", "weight": 4},
      {"to": "C", "weight": 2}
    ],
    "B": [
      {"to": "A", "weight": 4},
      {"to": "C", "weight": 1},
      {"to": "D", "weight": 5}
    ],
    "C": [
      {"to": "A", "weight": 2},
      {"to": "B", "weight": 1},
      {"to": "D", "weight": 8},
      {"to": "E", "weight": 10}
    ],
    "D": [
      {"to": "B", "weight": 5},
      {"to": "C", "weight": 8},
      {"to": "E", "weight": 2},
      {"to": "F", "weight": 6}
    ],
    "E": [
      {"to": "C", "weight": 10},
      {"to": "D", "weight": 2},
      {"to": "F", "weight": 3}
    ],
    "F": [
      {"to": "D", "weight": 6},
      {"to": "E", "weight": 3}
    ]
  }
}
```

**Ejemplo:**
```bash
curl "http://localhost:8080/api/dijkstra/default-graph"
```

---

### Camino Más Corto (GET)

Encuentra el camino más corto entre dos nodos usando el grafo predefinido.

**Endpoint:** `GET /api/dijkstra/shortest-path`

**Parámetros de Query:**
- `source` (string, requerido): Nodo origen
- `destination` (string, requerido): Nodo destino

**Respuesta Exitosa (200 OK):**
```json
{
  "source": "A",
  "destination": "F",
  "path": ["A", "C", "B", "D", "E", "F"],
  "distance": 12,
  "pathFound": true
}
```

**Ejemplo:**
```bash
curl "http://localhost:8080/api/dijkstra/shortest-path?source=A&destination=F"
```

**Errores:**
- `400 Bad Request`: Nodo no existe en el grafo

---

### Camino Más Corto (POST con Grafo Personalizado)

Encuentra el camino más corto en un grafo personalizado.

**Endpoint:** `POST /api/dijkstra/shortest-path`

**Parámetros de Query:**
- `source` (string, requerido): Nodo origen
- `destination` (string, requerido): Nodo destino

**Request Body:**
```json
{
  "adjacencyList": {
    "A": [
      {"to": "B", "weight": 1},
      {"to": "C", "weight": 4}
    ],
    "B": [
      {"to": "A", "weight": 1},
      {"to": "C", "weight": 2},
      {"to": "D", "weight": 5}
    ],
    "C": [
      {"to": "A", "weight": 4},
      {"to": "B", "weight": 2},
      {"to": "D", "weight": 1}
    ],
    "D": [
      {"to": "B", "weight": 5},
      {"to": "C", "weight": 1}
    ]
  }
}
```

**Respuesta Exitosa (200 OK):**
```json
{
  "source": "A",
  "destination": "D",
  "path": ["A", "B", "C", "D"],
  "distance": 4,
  "pathFound": true
}
```

**Ejemplo:**
```bash
curl -X POST "http://localhost:8080/api/dijkstra/shortest-path?source=A&destination=D" \
     -H "Content-Type: application/json" \
     -d '{"adjacencyList":{"A":[{"to":"B","weight":1}],"B":[{"to":"C","weight":2}],"C":[{"to":"D","weight":1}],"D":[]}}'
```

---

### Camino A → F Específico

Endpoint de conveniencia para obtener el camino de A a F.

**Endpoint:** `GET /api/dijkstra/path-a-to-f`

**Respuesta Exitosa (200 OK):**
```json
{
  "source": "A",
  "destination": "F",
  "path": ["A", "C", "B", "D", "E", "F"],
  "distance": 12,
  "pathFound": true
}
```

**Ejemplo:**
```bash
curl "http://localhost:8080/api/dijkstra/path-a-to-f"
```

---

## 🔐 Autenticación

Endpoints para gestión de usuarios y autenticación JWT. Base URL: `/api/auth`

### Registro de Usuario

Crea un nuevo usuario en el sistema.

**Endpoint:** `POST /api/auth/register`

**Request Body:**
```json
{
  "username": "newuser",
  "password": "SecurePass123!",
  "email": "user@example.com"
}
```

**Respuesta Exitosa (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJuZXd1c2VyIiwiaWF0IjoxNjE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
  "username": "newuser",
  "email": "user@example.com"
}
```

**Validaciones:**
- **Username:** 
  - Mínimo 3 caracteres
  - Solo alfanuméricos y guiones (a-z, A-Z, 0-9, -, _)
  - Único en el sistema
- **Password:**
  - Mínimo 8 caracteres
  - Debe incluir mayúsculas, minúsculas y números
  - Se almacena encriptada con BCrypt
- **Email:**
  - Formato válido
  - Único en el sistema

**Errores:**
```json
{
  "error": "Username no cumple con la política de seguridad"
}
```

**Ejemplo:**
```bash
curl -X POST "http://localhost:8080/api/auth/register" \
     -H "Content-Type: application/json" \
     -d '{
       "username": "johndoe",
       "password": "MySecure123!",
       "email": "john@example.com"
     }'
```

---

### Login

Autentica un usuario y retorna un token JWT.

**Endpoint:** `POST /api/auth/login`

**Request Body:**
```json
{
  "username": "admin",
  "password": "admin123"
}
```

**Respuesta Exitosa (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYxNjIzOTAyMn0.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
  "username": "admin",
  "email": "admin@example.com"
}
```

**Errores:**
- `400 Bad Request`: Credenciales inválidas o campos faltantes
- `401 Unauthorized`: Usuario o contraseña incorrectos

**Ejemplo:**
```bash
curl -X POST "http://localhost:8080/api/auth/login" \
     -H "Content-Type: application/json" \
     -d '{
       "username": "admin",
       "password": "admin123"
     }'
```

---

### Usuarios por Defecto

La aplicación viene con 3 usuarios pre-configurados para testing:

| Username | Password | Role | Email |
|----------|----------|------|-------|
| admin | admin123 | ADMIN | admin@example.com |
| user | user123 | USER | user@example.com |
| testuser | test123 | USER | testuser@example.com |

---

## 📊 Códigos de Estado HTTP

| Código | Significado | Uso |
|--------|-------------|-----|
| 200 OK | Operación exitosa | Todas las operaciones exitosas |
| 400 Bad Request | Petición inválida | Parámetros inválidos, validación fallida |
| 401 Unauthorized | No autenticado | Credenciales inválidas |
| 403 Forbidden | No autorizado | Falta de permisos |
| 404 Not Found | Recurso no encontrado | Endpoint no existe |
| 500 Internal Server Error | Error del servidor | Error inesperado del servidor |

---

## ⚠️ Manejo de Errores

Todos los errores retornan un objeto JSON estructurado:

### Formato de Error

```json
{
  "error": "Descripción del error"
}
```

### Ejemplos de Errores

**Parámetros faltantes:**
```json
{
  "error": "Invalid input"
}
```

**Validación fallida:**
```json
{
  "error": "Los números no pueden ser negativos"
}
```

**División por cero:**
```json
{
  "error": "No se puede dividir por cero"
}
```

**Nodo no encontrado (Dijkstra):**
```json
{
  "source": "A",
  "destination": "Z",
  "path": null,
  "distance": -1,
  "pathFound": false
}
```

**Usuario duplicado:**
```json
{
  "error": "Username already exists"
}
```

---

## 🔧 Testing de API

### Usando curl

Todos los ejemplos de este documento usan `curl`. Asegúrate de tener la aplicación corriendo:

```bash
mvn spring-boot:run
```

### Usando Postman

1. Importa la colección desde: [Postman Collection](TODO: añadir link)
2. Configura la variable `baseUrl` a `http://localhost:8080`
3. Ejecuta las peticiones

### Usando HTTPie

```bash
# GET request
http GET localhost:8080/add num1==5 num2==3

# POST request
http POST localhost:8080/api/math/sum a:=5 b:=3
```

---

## 📝 Notas Adicionales

### Content-Type

Todos los endpoints POST requieren:
```
Content-Type: application/json
```

### Formato de Respuesta

Todas las respuestas son en formato JSON con encoding UTF-8.

### Rate Limiting

Actualmente no hay rate limiting implementado.

### Versionamiento

La API no está versionada actualmente. Se mantiene retrocompatibilidad en cambios.

---

## 🔗 Referencias

- [README.md](README.md) - Guía general del proyecto
- [CONTRIBUTING.md](CONTRIBUTING.md) - Guía de contribución
- [Javadoc](TODO: link a Javadoc generado) - Documentación de código

---

**Última actualización:** 2025-11-21
