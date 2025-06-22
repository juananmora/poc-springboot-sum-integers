package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.util.List;

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
}