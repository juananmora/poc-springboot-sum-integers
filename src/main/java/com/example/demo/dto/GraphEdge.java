package com.example.demo.dto;

/**
 * DTO que representa una arista del grafo con su nodo destino y peso
 */
public class GraphEdge {
    private String to;
    private int weight;

    public GraphEdge() {}

    public GraphEdge(String to, int weight) {
        this.to = to;
        this.weight = weight;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getWeight() {
        return weight;
    }

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
