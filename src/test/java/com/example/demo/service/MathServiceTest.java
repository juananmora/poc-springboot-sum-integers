package com.example.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MathService Tests")
class MathServiceTest {

    private MathService mathService;

    @BeforeEach
    void setUp() {
        mathService = new MathService();
    }

    @Nested
    @DisplayName("Square Root Tests")
    class SqrtTests {

        @Test
        @DisplayName("Should return 0 for sqrt(0)")
        void testSqrtZero() {
            assertEquals(0.0, mathService.sqrt(0.0), 0.0001);
        }

        @Test
        @DisplayName("Should return 1 for sqrt(1)")
        void testSqrtOne() {
            assertEquals(1.0, mathService.sqrt(1.0), 0.0001);
        }

        @ParameterizedTest
        @DisplayName("Should calculate correct square root for perfect squares")
        @CsvSource({
            "4.0, 2.0",
            "9.0, 3.0",
            "16.0, 4.0",
            "25.0, 5.0",
            "36.0, 6.0",
            "49.0, 7.0",
            "64.0, 8.0",
            "81.0, 9.0",
            "100.0, 10.0"
        })
        void testSqrtPerfectSquares(double input, double expected) {
            assertEquals(expected, mathService.sqrt(input), 0.0001);
        }

        @ParameterizedTest
        @DisplayName("Should calculate correct square root for decimal numbers")
        @CsvSource({
            "2.0, 1.4142135623730951",
            "3.0, 1.7320508075688772",
            "0.25, 0.5",
            "0.01, 0.1",
            "1.44, 1.2"
        })
        void testSqrtDecimals(double input, double expected) {
            assertEquals(expected, mathService.sqrt(input), 0.0001);
        }

        @Test
        @DisplayName("Should handle very large numbers")
        void testSqrtVeryLarge() {
            double largeNumber = 1000000.0; // 1 million
            double expected = 1000.0;
            assertEquals(expected, mathService.sqrt(largeNumber), 0.0001);
        }

        @Test
        @DisplayName("Should handle very small positive numbers")
        void testSqrtVerySmall() {
            double smallNumber = 0.000001; // 1 millionth
            double expected = 0.001;
            assertEquals(expected, mathService.sqrt(smallNumber), 0.0001);
        }

        @ParameterizedTest
        @DisplayName("Should throw IllegalArgumentException for negative numbers")
        @ValueSource(doubles = {-1.0, -4.0, -0.1, -100.0, -0.001})
        void testSqrtNegativeNumbers(double negativeNumber) {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, 
                () -> mathService.sqrt(negativeNumber)
            );
            assertEquals("No se puede calcular la raíz cuadrada de un número negativo", exception.getMessage());
        }

        @Test
        @DisplayName("Should handle Double.MAX_VALUE")
        void testSqrtMaxValue() {
            double result = mathService.sqrt(Double.MAX_VALUE);
            assertFalse(Double.isInfinite(result));
            assertFalse(Double.isNaN(result));
            assertTrue(result > 0);
        }

        @Test
        @DisplayName("Should handle very close to zero positive number")
        void testSqrtAlmostZero() {
            double almostZero = Double.MIN_VALUE;
            double result = mathService.sqrt(almostZero);
            assertTrue(result >= 0);
            assertFalse(Double.isNaN(result));
        }
    }

    @Nested
    @DisplayName("Add Operation Tests")
    class AddTests {

        @Test
        @DisplayName("Should add two positive numbers correctly")
        void testAddPositiveNumbers() {
            assertEquals(8, mathService.add(3, 5));
        }

        @Test
        @DisplayName("Should add zero correctly")
        void testAddWithZero() {
            assertEquals(5, mathService.add(0, 5));
            assertEquals(3, mathService.add(3, 0));
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException for negative first number")
        void testAddFirstNegative() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> mathService.add(-1, 5)
            );
            assertEquals("Los números no pueden ser negativos", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException for negative second number")
        void testAddSecondNegative() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> mathService.add(5, -1)
            );
            assertEquals("Los números no pueden ser negativos", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Sum List Tests")
    class SumListTests {

        @Test
        @DisplayName("Should return 0 for empty list")
        void testSumEmptyList() {
            assertEquals(0, mathService.sumList(Collections.emptyList()));
        }

        @Test
        @DisplayName("Should return 0 for null list")
        void testSumNullList() {
            assertEquals(0, mathService.sumList(null));
        }

        @Test
        @DisplayName("Should sum list of positive numbers")
        void testSumPositiveNumbers() {
            List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
            assertEquals(15, mathService.sumList(numbers));
        }

        @Test
        @DisplayName("Should handle single element list")
        void testSumSingleElement() {
            List<Integer> numbers = Arrays.asList(42);
            assertEquals(42, mathService.sumList(numbers));
        }
    }

    @Nested
    @DisplayName("Utility Method Tests")
    class UtilityTests {

        @ParameterizedTest
        @DisplayName("Should correctly identify even numbers")
        @ValueSource(ints = {0, 2, 4, 100, -2, -4})
        void testIsEven(int number) {
            assertTrue(mathService.isEven(number));
        }

        @ParameterizedTest
        @DisplayName("Should correctly identify odd numbers")
        @ValueSource(ints = {1, 3, 5, 99, -1, -3})
        void testIsOdd(int number) {
            assertFalse(mathService.isEven(number));
        }

        @ParameterizedTest
        @DisplayName("Should correctly identify positive numbers")
        @ValueSource(ints = {1, 2, 100, 999})
        void testIsPositive(int number) {
            assertTrue(mathService.isPositive(number));
        }

        @ParameterizedTest
        @DisplayName("Should correctly identify non-positive numbers")
        @ValueSource(ints = {0, -1, -100})
        void testIsNotPositive(int number) {
            assertFalse(mathService.isPositive(number));
        }
    }

    @Nested
    @DisplayName("Arithmetic Operations Tests")
    class ArithmeticTests {

        @Test
        @DisplayName("Should multiply correctly")
        void testMultiply() {
            assertEquals(15, mathService.multiply(3, 5));
            assertEquals(0, mathService.multiply(0, 5));
            assertEquals(-10, mathService.multiply(-2, 5));
        }

        @Test
        @DisplayName("Should divide correctly")
        void testDivide() {
            assertEquals(2.5, mathService.divide(5, 2), 0.0001);
            assertEquals(0.0, mathService.divide(0, 5), 0.0001);
        }

        @Test
        @DisplayName("Should throw ArithmeticException for division by zero")
        void testDivideByZero() {
            ArithmeticException exception = assertThrows(
                ArithmeticException.class,
                () -> mathService.divide(5, 0)
            );
            assertEquals("No se puede dividir por cero", exception.getMessage());
        }

        @Test
        @DisplayName("Should subtract correctly")
        void testSubtract() {
            assertEquals(2, mathService.subtract(7, 5));
            assertEquals(-3, mathService.subtract(2, 5));
            assertEquals(5, mathService.subtract(5, 0));
        }
    }

    @Nested
    @DisplayName("Calculate Mean Tests")
    class CalculateMeanTests {

        @Test
        @DisplayName("Should calculate mean for valid list of integers")
        void testCalculateMeanValidList() {
            // Escenario 1: [1, 2, 3, 4, 5] -> 3.0
            List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
            assertEquals(3.0, mathService.calculateMean(numbers), 0.0001);
        }

        @Test
        @DisplayName("Should return same number for single element list")
        void testCalculateMeanSingleElement() {
            // Escenario 2: [7] -> 7.0
            List<Integer> numbers = Arrays.asList(7);
            assertEquals(7.0, mathService.calculateMean(numbers), 0.0001);
        }

        @Test
        @DisplayName("Should return 0.0 for empty list")
        void testCalculateMeanEmptyList() {
            // Escenario 3: [] -> 0.0
            assertEquals(0.0, mathService.calculateMean(Collections.emptyList()), 0.0001);
        }

        @Test
        @DisplayName("Should return 0.0 for null list")
        void testCalculateMeanNullList() {
            // Escenario 3: null -> 0.0
            assertEquals(0.0, mathService.calculateMean(null), 0.0001);
        }

        @Test
        @DisplayName("Should calculate mean correctly with negative numbers")
        void testCalculateMeanWithNegatives() {
            // Escenario 4: [-1, 0, 1] -> 0.0
            List<Integer> numbers = Arrays.asList(-1, 0, 1);
            assertEquals(0.0, mathService.calculateMean(numbers), 0.0001);
        }

        @Test
        @DisplayName("Should calculate mean correctly with all negative numbers")
        void testCalculateMeanAllNegatives() {
            List<Integer> numbers = Arrays.asList(-1, -2, -3);
            assertEquals(-2.0, mathService.calculateMean(numbers), 0.0001);
        }

        @Test
        @DisplayName("Should calculate mean correctly with decimal result")
        void testCalculateMeanDecimalResult() {
            List<Integer> numbers = Arrays.asList(1, 2, 4);
            assertEquals(2.3333333333333335, mathService.calculateMean(numbers), 0.0001);
        }

        @Test
        @DisplayName("Should handle large numbers correctly")
        void testCalculateMeanLargeNumbers() {
            List<Integer> numbers = Arrays.asList(1000, 2000, 3000);
            assertEquals(2000.0, mathService.calculateMean(numbers), 0.0001);
        }
    }
}