package com.example.demo.controller;

import com.example.demo.dto.DijkstraResult;
import com.example.demo.dto.Graph;
import com.example.demo.service.DijkstraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para operaciones del algoritmo de Dijkstra
 */
@RestController
@RequestMapping("/api/dijkstra")
public class DijkstraController {

    private final DijkstraService dijkstraService;

    @Autowired
    public DijkstraController(DijkstraService dijkstraService) {
        this.dijkstraService = dijkstraService;
    }

    /**
     * Encuentra el camino más corto entre dos nodos en el grafo predefinido
     * 
     * @param source nodo origen
     * @param destination nodo destino
     * @return resultado con el camino más corto y la distancia
     */
    @GetMapping("/shortest-path")
    public ResponseEntity<DijkstraResult> findShortestPath(
            @RequestParam String source, 
            @RequestParam String destination) {
        
        try {
            Graph defaultGraph = dijkstraService.createDefaultGraph();
            DijkstraResult result = dijkstraService.findShortestPath(defaultGraph, source, destination);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                new DijkstraResult(source, destination, null, -1, false)
            );
        }
    }

    /**
     * Encuentra el camino más corto entre dos nodos en un grafo personalizado
     * 
     * @param graph grafo personalizado
     * @param source nodo origen
     * @param destination nodo destino
     * @return resultado con el camino más corto y la distancia
     */
    @PostMapping("/shortest-path")
    public ResponseEntity<DijkstraResult> findShortestPathCustomGraph(
            @RequestBody Graph graph,
            @RequestParam String source, 
            @RequestParam String destination) {
        
        try {
            DijkstraResult result = dijkstraService.findShortestPath(graph, source, destination);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                new DijkstraResult(source, destination, null, -1, false)
            );
        }
    }

    /**
     * Obtiene el grafo predefinido usado en las pruebas
     * 
     * @return grafo predefinido con nodos A, B, C, D, E, F
     */
    @GetMapping("/default-graph")
    public ResponseEntity<Graph> getDefaultGraph() {
        Graph defaultGraph = dijkstraService.createDefaultGraph();
        return ResponseEntity.ok(defaultGraph);
    }

    /**
     * Encuentra específicamente el camino de A a F en el grafo predefinido
     * 
     * @return resultado del camino óptimo de A a F
     */
    @GetMapping("/path-a-to-f")
    public ResponseEntity<DijkstraResult> findPathAToF() {
        Graph defaultGraph = dijkstraService.createDefaultGraph();
        DijkstraResult result = dijkstraService.findShortestPath(defaultGraph, "A", "F");
        return ResponseEntity.ok(result);
    }
}
