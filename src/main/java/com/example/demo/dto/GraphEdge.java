package com.example.demo.dto;

/**
 * Data Transfer Object representing a directed, weighted edge in a graph.
 * 
 * <p>Each edge connects from an implied source node to a destination node
 * with an associated weight. The weight represents the cost of traversing
 * this edge (e.g., distance, time, or cost).
 * 
 * <p>This DTO is used in conjunction with {@link Graph} to represent the
 * structure of a weighted graph for algorithms like Dijkstra's shortest path.
 * 
 * <h3>Example Usage:</h3>
 * <pre>
 * GraphEdge edge = new GraphEdge("B", 5);
 * // Represents an edge to node "B" with weight 5
 * </pre>
 * 
 * @author Spring Boot Math Service Team
 * @version 1.0
 * @since 1.0
 * @see Graph
 * @see com.example.demo.service.DijkstraService
 */
public class GraphEdge {
    private String to;
    private int weight;

    /**
     * Default constructor for JSON deserialization.
     */
    public GraphEdge() {}

    /**
     * Constructs a GraphEdge with the specified destination node and weight.
     * 
     * @param to the destination node identifier
     * @param weight the cost/weight of traversing this edge (must be positive for Dijkstra)
     */
    public GraphEdge(String to, int weight) {
        this.to = to;
        this.weight = weight;
    }

    /**
     * Gets the destination node identifier.
     * 
     * @return the node this edge connects to
     */
    public String getTo() {
        return to;
    }

    /**
     * Sets the destination node identifier.
     * 
     * @param to the node this edge connects to
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * Gets the weight (cost) of this edge.
     * 
     * @return the weight of traversing this edge
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Sets the weight (cost) of this edge.
     * 
     * @param weight the weight of traversing this edge
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "GraphEdge{" +
                "to='" + to + '\'' +
                ", weight=" + weight +
                '}';
    }
}
