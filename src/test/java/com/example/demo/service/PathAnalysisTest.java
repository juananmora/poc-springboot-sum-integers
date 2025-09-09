package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.dto.Graph;
import com.example.demo.dto.DijkstraResult;

public class PathAnalysisTest {
    
    private DijkstraService dijkstraService;
    
    @BeforeEach
    void setUp() {
        dijkstraService = new DijkstraService();
    }
    
    @Test
    void analyzeAllOptimalPaths() {
        Graph graph = dijkstraService.createDefaultGraph();
        
        System.out.println("=== ANÁLISIS COMPLETO DE CAMINOS ÓPTIMOS ===");
        System.out.println();
        
        // Camino A → F
        DijkstraResult resultAF = dijkstraService.findShortestPath(graph, "A", "F");
        System.out.println("🎯 CAMINO ÓPTIMO A → F:");
        System.out.println("   Ruta: " + resultAF.getPath());
        System.out.println("   Distancia: " + resultAF.getDistance());
        System.out.println("   Detalles: " + getPathDetails(resultAF.getPath()));
        System.out.println();
        
        // Camino A → D  
        DijkstraResult resultAD = dijkstraService.findShortestPath(graph, "A", "D");
        System.out.println("🎯 CAMINO ÓPTIMO A → D:");
        System.out.println("   Ruta: " + resultAD.getPath());
        System.out.println("   Distancia: " + resultAD.getDistance());
        System.out.println("   Detalles: " + getPathDetails(resultAD.getPath()));
        System.out.println();
        
        // Camino B → F
        DijkstraResult resultBF = dijkstraService.findShortestPath(graph, "B", "F");
        System.out.println("🎯 CAMINO ÓPTIMO B → F:");
        System.out.println("   Ruta: " + resultBF.getPath());
        System.out.println("   Distancia: " + resultBF.getDistance());
        System.out.println("   Detalles: " + getPathDetails(resultBF.getPath()));
        System.out.println();
        
        // Verificaciones
        assertEquals(12, resultAF.getDistance(), "A→F debería ser 12");
        assertEquals(8, resultAD.getDistance(), "A→D debería ser 8");  
        assertEquals(9, resultBF.getDistance(), "B→F debería ser 9");
        
        System.out.println("✅ Todas las pruebas de caminos óptimos completadas exitosamente");
    }
    
    private String getPathDetails(java.util.List<String> path) {
        if (path.size() < 2) return "Sin conexiones";
        
        StringBuilder details = new StringBuilder();
        for (int i = 0; i < path.size() - 1; i++) {
            String from = path.get(i);
            String to = path.get(i + 1);
            int weight = getEdgeWeight(from, to);
            details.append(from).append("→").append(to).append("(").append(weight).append(")");
            if (i < path.size() - 2) {
                details.append(" + ");
            }
        }
        return details.toString();
    }
    
    private int getEdgeWeight(String from, String to) {
        // Pesos basados en el grafo bidireccional
        switch (from + "-" + to) {
            case "A-B": case "B-A": return 4;
            case "A-C": case "C-A": return 2;
            case "B-C": case "C-B": return 1;
            case "B-D": case "D-B": return 5;
            case "C-D": case "D-C": return 8;
            case "C-E": case "E-C": return 10;
            case "D-E": case "E-D": return 2;
            case "D-F": case "F-D": return 6;
            case "E-F": case "F-E": return 2;
            default: return 0;
        }
    }
}
