# GitHub Copilot Instructions

## Custom Actions

Process a multi-step request involving interactions with GitHub and Jira, providing information and performing actions as specified in each step.

You will process the request step by step, completing the action for each step before moving to the next.

# Steps

1.  List all the issues (opened or closed) for the GitHub repository `poc-springboot-sum-integers` within the organization `accenture-amer-extending-copilot`. Please use tools from Github MCP server installed.
2.  List the 10 oldest issues for the Jira project `JON`.
3.  Retrieve and provide important data for the Jira issue `JON-3292`. 
4.  Create a new issue in the GitHub repository `poc-springboot-sum-integers` (organization `accenture-amer-extending-copilot`). The description of this new GitHub issue must include:
    *   The complete description of the test cases from the Jira issue `JON-3292`.
    *   Include issue jira `JON-3292` to create a comment in which the results of the unit test.
    *   Assing issue to the user Copilot with tool assign_copilot_to_issue.

# FORMATO OBLIGATORIO DE RESPUESTA

Tu respuesta DEBE seguir EXACTAMENTE este formato:

## Step 1: List GitHub issues for repository poc-functional-test-playwright in organization accenture-amer-extending-copilot
[Aquí el listado completo con número, título, estado y fecha de cada issue]

## Step 2: List the 10 oldest Jira issues for project JON  
[Aquí el listado numerado de las 10 issues más antiguas con ID, título y fecha]

## Step 3: Provide details for Jira issue JON-3292, including related issues
[Aquí los detalles completos de JON-3292 y sus issues relacionadas]

## Step 4: Create a new GitHub issue confirmation
[Aquí la confirmación de creación con URL y descripción del contenido]

# REGLAS OBLIGATORIAS:

- NO summarices ni abrevies los resultados
- NO ejecutes todos los pasos de una vez sin mostrar los resultados intermedios
- MUESTRA el listado completo de cada paso antes de continuar al siguiente
- USA EXACTAMENTE los títulos "Step 1:", "Step 2:", etc.
- NO combines pasos en una sola respuesta
- EJECUTA los tools y MUESTRA los resultados inmediatamente

# DATOS ESPECÍFICOS REQUERIDOS:

## Para Step 1 (GitHub issues):
- Número de issue (#X)
- Título completo
- Estado (ABIERTA/CERRADA)
- Fecha de creación
- Autor

## Para Step 2 (Jira oldest issues):
- ID de issue (JON-X)
- Título
- Fecha de creación
- Estado actual

## Para Step 3 (JON-6 details):
- Todos los metadatos de la issue
- Descripción completa del TC-003
- Lista completa de issues relacionadas con sus IDs

# ORDEN DE EJECUCIÓN OBLIGATORIO:

1. EJECUTA el tool para Step 1
2. MUESTRA los resultados completos del Step 1
3. EJECUTA el tool para Step 2  
4. MUESTRA los resultados completos del Step 2
5. EJECUTA el tool para Step 3
6. MUESTRA los resultados completos del Step 3
7. EJECUTA el tool para Step 4
8. CONFIRMA la creación de la issue

NO EJECUTES MÚLTIPLES STEPS SIN MOSTRAR RESULTADOS INTERMEDIOS

# VERIFICACIÓN OBLIGATORIA:

Antes de dar por completada la tarea, confirma que:
- [ ] Has listado TODAS las issues de GitHub (no solo un resumen)
- [ ] Has listado las 10 issues más antiguas de Jira (con fechas específicas)
- [ ] Has proporcionado detalles COMPLETOS de JON-6
- [ ] Has creado la nueva issue de GitHub con ambos contenidos requeridos

# Examples

```
Step 1: List GitHub issues for repository bbva-copilot-playwright in organization BBVA-test.

1. #10 - "Título completo aquí" (ABIERTA) - Creada: DD/MM/YYYY por usuario
2. #9 - "Título completo aquí" (ABIERTA) - Creada: DD/MM/YYYY por usuario
[... todas las issues listadas individualmente]

Step 2: List the 10 oldest Jira issues for project JON.

1. JON-4 - "Título" - Creada: DD/MM/YYYY - Estado: Done
2. JON-12 - "Título" - Creada: DD/MM/YYYY - Estado: Done
[... las 10 issues más antiguas]

Step 3: Provide details for Jira issue JON-3292, including related issues.
[Details for JON-3292, including summary, status, description (if available), and related issues]

Step 4: Create a new GitHub issue in poc-functional-test-playwright with description from JON-6 (TC-003) and prompt.md content.
[Confirmation that the GitHub issue was created, stating it includes the description of TC-003 from JON-6 and the content of prompt.md]
```

