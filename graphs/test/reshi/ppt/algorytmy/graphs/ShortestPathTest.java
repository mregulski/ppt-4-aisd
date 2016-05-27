package reshi.ppt.algorytmy.graphs;

import org.junit.Test;
import reshi.ppt.algorytmy.graphs.edge.DirectedWeightedEdge;
import reshi.ppt.algorytmy.graphs.vertex.DirectedVertex;
import reshi.ppt.algorytmy.graphs.vertex.UndirectedVertex;
import reshi.ppt.algorytmy.graphs.vertex.Vertex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Marcin Regulski on 26.05.2016.
 */
public class ShortestPathTest {
    @Test
    public void dijkstra() throws Exception {
        ArrayList<Vertex> vertices = new ArrayList<>(8);
        for(int i = 0; i < 8; i++) {
            vertices.add(new DirectedVertex<>(i));
        }
        vertices.get(4).addEdge(vertices.get(5), 0.35);
        vertices.get(5).addEdge(vertices.get(4), 0.35);
        vertices.get(4).addEdge(vertices.get(7), 0.37);
        vertices.get(5).addEdge(vertices.get(7), 0.28);
        vertices.get(7).addEdge(vertices.get(5), 0.28);
        vertices.get(5).addEdge(vertices.get(1), 0.32);
        vertices.get(0).addEdge(vertices.get(4), 0.38);
        vertices.get(0).addEdge(vertices.get(2), 0.26);
        vertices.get(7).addEdge(vertices.get(3), 0.39);
        vertices.get(1).addEdge(vertices.get(3), 0.29);
        vertices.get(2).addEdge(vertices.get(7), 0.34);
        vertices.get(6).addEdge(vertices.get(2), 0.40);
        vertices.get(3).addEdge(vertices.get(6), 0.52);
        vertices.get(6).addEdge(vertices.get(0), 0.58);
        vertices.get(6).addEdge(vertices.get(4), 0.93);


        ShortestPath p = new ShortestPath(vertices);

        List<DirectedPath> paths = p.dijkstra(vertices.get(0));
        for(DirectedPath path : paths) {
            System.out.println(path);
        }
    }

}