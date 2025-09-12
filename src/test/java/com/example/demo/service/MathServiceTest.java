package com.example.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;

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

    @Nested
    @DisplayName("Quicksort Tests")
    class QuicksortTests {

        @Test
        @DisplayName("Should sort empty list")
        void testQuicksortEmptyList() {
            List<Integer> numbers = Arrays.asList();
            List<Integer> result = mathService.quicksort(numbers);
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Should return empty list for null input")
        void testQuicksortNullInput() {
            List<Integer> result = mathService.quicksort(null);
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Should sort single element list")
        void testQuicksortSingleElement() {
            List<Integer> numbers = Arrays.asList(42);
            List<Integer> result = mathService.quicksort(numbers);
            assertEquals(Arrays.asList(42), result);
        }

        @Test
        @DisplayName("Should sort already sorted list")
        void testQuicksortAlreadySorted() {
            List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
            List<Integer> result = mathService.quicksort(numbers);
            assertEquals(Arrays.asList(1, 2, 3, 4, 5), result);
        }

        @Test
        @DisplayName("Should sort reverse sorted list")
        void testQuicksortReverseSorted() {
            List<Integer> numbers = Arrays.asList(5, 4, 3, 2, 1);
            List<Integer> result = mathService.quicksort(numbers);
            assertEquals(Arrays.asList(1, 2, 3, 4, 5), result);
        }

        @Test
        @DisplayName("Should sort random order list")
        void testQuicksortRandomOrder() {
            List<Integer> numbers = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3);
            List<Integer> result = mathService.quicksort(numbers);
            assertEquals(Arrays.asList(1, 1, 2, 3, 3, 4, 5, 5, 6, 9), result);
        }

        @Test
        @DisplayName("Should sort list with duplicates")
        void testQuicksortWithDuplicates() {
            List<Integer> numbers = Arrays.asList(5, 5, 5, 5, 5);
            List<Integer> result = mathService.quicksort(numbers);
            assertEquals(Arrays.asList(5, 5, 5, 5, 5), result);
        }

        @Test
        @DisplayName("Should sort list with negative numbers")
        void testQuicksortWithNegatives() {
            List<Integer> numbers = Arrays.asList(-3, 1, -2, 5, 0, -1);
            List<Integer> result = mathService.quicksort(numbers);
            assertEquals(Arrays.asList(-3, -2, -1, 0, 1, 5), result);
        }

        @Test
        @DisplayName("Should sort list with two elements")
        void testQuicksortTwoElements() {
            List<Integer> numbers = Arrays.asList(2, 1);
            List<Integer> result = mathService.quicksort(numbers);
            assertEquals(Arrays.asList(1, 2), result);
        }

        @Test
        @DisplayName("Should not modify original list")
        void testQuicksortDoesNotModifyOriginal() {
            List<Integer> original = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5));
            List<Integer> originalCopy = new ArrayList<>(original);
            
            mathService.quicksort(original);
            
            // Verificar que la lista original no se modificó
            assertEquals(originalCopy, original);
        }

        @Test
        @DisplayName("Should handle large list efficiently")
        void testQuicksortLargeList() {
            // Crear una lista grande con números aleatorios pero predecibles
            List<Integer> numbers = Arrays.asList(
                100, 50, 150, 25, 75, 125, 175, 12, 37, 62, 87, 112, 137, 162, 187,
                6, 18, 31, 43, 56, 68, 81, 93, 106, 118, 131, 143, 156, 168, 181, 193
            );
            
            List<Integer> result = mathService.quicksort(numbers);
            
            // Verificar que está ordenado
            for (int i = 1; i < result.size(); i++) {
                assertTrue(result.get(i-1) <= result.get(i), 
                    "Lista no está ordenada en posición " + i);
            }
            
            // Verificar que contiene todos los elementos
            assertEquals(numbers.size(), result.size());
        }

        @ParameterizedTest
        @DisplayName("Should sort various small lists correctly")
        @MethodSource("provideSortTestCases")
        void testQuicksortVariousCases(List<Integer> input, List<Integer> expected) {
            List<Integer> result = mathService.quicksort(input);
            assertEquals(expected, result);
        }

        private static Stream<Arguments> provideSortTestCases() {
            return Stream.of(
                Arguments.of(Arrays.asList(1), Arrays.asList(1)),
                Arguments.of(Arrays.asList(2, 1), Arrays.asList(1, 2)),
                Arguments.of(Arrays.asList(1, 2), Arrays.asList(1, 2)),
                Arguments.of(Arrays.asList(3, 2, 1), Arrays.asList(1, 2, 3)),
                Arguments.of(Arrays.asList(1, 3, 2), Arrays.asList(1, 2, 3)),
                Arguments.of(Arrays.asList(2, 3, 1), Arrays.asList(1, 2, 3)),
                Arguments.of(Arrays.asList(-1, 0, 1), Arrays.asList(-1, 0, 1)),
                Arguments.of(Arrays.asList(1, 0, -1), Arrays.asList(-1, 0, 1))
            );
        }

        // ===============================
        // Tests para Quicksort Rational Numbers
        // ===============================

        @Test
        @DisplayName("Should sort empty double list")
        void testQuicksortRationalEmptyList() {
            List<Double> numbers = Arrays.asList();
            List<Double> result = mathService.quicksortRational(numbers);
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Should return empty list for null input in rational quicksort")
        void testQuicksortRationalNullInput() {
            List<Double> result = mathService.quicksortRational(null);
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Should sort single double element list")
        void testQuicksortRationalSingleElement() {
            List<Double> numbers = Arrays.asList(42.5);
            List<Double> result = mathService.quicksortRational(numbers);
            assertEquals(Arrays.asList(42.5), result);
        }

        @Test
        @DisplayName("Should sort already sorted double list")
        void testQuicksortRationalAlreadySorted() {
            List<Double> numbers = Arrays.asList(1.1, 2.2, 3.3, 4.4, 5.5);
            List<Double> result = mathService.quicksortRational(numbers);
            assertEquals(Arrays.asList(1.1, 2.2, 3.3, 4.4, 5.5), result);
        }

        @Test
        @DisplayName("Should sort reverse sorted double list")
        void testQuicksortRationalReverseSorted() {
            List<Double> numbers = Arrays.asList(5.5, 4.4, 3.3, 2.2, 1.1);
            List<Double> result = mathService.quicksortRational(numbers);
            assertEquals(Arrays.asList(1.1, 2.2, 3.3, 4.4, 5.5), result);
        }

        @Test
        @DisplayName("Should sort random order double list")
        void testQuicksortRationalRandomOrder() {
            List<Double> numbers = Arrays.asList(3.14, 1.41, 4.13, 1.41, 5.99, 9.81, 2.71, 6.28);
            List<Double> result = mathService.quicksortRational(numbers);
            
            // Verificar que está ordenado
            for (int i = 1; i < result.size(); i++) {
                assertTrue(result.get(i-1) <= result.get(i), 
                    "Lista no está ordenada en posición " + i);
            }
            
            // Verificar que contiene todos los elementos
            assertEquals(numbers.size(), result.size());
            assertTrue(result.contains(1.41)); // Verificar duplicados
            assertEquals(2, result.stream().mapToLong(d -> d.equals(1.41) ? 1 : 0).sum());
        }

        @Test
        @DisplayName("Should sort double list with duplicates")
        void testQuicksortRationalWithDuplicates() {
            List<Double> numbers = Arrays.asList(5.5, 5.5, 5.5, 5.5, 5.5);
            List<Double> result = mathService.quicksortRational(numbers);
            assertEquals(Arrays.asList(5.5, 5.5, 5.5, 5.5, 5.5), result);
        }

        @Test
        @DisplayName("Should sort double list with negative numbers")
        void testQuicksortRationalWithNegatives() {
            List<Double> numbers = Arrays.asList(-3.3, 1.1, -2.2, 5.5, 0.0, -1.1);
            List<Double> result = mathService.quicksortRational(numbers);
            assertEquals(Arrays.asList(-3.3, -2.2, -1.1, 0.0, 1.1, 5.5), result);
        }

        @Test
        @DisplayName("Should sort double list with two elements")
        void testQuicksortRationalTwoElements() {
            List<Double> numbers = Arrays.asList(2.5, 1.5);
            List<Double> result = mathService.quicksortRational(numbers);
            assertEquals(Arrays.asList(1.5, 2.5), result);
        }

        @Test
        @DisplayName("Should not modify original double list")
        void testQuicksortRationalDoesNotModifyOriginal() {
            List<Double> original = new ArrayList<>(Arrays.asList(3.3, 1.1, 4.4, 1.1, 5.5));
            List<Double> originalCopy = new ArrayList<>(original);
            
            mathService.quicksortRational(original);
            
            // Verificar que la lista original no se modificó
            assertEquals(originalCopy, original);
        }

        @Test
        @DisplayName("Should handle very small decimal differences")
        void testQuicksortRationalSmallDifferences() {
            List<Double> numbers = Arrays.asList(1.0001, 1.0002, 1.0000, 1.0003);
            List<Double> result = mathService.quicksortRational(numbers);
            assertEquals(Arrays.asList(1.0000, 1.0001, 1.0002, 1.0003), result);
        }

        @Test
        @DisplayName("Should handle scientific notation numbers")
        void testQuicksortRationalScientificNotation() {
            List<Double> numbers = Arrays.asList(1e-5, 1e5, 1e-3, 1e3);
            List<Double> result = mathService.quicksortRational(numbers);
            
            // Verificar que está ordenado
            for (int i = 1; i < result.size(); i++) {
                assertTrue(result.get(i-1) <= result.get(i), 
                    "Lista no está ordenada en posición " + i);
            }
        }

        @Test
        @DisplayName("Should handle mixed positive and negative fractions")
        void testQuicksortRationalMixedFractions() {
            List<Double> numbers = Arrays.asList(0.5, -0.5, 0.25, -0.25, 0.75, -0.75);
            List<Double> result = mathService.quicksortRational(numbers);
            assertEquals(Arrays.asList(-0.75, -0.5, -0.25, 0.25, 0.5, 0.75), result);
        }

        @Test
        @DisplayName("Should handle Double.MAX_VALUE and Double.MIN_VALUE")
        void testQuicksortRationalExtremeValues() {
            List<Double> numbers = Arrays.asList(Double.MAX_VALUE, Double.MIN_VALUE, 0.0, 1.0, -1.0);
            List<Double> result = mathService.quicksortRational(numbers);
            
            // Verificar que está ordenado
            for (int i = 1; i < result.size(); i++) {
                assertTrue(result.get(i-1) <= result.get(i), 
                    "Lista no está ordenada en posición " + i);
            }
            
            // Verificar que contiene todos los elementos
            assertEquals(numbers.size(), result.size());
        }

        @ParameterizedTest
        @DisplayName("Should sort various rational number lists correctly")
        @MethodSource("provideRationalSortTestCases")
        void testQuicksortRationalVariousCases(List<Double> input, List<Double> expected) {
            List<Double> result = mathService.quicksortRational(input);
            assertEquals(expected, result);
        }

        private static Stream<Arguments> provideRationalSortTestCases() {
            return Stream.of(
                Arguments.of(Arrays.asList(1.0), Arrays.asList(1.0)),
                Arguments.of(Arrays.asList(2.5, 1.5), Arrays.asList(1.5, 2.5)),
                Arguments.of(Arrays.asList(1.1, 2.2), Arrays.asList(1.1, 2.2)),
                Arguments.of(Arrays.asList(3.3, 2.2, 1.1), Arrays.asList(1.1, 2.2, 3.3)),
                Arguments.of(Arrays.asList(1.5, 3.5, 2.5), Arrays.asList(1.5, 2.5, 3.5)),
                Arguments.of(Arrays.asList(2.7, 3.7, 1.7), Arrays.asList(1.7, 2.7, 3.7)),
                Arguments.of(Arrays.asList(-1.1, 0.0, 1.1), Arrays.asList(-1.1, 0.0, 1.1)),
                Arguments.of(Arrays.asList(1.1, 0.0, -1.1), Arrays.asList(-1.1, 0.0, 1.1))
            );
        }
    }

    @Nested
    @DisplayName("Calculate Mode Tests")
    class CalculateModeTests {

        @Test
        @DisplayName("Should return empty list for null input")
        void testCalculateModeNullList() {
            List<Integer> result = mathService.calculateMode(null);
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Should return empty list for empty input")
        void testCalculateModeEmptyList() {
            List<Integer> result = mathService.calculateMode(Collections.emptyList());
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Should return single element for single element list")
        void testCalculateModeSingleElement() {
            List<Integer> numbers = Arrays.asList(42);
            List<Integer> result = mathService.calculateMode(numbers);
            assertEquals(Arrays.asList(42), result);
        }

        @Test
        @DisplayName("Should return mode for list with clear mode")
        void testCalculateModeWithClearMode() {
            // [1, 2, 2, 3, 2] -> [2]
            List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 2);
            List<Integer> result = mathService.calculateMode(numbers);
            assertEquals(Arrays.asList(2), result);
        }

        @Test
        @DisplayName("Should return multiple modes when tied")
        void testCalculateModeWithMultipleModes() {
            // [1, 1, 2, 2, 3] -> [1, 2] (ambos aparecen 2 veces)
            List<Integer> numbers = Arrays.asList(1, 1, 2, 2, 3);
            List<Integer> result = mathService.calculateMode(numbers);
            assertEquals(Arrays.asList(1, 2), result);
        }

        @Test
        @DisplayName("Should return all elements when all have same frequency")
        void testCalculateModeAllSameFrequency() {
            // [1, 2, 3, 4] -> [1, 2, 3, 4] (todos aparecen 1 vez)
            List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
            List<Integer> result = mathService.calculateMode(numbers);
            assertEquals(Arrays.asList(1, 2, 3, 4), result);
        }

        @Test
        @DisplayName("Should handle list with all same elements")
        void testCalculateModeAllSameElements() {
            // [5, 5, 5, 5] -> [5]
            List<Integer> numbers = Arrays.asList(5, 5, 5, 5);
            List<Integer> result = mathService.calculateMode(numbers);
            assertEquals(Arrays.asList(5), result);
        }

        @Test
        @DisplayName("Should handle negative numbers correctly")
        void testCalculateModeWithNegativeNumbers() {
            // [-1, -1, 0, 1, -1] -> [-1]
            List<Integer> numbers = Arrays.asList(-1, -1, 0, 1, -1);
            List<Integer> result = mathService.calculateMode(numbers);
            assertEquals(Arrays.asList(-1), result);
        }

        @Test
        @DisplayName("Should handle mixed positive and negative numbers")
        void testCalculateModeMixedNumbers() {
            // [-2, -1, -1, 0, 1, 1, 2] -> [-1, 1]
            List<Integer> numbers = Arrays.asList(-2, -1, -1, 0, 1, 1, 2);
            List<Integer> result = mathService.calculateMode(numbers);
            assertEquals(Arrays.asList(-1, 1), result);
        }

        @Test
        @DisplayName("Should return modes in sorted order")
        void testCalculateModeSortedResult() {
            // [3, 1, 3, 1, 5] -> [1, 3] (ordenado)
            List<Integer> numbers = Arrays.asList(3, 1, 3, 1, 5);
            List<Integer> result = mathService.calculateMode(numbers);
            assertEquals(Arrays.asList(1, 3), result);
        }

        @Test
        @DisplayName("Should handle large numbers correctly")
        void testCalculateModeWithLargeNumbers() {
            List<Integer> numbers = Arrays.asList(1000000, 999999, 1000000, 999998);
            List<Integer> result = mathService.calculateMode(numbers);
            assertEquals(Arrays.asList(1000000), result);
        }

        @Test
        @DisplayName("Should handle zero correctly")
        void testCalculateModeWithZero() {
            // [0, 1, 0, 2, 0] -> [0]
            List<Integer> numbers = Arrays.asList(0, 1, 0, 2, 0);
            List<Integer> result = mathService.calculateMode(numbers);
            assertEquals(Arrays.asList(0), result);
        }

        @Test
        @DisplayName("Should handle complex frequency pattern")
        void testCalculateModeComplexPattern() {
            // [1, 2, 3, 1, 2, 1, 4, 4, 4] -> [1, 4] (ambos aparecen 3 veces)
            List<Integer> numbers = Arrays.asList(1, 2, 3, 1, 2, 1, 4, 4, 4);
            List<Integer> result = mathService.calculateMode(numbers);
            assertEquals(Arrays.asList(1, 4), result);
        }

        @ParameterizedTest
        @DisplayName("Should calculate mode correctly for various cases")
        @MethodSource("provideModeTestCases")
        void testCalculateModeVariousCases(List<Integer> input, List<Integer> expected) {
            List<Integer> result = mathService.calculateMode(input);
            assertEquals(expected, result);
        }

        private static Stream<Arguments> provideModeTestCases() {
            return Stream.of(
                Arguments.of(Arrays.asList(1), Arrays.asList(1)),
                Arguments.of(Arrays.asList(1, 2), Arrays.asList(1, 2)),
                Arguments.of(Arrays.asList(1, 1), Arrays.asList(1)),
                Arguments.of(Arrays.asList(1, 2, 1), Arrays.asList(1)),
                Arguments.of(Arrays.asList(1, 2, 2, 3), Arrays.asList(2)),
                Arguments.of(Arrays.asList(5, 1, 3, 1, 5), Arrays.asList(1, 5)),
                Arguments.of(Arrays.asList(-1, -1, -2), Arrays.asList(-1)),
                Arguments.of(Arrays.asList(0, 0, 1, 1, 2), Arrays.asList(0, 1))
            );
        }
    }

    @Nested
    @DisplayName("Factorial Tests")
    class FactorialTests {

        @Test
        @DisplayName("Should return 1 for factorial(0)")
        void testFactorialZero() {
            // Given - 0! = 1 by mathematical definition
            int input = 0;
            long expected = 1L;

            // When
            long result = mathService.factorial(input);

            // Then
            assertEquals(expected, result);
        }

        @Test
        @DisplayName("Should return 1 for factorial(1)")
        void testFactorialOne() {
            // Given - 1! = 1
            int input = 1;
            long expected = 1L;

            // When
            long result = mathService.factorial(input);

            // Then
            assertEquals(expected, result);
        }

        @ParameterizedTest
        @DisplayName("Should calculate correct factorial for small positive integers")
        @CsvSource({
            "2, 2",          // 2! = 2
            "3, 6",          // 3! = 6
            "4, 24",         // 4! = 24
            "5, 120",        // 5! = 120
            "6, 720",        // 6! = 720
            "7, 5040"        // 7! = 5040
        })
        void testFactorialSmallPositiveNumbers(int input, long expected) {
            // When
            long result = mathService.factorial(input);

            // Then
            assertEquals(expected, result);
        }

        @ParameterizedTest
        @DisplayName("Should calculate correct factorial for larger positive integers")
        @CsvSource({
            "8, 40320",                    // 8! = 40320
            "9, 362880",                   // 9! = 362880
            "10, 3628800",                 // 10! = 3628800
            "11, 39916800",                // 11! = 39916800
            "12, 479001600",               // 12! = 479001600
            "13, 6227020800",              // 13! = 6227020800
            "14, 87178291200",             // 14! = 87178291200
            "15, 1307674368000"            // 15! = 1307674368000
        })
        void testFactorialLargerPositiveNumbers(int input, long expected) {
            // When
            long result = mathService.factorial(input);

            // Then
            assertEquals(expected, result);
        }

        @ParameterizedTest
        @DisplayName("Should throw IllegalArgumentException for negative numbers")
        @ValueSource(ints = {-1, -2, -10, -100, -1000})
        void testFactorialNegativeNumbers(int negativeNumber) {
            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> mathService.factorial(negativeNumber)
            );
            assertEquals("No se puede calcular el factorial de un número negativo", exception.getMessage());
        }

        @Test
        @DisplayName("Should handle factorial of 20 without overflow")
        void testFactorialTwenty() {
            // Given - 20! = 2432902008176640000
            int input = 20;
            long expected = 2432902008176640000L;

            // When
            long result = mathService.factorial(input);

            // Then
            assertEquals(expected, result);
            // Verify result is positive (no overflow occurred)
            assertTrue(result > 0);
        }

        @Test
        @DisplayName("Should handle edge case values efficiently")
        void testFactorialEfficiency() {
            // Test that method executes quickly for reasonable inputs
            long startTime = System.nanoTime();
            
            // Calculate factorial of 20 multiple times
            for (int i = 0; i < 1000; i++) {
                mathService.factorial(20);
            }
            
            long endTime = System.nanoTime();
            long durationMs = (endTime - startTime) / 1_000_000;
            
            // Should complete in reasonable time (less than 100ms for 1000 iterations)
            assertTrue(durationMs < 100, "Factorial calculation took too long: " + durationMs + "ms");
        }
    }
}