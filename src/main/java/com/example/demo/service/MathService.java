package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class MathService {

    public int add(int num1, int num2) {
        if (num1 < 0 || num2 < 0) {
            throw new IllegalArgumentException("Los números no pueden ser negativos");
        }
        return num1 + num2;
    }

    public int sumList(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0;
        }
        return numbers.stream().mapToInt(Integer::intValue).sum();
    }

    public boolean isEven(int number) {
        return number % 2 == 0;
    }

    public boolean isPositive(int number) {
        return number > 0;
    }

    public int multiply(int num1, int num2) {
        return num1 * num2;
    }

    public double divide(int num1, int num2) {
        if (num2 == 0) {
            throw new ArithmeticException("No se puede dividir por cero");
        }
        return (double) num1 / num2;
    }

    public int subtract(int num1, int num2) {
        return num1 - num2;
    }

    public double sqrt(double number) {
        if (number < 0) {
            throw new IllegalArgumentException("No se puede calcular la raíz cuadrada de un número negativo");
        }
        return Math.sqrt(number);
    }

    public double calculateMean(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0.0;
        }
        double sum = numbers.stream().mapToInt(Integer::intValue).sum();
        return sum / numbers.size();
    }

    public List<Integer> quicksort(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Crear una copia para no modificar la lista original
        List<Integer> sortedNumbers = new ArrayList<>(numbers);
        quicksortHelper(sortedNumbers, 0, sortedNumbers.size() - 1);
        return sortedNumbers;
    }

    private void quicksortHelper(List<Integer> numbers, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(numbers, low, high);
            quicksortHelper(numbers, low, pivotIndex - 1);
            quicksortHelper(numbers, pivotIndex + 1, high);
        }
    }

    private int partition(List<Integer> numbers, int low, int high) {
        int pivot = numbers.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (numbers.get(j) <= pivot) {
                i++;
                swap(numbers, i, j);
            }
        }
        swap(numbers, i + 1, high);
        return i + 1;
    }

    private void swap(List<Integer> numbers, int i, int j) {
        Integer temp = numbers.get(i);
        numbers.set(i, numbers.get(j));
        numbers.set(j, temp);
    }
}