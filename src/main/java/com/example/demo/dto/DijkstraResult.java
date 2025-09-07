package com.example.demo.dto;

import java.util.List;

/**
 * DTO que representa el resultado de la ejecución del algoritmo de Dijkstra
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
