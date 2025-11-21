package com.example.demo.dto;

import java.util.List;
import java.util.Map;

/**
 * DTO que representa un grafo con sus nodos y aristas
 */
public class Graph {
    private Map<String, List<GraphEdge>> adjacencyList;

    public Graph() {}

    public Graph(Map<String, List<GraphEdge>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public Map<String, List<GraphEdge>> getAdjacencyList() {
        return adjacencyList;
    }

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
