package com.example.demo.dto;

import java.util.List;

/**
 * Data Transfer Object (DTO) que representa el resultado de ejecutar el algoritmo de Dijkstra.
 * <p>
 * Este objeto encapsula toda la información sobre el camino más corto encontrado
 * entre dos nodos en un grafo, incluyendo la ruta completa y la distancia total.
 * </p>
 * 
 * <p><strong>Componentes del resultado:</strong></p>
 * <ul>
 *   <li><strong>source:</strong> Nodo de origen del camino</li>
 *   <li><strong>destination:</strong> Nodo de destino del camino</li>
 *   <li><strong>path:</strong> Lista ordenada de nodos que forman el camino más corto</li>
 *   <li><strong>distance:</strong> Distancia total del camino (suma de pesos de aristas)</li>
 *   <li><strong>pathFound:</strong> Indica si se encontró un camino válido</li>
 * </ul>
 * 
 * <p><strong>Ejemplo de respuesta exitosa:</strong></p>
 * <pre>
 * {
 *   "source": "A",
 *   "destination": "F",
 *   "path": ["A", "C", "B", "D", "E", "F"],
 *   "distance": 12,
 *   "pathFound": true
 * }
 * </pre>
 * 
 * <p><strong>Ejemplo de respuesta sin camino:</strong></p>
 * <pre>
 * {
 *   "source": "A",
 *   "destination": "Z",
 *   "path": null,
 *   "distance": -1,
 *   "pathFound": false
 * }
 * </pre>
 * 
 * @author Spring Boot Math Service Team
 * @version 1.0
 * @see com.example.demo.service.DijkstraService
 * @see Graph
 */
public class DijkstraResult {
    private String source;
    private String destination;
    private List<String> path;
    private int distance;
    private boolean pathFound;

    public DijkstraResult() {}

    public DijkstraResult(String source, String destination, List<String> path, int distance, boolean pathFound) {
        this.source = source;
        this.destination = destination;
        this.path = path;
        this.distance = distance;
        this.pathFound = pathFound;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<String> getPath() {
        return path;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public boolean isPathFound() {
        return pathFound;
    }

    public void setPathFound(boolean pathFound) {
        this.pathFound = pathFound;
    }

    @Override
    public String toString() {
        return "DijkstraResult{" +
                "source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", path=" + path +
                ", distance=" + distance +
                ", pathFound=" + pathFound +
                '}';
    }
}
