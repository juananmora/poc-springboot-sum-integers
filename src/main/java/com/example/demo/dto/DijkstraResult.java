package com.example.demo.dto;

import java.util.List;

/**
 * Data Transfer Object representing the result of Dijkstra's shortest path algorithm execution.
 * 
 * <p>This DTO encapsulates all information about a shortest path computation, including:
 * <ul>
 *   <li>Source and destination nodes</li>
 *   <li>The complete path from source to destination</li>
 *   <li>Total distance/cost of the path</li>
 *   <li>Whether a valid path was found</li>
 * </ul>
 * 
 * <h3>Example Response:</h3>
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
 * <p>If no path exists between the source and destination:
 * <pre>
 * {
 *   "source": "A",
 *   "destination": "X",
 *   "path": [],
 *   "distance": -1,
 *   "pathFound": false
 * }
 * </pre>
 * 
 * @author Spring Boot Math Service Team
 * @version 1.0
 * @since 1.0
 * @see com.example.demo.service.DijkstraService
 * @see Graph
 */
public class DijkstraResult {
    private String source;
    private String destination;
    private List<String> path;
    private int distance;
    private boolean pathFound;

    /**
     * Default constructor for JSON deserialization.
     */
    public DijkstraResult() {}

    /**
     * Constructs a DijkstraResult with all path information.
     * 
     * @param source the starting node of the path
     * @param destination the ending node of the path
     * @param path ordered list of nodes from source to destination (empty if no path exists)
     * @param distance total cost/weight of the path (-1 if no path exists)
     * @param pathFound true if a valid path exists, false otherwise
     */
    public DijkstraResult(String source, String destination, List<String> path, int distance, boolean pathFound) {
        this.source = source;
        this.destination = destination;
        this.path = path;
        this.distance = distance;
        this.pathFound = pathFound;
    }

    /**
     * Gets the source node identifier.
     * 
     * @return the starting node of the path
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the source node identifier.
     * 
     * @param source the starting node of the path
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Gets the destination node identifier.
     * 
     * @return the ending node of the path
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the destination node identifier.
     * 
     * @param destination the ending node of the path
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Gets the complete path from source to destination.
     * 
     * @return ordered list of node identifiers, or empty list if no path exists
     */
    public List<String> getPath() {
        return path;
    }

    /**
     * Sets the complete path from source to destination.
     * 
     * @param path ordered list of node identifiers
     */
    public void setPath(List<String> path) {
        this.path = path;
    }

    /**
     * Gets the total distance/cost of the path.
     * 
     * @return total weight of all edges in the path, or -1 if no path exists
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Sets the total distance/cost of the path.
     * 
     * @param distance total weight of all edges in the path
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Indicates whether a valid path was found.
     * 
     * @return true if a path exists from source to destination, false otherwise
     */
    public boolean isPathFound() {
        return pathFound;
    }

    /**
     * Sets whether a valid path was found.
     * 
     * @param pathFound true if a path exists, false otherwise
     */
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
