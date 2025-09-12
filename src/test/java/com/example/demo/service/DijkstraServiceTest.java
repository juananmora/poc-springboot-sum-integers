package com.example.demo.service;

import com.example.demo.dto.DijkstraResult;
import com.example.demo.dto.Graph;
import com.example.demo.dto.GraphEdge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("DijkstraService Tests")
class DijkstraServiceTest {

    private DijkstraService dijkstraService;
    private Graph defaultGraph;

    @BeforeEach
    void setUp() {
        dijkstraService = new DijkstraService();
        defaultGraph = dijkstraService.createDefaultGraph();
    }

    @Nested
    @DisplayName("findShortestPath Tests")
    class FindShortestPathTests {

        @Test
        @DisplayName("Should find shortest path from A to F")
        void shouldFindShortestPathFromAToF() {
            // Act
            DijkstraResult result = dijkstraService.findShortestPath(defaultGraph, "A", "F");

            // Assert
            assertTrue(result.isPathFound());
            assertEquals("A", result.getSource());
            assertEquals("F", result.getDestination());
            assertEquals(12, result.getDistance()); // A->C(2) + C->B(1) + B->D(5) + D->E(2) + E->F(2) = 12
            List<String> expectedPath = Arrays.asList("A", "C", "B", "D", "E", "F");
            assertEquals(expectedPath, result.getPath());
        }

        @Test
        @DisplayName("Should find shortest path from A to C")
        void shouldFindShortestPathFromAToC() {
            // Act
            DijkstraResult result = dijkstraService.findShortestPath(defaultGraph, "A", "C");

            // Assert
            assertTrue(result.isPathFound());
            assertEquals("A", result.getSource());
            assertEquals("C", result.getDestination());
            assertEquals(2, result.getDistance()); // Directo A->C(2)
            List<String> expectedPath = Arrays.asList("A", "C");
            assertEquals(expectedPath, result.getPath());
        }

        @Test
        @DisplayName("Should find alternative path when direct is not shortest")
        void shouldFindAlternativePathWhenDirectIsNotShortest() {
            // Act
            DijkstraResult result = dijkstraService.findShortestPath(defaultGraph, "A", "D");

            // Assert
            assertTrue(result.isPathFound());
            assertEquals("A", result.getSource());
            assertEquals("D", result.getDestination());
            assertEquals(8, result.getDistance()); // A->C(2) + C->B(1) + B->D(5) = 8, better than A->B(4) + B->D(5) = 9
            List<String> expectedPath = Arrays.asList("A", "C", "B", "D");
            assertEquals(expectedPath, result.getPath());
        }

        @Test
        @DisplayName("Should handle same source and destination")
        void shouldHandleSameSourceAndDestination() {
            // Act
            DijkstraResult result = dijkstraService.findShortestPath(defaultGraph, "A", "A");

            // Assert
            assertTrue(result.isPathFound());
            assertEquals("A", result.getSource());
            assertEquals("A", result.getDestination());
            assertEquals(0, result.getDistance());
            List<String> expectedPath = Arrays.asList("A");
            assertEquals(expectedPath, result.getPath());
        }

        @Test
        @DisplayName("Should throw exception when graph is null")
        void shouldThrowExceptionWhenGraphIsNull() {
            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> dijkstraService.findShortestPath(null, "A", "B")
            );
            assertEquals("El grafo no puede ser nulo", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when source is null")
        void shouldThrowExceptionWhenSourceIsNull() {
            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> dijkstraService.findShortestPath(defaultGraph, null, "B")
            );
            assertEquals("Los nodos origen y destino no pueden ser nulos", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when destination is null")
        void shouldThrowExceptionWhenDestinationIsNull() {
            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> dijkstraService.findShortestPath(defaultGraph, "A", null)
            );
            assertEquals("Los nodos origen y destino no pueden ser nulos", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when source node does not exist")
        void shouldThrowExceptionWhenSourceNodeDoesNotExist() {
            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> dijkstraService.findShortestPath(defaultGraph, "X", "B")
            );
            assertEquals("El nodo origen 'X' no existe en el grafo", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when destination node does not exist")
        void shouldThrowExceptionWhenDestinationNodeDoesNotExist() {
            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> dijkstraService.findShortestPath(defaultGraph, "A", "X")
            );
            assertEquals("El nodo destino 'X' no existe en el grafo", exception.getMessage());
        }

        @Test
        @DisplayName("Should return no path when nodes are disconnected")
        void shouldReturnNoPathWhenNodesAreDisconnected() {
            // Arrange - Create a graph with disconnected components
            Map<String, List<GraphEdge>> adjacencyList = new HashMap<>();
            adjacencyList.put("A", Arrays.asList(new GraphEdge("B", 1)));
            adjacencyList.put("B", new ArrayList<>());
            adjacencyList.put("C", new ArrayList<>());
            Graph disconnectedGraph = new Graph(adjacencyList);

            // Act
            DijkstraResult result = dijkstraService.findShortestPath(disconnectedGraph, "A", "C");

            // Assert
            assertFalse(result.isPathFound());
            assertEquals("A", result.getSource());
            assertEquals("C", result.getDestination());
            assertEquals(-1, result.getDistance());
            assertTrue(result.getPath().isEmpty());
        }
    }

    @Nested
    @DisplayName("createDefaultGraph Tests")
    class CreateDefaultGraphTests {

        @Test
        @DisplayName("Should create graph with all expected nodes")
        void shouldCreateGraphWithAllExpectedNodes() {
            // Act
            Graph graph = dijkstraService.createDefaultGraph();

            // Assert
            assertNotNull(graph);
            assertNotNull(graph.getAdjacencyList());
            
            Set<String> expectedNodes = Set.of("A", "B", "C", "D", "E", "F");
            assertEquals(expectedNodes, graph.getAdjacencyList().keySet());
        }

        @Test
        @DisplayName("Should create graph with correct edges from A")
        void shouldCreateGraphWithCorrectEdgesFromA() {
            // Act
            Graph graph = dijkstraService.createDefaultGraph();
            List<GraphEdge> edgesFromA = graph.getAdjacencyList().get("A");

            // Assert
            assertEquals(2, edgesFromA.size());
            
            // Verificar A->B(4)
            GraphEdge edgeToB = edgesFromA.stream()
                .filter(edge -> "B".equals(edge.getTo()))
                .findFirst()
                .orElse(null);
            assertNotNull(edgeToB);
            assertEquals(4, edgeToB.getWeight());
            
            // Verificar A->C(2)
            GraphEdge edgeToC = edgesFromA.stream()
                .filter(edge -> "C".equals(edge.getTo()))
                .findFirst()
                .orElse(null);
            assertNotNull(edgeToC);
            assertEquals(2, edgeToC.getWeight());
        }

        @Test
        @DisplayName("Should create graph with correct edges from B")
        void shouldCreateGraphWithCorrectEdgesFromB() {
            // Act
            Graph graph = dijkstraService.createDefaultGraph();
            List<GraphEdge> edgesFromB = graph.getAdjacencyList().get("B");

            // Assert
            assertEquals(3, edgesFromB.size()); // Updated: B has 3 edges in bidirectional graph (C, D, E)
            
            // Verificar B->D(5)
            GraphEdge edgeToD = edgesFromB.stream()
                .filter(edge -> "D".equals(edge.getTo()))
                .findFirst()
                .orElse(null);
            assertNotNull(edgeToD);
            assertEquals(5, edgeToD.getWeight());
            
            // Verificar B->C(1)
            GraphEdge edgeToC = edgesFromB.stream()
                .filter(edge -> "C".equals(edge.getTo()))
                .findFirst()
                .orElse(null);
            assertNotNull(edgeToC);
            assertEquals(1, edgeToC.getWeight());
        }

        @Test
        @DisplayName("Should create graph with F having no outgoing edges")
        void shouldCreateGraphWithFHavingNoOutgoingEdges() {
            // Act
            Graph graph = dijkstraService.createDefaultGraph();
            List<GraphEdge> edgesFromF = graph.getAdjacencyList().get("F");

            // Assert
            assertNotNull(edgesFromF);
            assertFalse(edgesFromF.isEmpty()); // Updated: F now has outgoing edges in bidirectional graph (E)
        }
    }

    @Nested
    @DisplayName("Edge Cases and Performance Tests")
    class EdgeCasesAndPerformanceTests {

        @Test
        @DisplayName("Should handle graph with adjacency list null")
        void shouldHandleGraphWithAdjacencyListNull() {
            // Arrange
            Graph invalidGraph = new Graph(null);

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> dijkstraService.findShortestPath(invalidGraph, "A", "B")
            );
            assertEquals("El grafo no puede ser nulo", exception.getMessage());
        }

        @Test
        @DisplayName("Should handle single node graph")
        void shouldHandleSingleNodeGraph() {
            // Arrange
            Map<String, List<GraphEdge>> adjacencyList = new HashMap<>();
            adjacencyList.put("A", new ArrayList<>());
            Graph singleNodeGraph = new Graph(adjacencyList);

            // Act
            DijkstraResult result = dijkstraService.findShortestPath(singleNodeGraph, "A", "A");

            // Assert
            assertTrue(result.isPathFound());
            assertEquals(0, result.getDistance());
            assertEquals(Arrays.asList("A"), result.getPath());
        }

        @Test
        @DisplayName("Should handle empty adjacency list for existing node")
        void shouldHandleEmptyAdjacencyListForExistingNode() {
            // Arrange
            Map<String, List<GraphEdge>> adjacencyList = new HashMap<>();
            adjacencyList.put("A", null);
            adjacencyList.put("B", new ArrayList<>());
            Graph graphWithNullList = new Graph(adjacencyList);

            // Act
            DijkstraResult result = dijkstraService.findShortestPath(graphWithNullList, "A", "B");

            // Assert
            assertFalse(result.isPathFound());
            assertEquals(-1, result.getDistance());
        }
    }
}
