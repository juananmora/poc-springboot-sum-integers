package com.example.demo.service;

import com.example.demo.dto.DijkstraResult;
import com.example.demo.dto.Graph;
import com.example.demo.dto.GraphEdge;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Servicio que implementa el algoritmo de Dijkstra para encontrar el camino más corto
 * entre dos nodos en un grafo dirigido con pesos positivos
 */
@Service
public class DijkstraService {

    /**
     * Encuentra el camino más corto entre dos nodos usando el algoritmo de Dijkstra
     * 
     * @param graph el grafo representado como lista de adyacencias
     * @param source nodo origen
     * @param destination nodo destino
     * @return resultado con el camino más corto y la distancia
     */
    public DijkstraResult findShortestPath(Graph graph, String source, String destination) {
        if (graph == null || graph.getAdjacencyList() == null) {
            throw new IllegalArgumentException("El grafo no puede ser nulo");
        }
        
        if (source == null || destination == null) {
            throw new IllegalArgumentException("Los nodos origen y destino no pueden ser nulos");
        }
        
        if (!graph.getAdjacencyList().containsKey(source)) {
            throw new IllegalArgumentException("El nodo origen '" + source + "' no existe en el grafo");
        }
        
        if (!graph.getAdjacencyList().containsKey(destination)) {
            throw new IllegalArgumentException("El nodo destino '" + destination + "' no existe en el grafo");
        }

        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<NodeDistance> queue = new PriorityQueue<>(Comparator.comparingInt(nd -> nd.distance));

        // Inicializar distancias
        for (String node : graph.getAdjacencyList().keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(source, 0);
        queue.offer(new NodeDistance(source, 0));

        while (!queue.isEmpty()) {
            NodeDistance current = queue.poll();
            String currentNode = current.node;

            if (visited.contains(currentNode)) {
                continue;
            }

            visited.add(currentNode);

            // Si llegamos al destino, podemos terminar temprano
            if (currentNode.equals(destination)) {
                break;
            }

            // Procesar vecinos
            List<GraphEdge> neighbors = graph.getAdjacencyList().get(currentNode);
            if (neighbors != null) {
                for (GraphEdge edge : neighbors) {
                    String neighbor = edge.getTo();
                    int newDistance = distances.get(currentNode) + edge.getWeight();

                    if (newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                        previous.put(neighbor, currentNode);
                        queue.offer(new NodeDistance(neighbor, newDistance));
                    }
                }
            }
        }

        // Reconstruir el camino
        List<String> path = reconstructPath(previous, source, destination);
        int distance = distances.get(destination);
        boolean pathFound = distance != Integer.MAX_VALUE;

        return new DijkstraResult(source, destination, path, 
                                pathFound ? distance : -1, pathFound);
    }

    /**
     * Crea el grafo predefinido de la imagen para las pruebas
     * @return grafo con los nodos A, B, C, D, E, F y sus conexiones
     */
    public Graph createDefaultGraph() {
        Map<String, List<GraphEdge>> adjacencyList = new HashMap<>();
        
        // Nodo A: conecta a B(4) y C(2)
        adjacencyList.put("A", Arrays.asList(
            new GraphEdge("B", 4),
            new GraphEdge("C", 2)
        ));
        
        // Nodo B: conecta a D(5) y C(1)
        adjacencyList.put("B", Arrays.asList(
            new GraphEdge("D", 5),
            new GraphEdge("C", 1)
        ));
        
        // Nodo C: conecta a D(8) y E(10)
        adjacencyList.put("C", Arrays.asList(
            new GraphEdge("D", 8),
            new GraphEdge("E", 10)
        ));
        
        // Nodo D: conecta a E(2) y F(6)
        adjacencyList.put("D", Arrays.asList(
            new GraphEdge("E", 2),
            new GraphEdge("F", 6)
        ));
        
        // Nodo E: conecta a F(2)
        adjacencyList.put("E", Arrays.asList(
            new GraphEdge("F", 2)
        ));
        
        // Nodo F: no tiene conexiones salientes
        adjacencyList.put("F", new ArrayList<>());
        
        return new Graph(adjacencyList);
    }

    /**
     * Reconstruye el camino más corto desde el nodo origen al destino
     * @param previous mapa de nodos anteriores
     * @param source nodo origen
     * @param destination nodo destino
     * @return lista con el camino más corto
     */
    private List<String> reconstructPath(Map<String, String> previous, String source, String destination) {
        List<String> path = new ArrayList<>();
        String current = destination;

        // Si no hay camino al destino
        if (!previous.containsKey(destination) && !source.equals(destination)) {
            return path;
        }

        // Reconstruir el camino hacia atrás
        while (current != null) {
            path.add(0, current);
            current = previous.get(current);
        }

        return path;
    }

    /**
     * Clase interna para representar un nodo con su distancia en la cola de prioridad
     */
    private static class NodeDistance {
        String node;
        int distance;

        NodeDistance(String node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }
}
