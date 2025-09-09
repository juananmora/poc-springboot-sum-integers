package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.dto.Graph;
import com.example.demo.dto.DijkstraResult;

public class PathVerificationTest {
    
    private DijkstraService dijkstraService;
    
    @BeforeEach
    void setUp() {
        dijkstraService = new DijkstraService();
    }
    
    @Test
    void verifyNewOptimalPathAToF() {
        Graph graph = dijkstraService.createDefaultGraph();
        DijkstraResult result = dijkstraService.findShortestPath(graph, "A", "F");
        
        System.out.println("=== VERIFICATION OF A→F PATH ===");
        System.out.println("Path found: " + result.getPath());
        System.out.println("Total distance: " + result.getDistance());
        System.out.println("Path exists: " + result.isPathFound());
        
        // Check if the path matches A-C-B-D-E-F = 12
        if (result.getPath().equals(java.util.Arrays.asList("A", "C", "B", "D", "E", "F"))) {
            System.out.println("✅ USER PREDICTION CORRECT: A-C-B-D-E-F with distance " + result.getDistance());
        } else {
            System.out.println("❌ Different path found: " + result.getPath());
            System.out.println("Expected: [A, C, B, D, E, F]");
            System.out.println("Actual: " + result.getPath());
        }
        
        assertEquals(12, result.getDistance(), "Distance should be 12 as predicted by user");
        assertTrue(result.isPathFound(), "Path should be found");
    }
}
