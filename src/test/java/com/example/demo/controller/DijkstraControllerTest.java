package com.example.demo.controller;

import com.example.demo.dto.DijkstraResult;
import com.example.demo.dto.Graph;
import com.example.demo.dto.GraphEdge;
import com.example.demo.service.DijkstraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DijkstraController.class, 
    excludeAutoConfiguration = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
@DisplayName("DijkstraController Tests")
class DijkstraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DijkstraService dijkstraService;

    private Graph defaultGraph;
    private DijkstraResult successResult;

    @BeforeEach
    void setUp() {
        Map<String, List<GraphEdge>> adjacencyList = new HashMap<>();
        adjacencyList.put("A", Arrays.asList(new GraphEdge("B", 4), new GraphEdge("C", 2)));
        defaultGraph = new Graph(adjacencyList);
        successResult = new DijkstraResult("A", "F", Arrays.asList("A", "B", "D", "E", "F"), 13, true);
    }

    @Nested
    @DisplayName("GET /api/dijkstra/shortest-path Tests")
    class ShortestPathGetTests {

        @Test
        @DisplayName("Should return shortest path when valid parameters provided")
        void shouldReturnShortestPathWhenValidParametersProvided() throws Exception {
            when(dijkstraService.createDefaultGraph()).thenReturn(defaultGraph);
            when(dijkstraService.findShortestPath(any(Graph.class), eq("A"), eq("F")))
                .thenReturn(successResult);

            mockMvc.perform(get("/api/dijkstra/shortest-path")
                    .param("source", "A")
                    .param("destination", "F"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.source").value("A"))
                .andExpect(jsonPath("$.destination").value("F"));
        }
    }
}
