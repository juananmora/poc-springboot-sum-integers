package com.example.demo.dto;

/**
 * Data Transfer Object (DTO) que representa una arista en un grafo dirigido.
 * <p>
 * Una arista conecta dos nodos y tiene un peso asociado que representa
 * la distancia, costo o cualquier otra métrica relevante entre nodos.
 * </p>
 * 
 * <p><strong>Uso:</strong></p>
 * <ul>
 *   <li>Utilizado en el contexto del algoritmo de Dijkstra</li>
 *   <li>Forma parte de la estructura {@link Graph}</li>
 *   <li>El peso debe ser un valor positivo</li>
 * </ul>
 * 
 * <p><strong>Ejemplo:</strong></p>
 * <pre>
 * GraphEdge edge = new GraphEdge("B", 5);
 * // Representa una arista al nodo "B" con peso 5
 * </pre>
 * 
 * @author Spring Boot Math Service Team
 * @version 1.0
 * @see Graph
 */
public class GraphEdge {
    private String to;
    private int weight;

    /**
     * Constructor sin argumentos requerido para deserialización JSON.
     */
    public GraphEdge() {}

    /**
     * Constructor con nodo destino y peso.
     * 
     * @param to identificador del nodo destino
     * @param weight peso de la arista (debe ser positivo)
     */
    public GraphEdge(String to, int weight) {
        this.to = to;
        this.weight = weight;
    }

    /**
     * Obtiene el identificador del nodo destino.
     * 
     * @return identificador del nodo destino
     */
    public String getTo() {
        return to;
    }

    /**
     * Establece el identificador del nodo destino.
     * 
     * @param to identificador del nodo destino
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * Obtiene el peso de la arista.
     * 
     * @return peso de la arista
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Establece el peso de la arista.
     * 
     * @param weight peso de la arista (debe ser positivo)
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
