package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

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
        quicksortHelperInteger(sortedNumbers, 0, sortedNumbers.size() - 1);
        return sortedNumbers;
    }

    public List<Double> quicksortRational(List<Double> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Crear una copia para no modificar la lista original
        List<Double> sortedNumbers = new ArrayList<>(numbers);
        quicksortHelperDouble(sortedNumbers, 0, sortedNumbers.size() - 1);
        return sortedNumbers;
    }

    private void quicksortHelperInteger(List<Integer> numbers, int low, int high) {
        if (low < high) {
            int pivotIndex = partitionInteger(numbers, low, high);
            quicksortHelperInteger(numbers, low, pivotIndex - 1);
            quicksortHelperInteger(numbers, pivotIndex + 1, high);
        }
    }

    private void quicksortHelperDouble(List<Double> numbers, int low, int high) {
        if (low < high) {
            int pivotIndex = partitionDouble(numbers, low, high);
            quicksortHelperDouble(numbers, low, pivotIndex - 1);
            quicksortHelperDouble(numbers, pivotIndex + 1, high);
        }
    }

    private int partitionInteger(List<Integer> numbers, int low, int high) {
        int pivot = numbers.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (numbers.get(j) <= pivot) {
                i++;
                swapInteger(numbers, i, j);
            }
        }
        swapInteger(numbers, i + 1, high);
        return i + 1;
    }

    private int partitionDouble(List<Double> numbers, int low, int high) {
        double pivot = numbers.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (numbers.get(j) <= pivot) {
                i++;
                swapDouble(numbers, i, j);
            }
        }
        swapDouble(numbers, i + 1, high);
        return i + 1;
    }

    private void swapInteger(List<Integer> numbers, int i, int j) {
        Integer temp = numbers.get(i);
        numbers.set(i, numbers.get(j));
        numbers.set(j, temp);
    }

    private void swapDouble(List<Double> numbers, int i, int j) {
        Double temp = numbers.get(i);
        numbers.set(i, numbers.get(j));
        numbers.set(j, temp);
    }

    public List<Integer> calculateMode(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Contar frecuencias
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (Integer number : numbers) {
            frequencyMap.put(number, frequencyMap.getOrDefault(number, 0) + 1);
        }
        
        // Encontrar la frecuencia máxima
        int maxFrequency = 0;
        for (int frequency : frequencyMap.values()) {
            if (frequency > maxFrequency) {
                maxFrequency = frequency;
            }
        }
        
        // Recopilar todos los números con la frecuencia máxima
        List<Integer> modes = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() == maxFrequency) {
                modes.add(entry.getKey());
            }
        }
        
        // Ordenar la lista de modas para consistencia
        Collections.sort(modes);
        return modes;
    }

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
}