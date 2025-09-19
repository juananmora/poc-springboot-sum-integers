import { test, expect } from '@playwright/test';

/**
 * Test Case: JUAN-3 - TODO List Task Ordering Verification
 * 
 * Objetivo: Verificar que los elementos de la lista de tareas se ordenan correctamente 
 * (nuevos elementos aparecen al final).
 * 
 * Based on Jira issue JUAN-3 requirements
 */
test.describe('TODO List Task Ordering - JUAN-3', () => {
  
  test('should verify that new tasks are added at the end of the list in correct order', async ({ page }) => {
    // Step 1: Acceder a la aplicación TODO
    await test.step('Navigate to TODO application', async () => {
      await page.goto('/todomvc/');
      await expect(page).toHaveTitle(/TodoMVC/);
    });

    // Verificar precondiciones: la aplicación está cargada
    await test.step('Verify application is loaded', async () => {
      const todoInput = page.locator('.new-todo');
      await expect(todoInput).toBeVisible();
      await expect(todoInput).toHaveAttribute('placeholder', 'What needs to be done?');
    });

    // Step 2: Crear una primera tarea llamada "Primera tarea"
    await test.step('Create first task: "Primera tarea"', async () => {
      const todoInput = page.locator('.new-todo');
      await todoInput.fill('Primera tarea');
      await todoInput.press('Enter');
      
      // Verificar que la tarea se añadió
      const todoItems = page.locator('.todo-list li');
      await expect(todoItems).toHaveCount(1);
      await expect(todoItems.first().locator('label')).toHaveText('Primera tarea');
    });

    // Step 3: Crear una segunda tarea llamada "Segunda tarea"
    await test.step('Create second task: "Segunda tarea"', async () => {
      const todoInput = page.locator('.new-todo');
      await todoInput.fill('Segunda tarea');
      await todoInput.press('Enter');
      
      // Verificar que ahora hay 2 tareas
      const todoItems = page.locator('.todo-list li');
      await expect(todoItems).toHaveCount(2);
    });

    // Step 4: Crear una tercera tarea llamada "Tercera tarea"
    await test.step('Create third task: "Tercera tarea"', async () => {
      const todoInput = page.locator('.new-todo');
      await todoInput.fill('Tercera tarea');
      await todoInput.press('Enter');
      
      // Verificar que ahora hay 3 tareas
      const todoItems = page.locator('.todo-list li');
      await expect(todoItems).toHaveCount(3);
    });

    // Step 5: Verificar que las tareas aparecen en el orden correcto (Primera, Segunda, Tercera)
    await test.step('Verify tasks appear in correct order (Primera, Segunda, Tercera)', async () => {
      const todoItems = page.locator('.todo-list li');
      
      // Verificar orden específico
      await expect(todoItems.nth(0).locator('label')).toHaveText('Primera tarea');
      await expect(todoItems.nth(1).locator('label')).toHaveText('Segunda tarea');
      await expect(todoItems.nth(2).locator('label')).toHaveText('Tercera tarea');
    });

    // Step 6: Verificar que cada nueva tarea se añade al final de la lista
    await test.step('Verify each new task is added at the end of the list', async () => {
      // Añadir una cuarta tarea para confirmar que se añade al final
      const todoInput = page.locator('.new-todo');
      await todoInput.fill('Cuarta tarea');
      await todoInput.press('Enter');
      
      const todoItems = page.locator('.todo-list li');
      await expect(todoItems).toHaveCount(4);
      
      // Verificar que la nueva tarea está al final
      await expect(todoItems.nth(3).locator('label')).toHaveText('Cuarta tarea');
      
      // Verificar que el orden anterior se mantiene
      await expect(todoItems.nth(0).locator('label')).toHaveText('Primera tarea');
      await expect(todoItems.nth(1).locator('label')).toHaveText('Segunda tarea');
      await expect(todoItems.nth(2).locator('label')).toHaveText('Tercera tarea');
    });

    // Step 7: Comprobar que el contador de elementos muestra "4 items left"
    await test.step('Check that counter shows "4 items left"', async () => {
      const todoCount = page.locator('.todo-count');
      await expect(todoCount).toBeVisible();
      await expect(todoCount).toContainText('4 items left');
    });

    // Criterios de aceptación adicionales
    await test.step('Verify acceptance criteria', async () => {
      const todoItems = page.locator('.todo-list li');
      
      // El orden de las tareas debe mantenerse consistente
      const taskTexts = await todoItems.locator('label').allTextContents();
      expect(taskTexts).toEqual(['Primera tarea', 'Segunda tarea', 'Tercera tarea', 'Cuarta tarea']);
      
      // No debe haber duplicados en la lista
      const uniqueTexts = [...new Set(taskTexts)];
      expect(uniqueTexts.length).toBe(taskTexts.length);
      
      // El contador debe ser preciso
      expect(taskTexts.length).toBe(4);
    });
  });

  test('should maintain order when adding multiple tasks consecutively', async ({ page }) => {
    await test.step('Navigate to TODO application', async () => {
      await page.goto('/todomvc/');
    });

    const taskNames = ['Tarea A', 'Tarea B', 'Tarea C', 'Tarea D', 'Tarea E'];
    
    // Añadir múltiples tareas de forma consecutiva
    for (let i = 0; i < taskNames.length; i++) {
      await test.step(`Add task ${i + 1}: "${taskNames[i]}"`, async () => {
        const todoInput = page.locator('.new-todo');
        await todoInput.fill(taskNames[i]);
        await todoInput.press('Enter');
        
        // Verificar que el contador se actualiza correctamente
        const todoCount = page.locator('.todo-count');
        await expect(todoCount).toContainText(`${i + 1} item${i === 0 ? '' : 's'} left`);
      });
    }

    // Verificar orden final
    await test.step('Verify final order of all tasks', async () => {
      const todoItems = page.locator('.todo-list li');
      await expect(todoItems).toHaveCount(5);
      
      for (let i = 0; i < taskNames.length; i++) {
        await expect(todoItems.nth(i).locator('label')).toHaveText(taskNames[i]);
      }
    });
  });
});