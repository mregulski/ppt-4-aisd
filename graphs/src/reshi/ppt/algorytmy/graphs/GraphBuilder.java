package reshi.ppt.algorytmy.graphs;

import reshi.ppt.algorytmy.graphs.vertex.DirectedVertex;
import reshi.ppt.algorytmy.graphs.vertex.UndirectedVertex;
import reshi.ppt.algorytmy.graphs.vertex.Vertex;

import java.util.*;

/**
 * @author Marcin Regulski on 27.05.2016.
 */
public class GraphBuilder {
    public static List<Vertex> buildDirectedGraph(List<EdgeData> edges) {
        Map<Integer, Vertex<Integer>> vertices = new HashMap<>();
        for(EdgeData edge : edges) {
            vertices.put(edge.sourceId, new DirectedVertex<>(edge.sourceId));
            vertices.put(edge.targetId, new DirectedVertex<>(edge.targetId));
        }
        for(Integer id : vertices.keySet()) {
            Vertex v = vertices.get(id);
            edges.stream()
                    .filter(e->e.sourceId == id)
                    .forEach(e->v.addEdge(vertices.get(e.targetId), e.weight));
        }
        List<Vertex> graph = new ArrayList<>();
        vertices.forEach((i,v) -> {
            System.out.println(v.getData() + ": " + v.getEdges());
            graph.add(v);
        });

        return graph;
    }

    public static List<Vertex> buildUndirectedGraph(List<EdgeData> edges) {
        Map<Integer, Vertex<Integer>> vertices = new HashMap<>();
        for(EdgeData edge : edges) {
            vertices.put(edge.sourceId, new UndirectedVertex<>(edge.sourceId));
            vertices.put(edge.targetId, new UndirectedVertex<>(edge.targetId));
        }
        for(Integer id : vertices.keySet()) {
            Vertex v = vertices.get(id);
            edges.stream()
                    .filter(e->e.sourceId == id)
                    .forEach(e->v.addEdge(vertices.get(e.targetId), e.weight));
        }
        List<Vertex> graph = new ArrayList<>();
        vertices.forEach((i,v) -> {
            System.out.println(v.getData() + ": " + v.getEdges());
            graph.add(v);
        });

        return graph;
    }
}
