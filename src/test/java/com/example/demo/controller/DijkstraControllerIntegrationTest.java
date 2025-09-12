package com.example.demo.controller;

import com.example.demo.dto.DijkstraResult;
import com.example.demo.dto.Graph;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("DijkstraController Integration Tests")
class DijkstraControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Should find shortest path from A to F using GET endpoint")
    void shouldFindShortestPathFromAToFUsingGetEndpoint() {
        // Act
        String url = "http://localhost:" + port + "/api/dijkstra/shortest-path?source=A&destination=F";
        ResponseEntity<DijkstraResult> response = restTemplate.getForEntity(url, DijkstraResult.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        
        DijkstraResult result = response.getBody();
        assertTrue(result.isPathFound());
        assertEquals("A", result.getSource());
        assertEquals("F", result.getDestination());
        assertEquals(12, result.getDistance());
        assertNotNull(result.getPath());
        assertEquals("A", result.getPath().get(0));
        assertEquals("F", result.getPath().get(result.getPath().size() - 1));
    }

    @Test
    @DisplayName("Should get default graph successfully")
    void shouldGetDefaultGraphSuccessfully() {
        // Act
        String url = "http://localhost:" + port + "/api/dijkstra/default-graph";
        ResponseEntity<Graph> response = restTemplate.getForEntity(url, Graph.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        
        Graph graph = response.getBody();
        assertNotNull(graph.getAdjacencyList());
        assertTrue(graph.getAdjacencyList().containsKey("A"));
        assertTrue(graph.getAdjacencyList().containsKey("F"));
    }

    @Test
    @DisplayName("Should return specific path from A to F")
    void shouldReturnSpecificPathFromAToF() {
        // Act
        String url = "http://localhost:" + port + "/api/dijkstra/path-a-to-f";
        ResponseEntity<DijkstraResult> response = restTemplate.getForEntity(url, DijkstraResult.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        
        DijkstraResult result = response.getBody();
        assertTrue(result.isPathFound());
        assertEquals("A", result.getSource());
        assertEquals("F", result.getDestination());
        assertEquals(12, result.getDistance());
    }

    @Test
    @DisplayName("Should return bad request for invalid node")
    void shouldReturnBadRequestForInvalidNode() {
        // Act
        String url = "http://localhost:" + port + "/api/dijkstra/shortest-path?source=X&destination=F";
        ResponseEntity<DijkstraResult> response = restTemplate.getForEntity(url, DijkstraResult.class);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        
        DijkstraResult result = response.getBody();
        assertFalse(result.isPathFound());
        assertEquals(-1, result.getDistance());
    }
}
