package com.example.demo.dto;

import java.util.List;
import java.util.Map;

/**
 * Data Transfer Object (DTO) que representa un grafo con sus nodos y aristas.
 * <p>
 * Este DTO se utiliza para transferir información de grafos entre el cliente
 * y el servidor, especialmente en el contexto del algoritmo de Dijkstra.
 * </p>
 * 
 * <p><strong>Estructura:</strong></p>
 * <ul>
 *   <li>El grafo se representa como una lista de adyacencias</li>
 *   <li>Cada nodo (String) mapea a una lista de aristas ({@link GraphEdge})</li>
 *   <li>Las aristas contienen el nodo destino y el peso/distancia</li>
 * </ul>
 * 
 * <p><strong>Ejemplo JSON:</strong></p>
 * <pre>
 * {
 *   "adjacencyList": {
 *     "A": [
 *       {"to": "B", "weight": 4},
 *       {"to": "C", "weight": 2}
 *     ],
 *     "B": [
 *       {"to": "D", "weight": 5}
 *     ]
 *   }
 * }
 * </pre>
 * 
 * @author Spring Boot Math Service Team
 * @version 1.0
 * @see GraphEdge
 * @see com.example.demo.service.DijkstraService
 */
public class Graph {
    private Map<String, List<GraphEdge>> adjacencyList;

    /**
     * Constructor sin argumentos requerido para deserialización JSON.
     */
    public Graph() {}

    /**
     * Constructor con lista de adyacencias.
     * 
     * @param adjacencyList mapa de nodos a listas de aristas
     */
    public Graph(Map<String, List<GraphEdge>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    /**
     * Obtiene la lista de adyacencias del grafo.
     * 
     * @return mapa donde cada clave es un nodo y el valor es la lista de aristas salientes
     */
    public Map<String, List<GraphEdge>> getAdjacencyList() {
        return adjacencyList;
    }

    /**
     * Establece la lista de adyacencias del grafo.
     * 
     * @param adjacencyList mapa de nodos a listas de aristas
     */
    public void setAdjacencyList(Map<String, List<GraphEdge>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "adjacencyList=" + adjacencyList +
                '}';
    }
}
