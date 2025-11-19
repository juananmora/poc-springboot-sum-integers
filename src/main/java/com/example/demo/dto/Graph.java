package com.example.demo.dto;

import java.util.List;
import java.util.Map;

/**
 * Data Transfer Object representing a weighted graph using an adjacency list representation.
 * 
 * <p>This DTO is used to pass graph data between the controller and service layers,
 * particularly for the Dijkstra shortest path algorithm implementation.
 * 
 * <p>The graph is represented as a map where:
 * <ul>
 *   <li>Key: Node identifier (String)</li>
 *   <li>Value: List of edges ({@link GraphEdge}) connecting to other nodes</li>
 * </ul>
 * 
 * <h3>Example Structure:</h3>
 * <pre>
 * {
 *   "A": [{"to": "B", "weight": 4}, {"to": "C", "weight": 2}],
 *   "B": [{"to": "D", "weight": 5}],
 *   "C": [{"to": "D", "weight": 8}]
 * }
 * </pre>
 * 
 * @author Spring Boot Math Service Team
 * @version 1.0
 * @since 1.0
 * @see GraphEdge
 * @see com.example.demo.service.DijkstraService
 */
public class Graph {
    private Map<String, List<GraphEdge>> adjacencyList;

    /**
     * Default constructor for JSON deserialization.
     */
    public Graph() {}

    /**
     * Constructs a Graph with the specified adjacency list.
     * 
     * @param adjacencyList map of nodes to their outgoing edges
     */
    public Graph(Map<String, List<GraphEdge>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    /**
     * Gets the adjacency list representation of the graph.
     * 
     * @return map where keys are node identifiers and values are lists of edges
     */
    public Map<String, List<GraphEdge>> getAdjacencyList() {
        return adjacencyList;
    }

    /**
     * Sets the adjacency list representation of the graph.
     * 
     * @param adjacencyList map where keys are node identifiers and values are lists of edges
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
