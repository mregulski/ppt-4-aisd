package reshi.ppt.algorytmy.graphs;

import org.junit.Test;
import reshi.ppt.algorytmy.graphs.edge.UndirectedWeightedEdge;
import reshi.ppt.algorytmy.graphs.vertex.UndirectedVertex;
import reshi.ppt.algorytmy.graphs.vertex.Vertex;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcin Regulski on 26.05.2016.
 */
public class MSTFactoryTest {
    @Test
    public void create() throws Exception {
        ArrayList<Vertex> vertices = new ArrayList<>(8);
        for(int i = 0; i < 8; i++) {
            vertices.add(new UndirectedVertex<>(i));
        }
        vertices.get(4).addEdge(vertices.get(5), 0.35);
        vertices.get(4).addEdge(vertices.get(7), 0.37);
        vertices.get(5).addEdge(vertices.get(7), 0.28);
        vertices.get(0).addEdge(vertices.get(7), 0.16);
        vertices.get(1).addEdge(vertices.get(5), 0.32);
        vertices.get(0).addEdge(vertices.get(4), 0.38);
        vertices.get(2).addEdge(vertices.get(3), 0.17);
        vertices.get(1).addEdge(vertices.get(7), 0.19);
        vertices.get(0).addEdge(vertices.get(2), 0.26);
        vertices.get(1).addEdge(vertices.get(2), 0.36);
        vertices.get(1).addEdge(vertices.get(3), 0.29);
        vertices.get(2).addEdge(vertices.get(7), 0.34);
        vertices.get(6).addEdge(vertices.get(2), 0.40);
        vertices.get(3).addEdge(vertices.get(6), 0.52);
        vertices.get(6).addEdge(vertices.get(0), 0.58);
        vertices.get(6).addEdge(vertices.get(4), 0.93);

        MSTFactory mst = new MSTFactory(vertices);
        MST tree2 = mst.createKruskal();
        System.out.println("Kruskal: " + tree2);
        MST tree = mst.createPrim();
        System.out.println("Prim: " + tree);


    }

}