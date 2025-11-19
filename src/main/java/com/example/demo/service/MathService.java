package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Service
public class MathService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MathService.class);
    
    // ⚠️ ADVERTENCIA DE SEGURIDAD ⚠️
    // Esta función demuestra las mejores prácticas de seguridad según jon-security space.
    // NUNCA se debe imprimir passwords directamente en terminal o logs por motivos de seguridad.
    
    /**
     * Función segura para manejo de credenciales según prácticas del space jon-security.
     * 
     * NOTA IMPORTANTE: Esta implementación NO imprime passwords en terminal, ya que esto
     * violaría múltiples principios de seguridad establecidos en el space jon-security:
     * 
     * 1. "No publicar datos sensibles en los logs"
     * 2. "Almacenamiento seguro de credenciales" 
     * 3. "Evitar exposición de información sensible"
     * 
     * @param sensitiveData - Dato sensible a procesar de forma segura
     * @return Hash seguro para verificación (NO el password original)
     */
    public String processCredentialSecurely(String sensitiveData) {
        
        // 1. VALIDACIÓN DE ENTRADA según jon-security space
        if (sensitiveData == null || sensitiveData.trim().isEmpty()) {
            LOGGER.warn("Intento de procesamiento de credencial vacía o nula - operación rechazada");
            throw new IllegalArgumentException("Los datos sensibles no pueden estar vacíos");
        }
        
        // 2. VALIDACIÓN DE LONGITUD (política de seguridad)
        if (sensitiveData.length() < 8) {
            LOGGER.warn("Intento de procesamiento de credencial que no cumple políticas de seguridad");
            throw new IllegalArgumentException("Los datos sensibles deben cumplir las políticas mínimas de seguridad");
        }
        
        try {
            // 3. PROCESAMIENTO SEGURO - En lugar de imprimir en terminal:
            // - Generar hash seguro para verificación
            // - NO almacenar el password original
            // - NO imprimir en terminal (violación de seguridad)
            
            String secureHash = generateSecureHash(sensitiveData);
            
            // 4. LOG SEGURO - Solo información técnica, SIN datos sensibles
            LOGGER.info("Procesamiento seguro de credencial completado exitosamente");
            
            // 5. ADVERTENCIA DE SEGURIDAD EN LUGAR DE IMPRIMIR PASSWORD
            System.out.println("=".repeat(80));
            System.out.println("🔒 ADVERTENCIA DE SEGURIDAD - Space jon-security");
            System.out.println("=".repeat(80));
            System.out.println("❌ OPERACIÓN BLOQUEADA por políticas de seguridad");
            System.out.println("");
            System.out.println("📋 MOTIVOS DE BLOQUEO:");
            System.out.println("   • Imprimir passwords en terminal es una vulnerabilidad de seguridad");
            System.out.println("   • Base64 NO es cifrado, solo encoding - el password seguiría visible");
            System.out.println("   • Podría quedar registrado en logs o historial de terminal");
            System.out.println("   • Viola el principio de 'gestión segura de credenciales'");
            System.out.println("");
            System.out.println("✅ ALTERNATIVAS SEGURAS IMPLEMENTADAS:");
            System.out.println("   • Hash seguro generado para verificación");
            System.out.println("   • Validación de entrada aplicada");
            System.out.println("   • Logs seguros sin datos sensibles");
            System.out.println("   • Manejo seguro de errores");
            System.out.println("");
            System.out.println("📝 Hash para verificación: " + secureHash.substring(0, 8) + "...[TRUNCADO]");
            System.out.println("=".repeat(80));
            
            return secureHash;
            
        } catch (Exception e) {
            // 6. MANEJO SEGURO DE ERRORES - No exponer información sensible
            LOGGER.error("Error en procesamiento seguro de credencial - detalles técnicos registrados");
            throw new RuntimeException("Error en procesamiento de datos sensibles");
        }
    }
    
    /**
     * Genera un hash seguro usando algoritmos modernos.
     * Implementa las recomendaciones del space jon-security sobre criptografía.
     */
    private String generateSecureHash(String data) {
        try {
            // Usar algoritmos criptográficos seguros (no MD5/SHA1 según jon-security)
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            
            // Añadir salt para mayor seguridad
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            
            digest.update(salt);
            byte[] hashedBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            
            // Combinar salt + hash en Base64 para almacenamiento seguro
            byte[] saltAndHash = new byte[salt.length + hashedBytes.length];
            System.arraycopy(salt, 0, saltAndHash, 0, salt.length);
            System.arraycopy(hashedBytes, 0, saltAndHash, salt.length, hashedBytes.length);
            
            return Base64.getEncoder().encodeToString(saltAndHash);
            
        } catch (Exception e) {
            LOGGER.error("Error en generación de hash seguro");
            throw new RuntimeException("Error en procesamiento criptográfico");
        }
    }
    
    /**
     * Método alternativo para demostrar manejo seguro de configuración externa.
     * Sigue las prácticas del space jon-security sobre gestión de secretos.
     */
    public void demonstrateSecureConfigHandling() {
        System.out.println("\n🔐 DEMO: Manejo seguro de configuración (jon-security space)");
        System.out.println("=".repeat(60));
        System.out.println("✅ Usar variables de entorno para secretos:");
        System.out.println("   export DB_PASSWORD=your_secure_password");
        System.out.println("   String dbPassword = System.getenv(\"DB_PASSWORD\");");
        System.out.println("");
        System.out.println("✅ Usar Spring @Value con configuración externa:");
        System.out.println("   @Value(\"${app.security.secret:}\"");
        System.out.println("   private String securitySecret;");
        System.out.println("");
        System.out.println("✅ Usar gestores de secretos (Azure Key Vault, etc.)");
        System.out.println("❌ NUNCA hardcodear passwords en código");
        System.out.println("❌ NUNCA imprimir passwords en terminal/logs");
        System.out.println("=".repeat(60));
    }

    /**
     * Adds two non-negative integers together.
     * This method validates that both input numbers are non-negative before performing the addition.
     * 
     * @param num1 the first number to add (must be non-negative)
     * @param num2 the second number to add (must be non-negative)
     * @return the sum of num1 and num2
     * @throws IllegalArgumentException if either num1 or num2 is negative
     */
    public int add(int num1, int num2) {
        if (num1 < 0 || num2 < 0) {
            throw new IllegalArgumentException("Los números no pueden ser negativos");
        }
        return num1 + num2;
    }

    /**
     * Calculates the sum of all integers in a list.
     * Returns 0 for null or empty lists.
     * 
     * @param numbers the list of integers to sum (can be null or empty)
     * @return the sum of all numbers in the list, or 0 if the list is null or empty
     */
    public int sumList(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0;
        }
        return numbers.stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Determines if a number is even.
     * A number is even if it is divisible by 2 with no remainder.
     * 
     * @param number the integer to check
     * @return true if the number is even, false otherwise
     */
    public boolean isEven(int number) {
        return number % 2 == 0;
    }

    /**
     * Determines if a number is positive.
     * A number is positive if it is greater than zero.
     * 
     * @param number the integer to check
     * @return true if the number is positive (> 0), false otherwise
     */
    public boolean isPositive(int number) {
        return number > 0;
    }

    /**
     * Multiplies two integers together.
     * 
     * @param num1 the first number to multiply
     * @param num2 the second number to multiply
     * @return the product of num1 and num2
     */
    public int multiply(int num1, int num2) {
        return num1 * num2;
    }

    /**
     * Divides one integer by another, returning the result as a double.
     * This method validates that the divisor is not zero before performing division.
     * 
     * @param num1 the dividend (number to be divided)
     * @param num2 the divisor (number to divide by)
     * @return the quotient as a double-precision floating-point number
     * @throws ArithmeticException if num2 is zero
     */
    public double divide(int num1, int num2) {
        if (num2 == 0) {
            throw new ArithmeticException("No se puede dividir por cero");
        }
        return (double) num1 / num2;
    }

    /**
     * Subtracts one integer from another.
     * 
     * @param num1 the minuend (number to subtract from)
     * @param num2 the subtrahend (number to be subtracted)
     * @return the difference (num1 - num2)
     */
    public int subtract(int num1, int num2) {
        return num1 - num2;
    }

    /**
     * Calculates the square root of a non-negative number.
     * This method validates that the input is non-negative before calculation.
     * 
     * @param number the number to find the square root of (must be non-negative)
     * @return the square root of the number
     * @throws IllegalArgumentException if the number is negative
     */
    public double sqrt(double number) {
        if (number < 0) {
            throw new IllegalArgumentException("No se puede calcular la raíz cuadrada de un número negativo");
        }
        return Math.sqrt(number);
    }

    /**
     * Calculates the arithmetic mean (average) of a list of integers.
     * Returns 0.0 for null or empty lists.
     * 
     * @param numbers the list of integers to average (can be null or empty)
     * @return the mean value, or 0.0 if the list is null or empty
     */
    public double calculateMean(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0.0;
        }
        double sum = numbers.stream().mapToInt(Integer::intValue).sum();
        return sum / numbers.size();
    }

    /**
     * Sorts a list of integers using the quicksort algorithm.
     * Creates a copy of the input list to avoid modifying the original.
     * Returns an empty list if the input is null or empty.
     * 
     * <p>Time complexity: O(n log n) average case, O(n²) worst case
     * <p>Space complexity: O(n) for the copy + O(log n) for recursion stack
     * 
     * @param numbers the list of integers to sort (can be null or empty)
     * @return a new sorted list in ascending order, or an empty list if input is null/empty
     */
    public List<Integer> quicksort(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Crear una copia para no modificar la lista original
        List<Integer> sortedNumbers = new ArrayList<>(numbers);
        quicksortHelperInteger(sortedNumbers, 0, sortedNumbers.size() - 1);
        return sortedNumbers;
    }

    /**
     * Sorts a list of doubles using the quicksort algorithm.
     * Creates a copy of the input list to avoid modifying the original.
     * Returns an empty list if the input is null or empty.
     * 
     * <p>Time complexity: O(n log n) average case, O(n²) worst case
     * <p>Space complexity: O(n) for the copy + O(log n) for recursion stack
     * 
     * @param numbers the list of doubles to sort (can be null or empty)
     * @return a new sorted list in ascending order, or an empty list if input is null/empty
     */
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

    /**
     * Calculates the mode(s) of a list of integers.
     * The mode is the value(s) that appear most frequently in the dataset.
     * If multiple values have the same maximum frequency, all are returned in sorted order.
     * Returns an empty list if the input is null or empty.
     * 
     * @param numbers the list of integers to analyze (can be null or empty)
     * @return a sorted list of the mode value(s), or an empty list if input is null/empty
     * 
     * <p><b>Note:</b> If all numbers appear with the same frequency, all numbers are considered modes.
     * For example, {@code [1, 2, 3]} returns {@code [1, 2, 3]}, while {@code [1, 1, 2, 2, 3]} returns {@code [1, 2]}.
     */
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

    /**
     * Calcula el factorial de un número entero.
     * El factorial de n (n!) es el producto de todos los enteros positivos menores o iguales a n.
     * Por definición: 0! = 1, 1! = 1, n! = n * (n-1)!
     * 
     * @param number el número del cual calcular el factorial (debe ser >= 0)
     * @return el factorial del número (long para evitar overflow en valores pequeños)
     * @throws IllegalArgumentException si el número es negativo o si el resultado excede Long.MAX_VALUE
     */
    public long factorial(int number) {
        // Validación: no se puede calcular factorial de números negativos
        if (number < 0) {
            throw new IllegalArgumentException("No se puede calcular el factorial de un número negativo");
        }
        
        // Casos base: 0! = 1 y 1! = 1
        if (number == 0 || number == 1) {
            return 1L;
        }
        
        // Validación de overflow: factorial de números > 20 excede Long.MAX_VALUE
        if (number > 20) {
            throw new IllegalArgumentException("El factorial de " + number + " excede el límite de Long.MAX_VALUE (máximo: 20!)");
        }
        
        // Cálculo iterativo del factorial para evitar stack overflow
        long result = 1L;
        for (int i = 2; i <= number; i++) {
            result *= i;
        }
        
        return result;
    }
}