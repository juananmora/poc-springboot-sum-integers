package com.example.demo;

import com.example.demo.controller.AdditionController;
import com.example.demo.service.MathService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("DemoApplication Context Tests")
class DemoApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private MathService mathService;

    @Autowired
    private AdditionController additionController;

    @Test
    @DisplayName("Should load Spring Boot application context")
    void contextLoads() {
        assertNotNull(applicationContext);
    }

    @Test
    @DisplayName("Should load MathService bean")
    void mathServiceShouldLoad() {
        assertNotNull(mathService);
        assertTrue(applicationContext.containsBean("mathService"));
    }

    @Test
    @DisplayName("Should load AdditionController bean")
    void additionControllerShouldLoad() {
        assertNotNull(additionController);
    }

    @Test
    @DisplayName("Should have working sqrt functionality")
    void sqrtFunctionalityShouldWork() {
        // Test that the sqrt method is accessible and working
        assertEquals(2.0, mathService.sqrt(4.0), 0.0001);
        assertEquals(0.0, mathService.sqrt(0.0), 0.0001);
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> mathService.sqrt(-1.0)
        );
        assertEquals("No se puede calcular la raíz cuadrada de un número negativo", exception.getMessage());
    }
}