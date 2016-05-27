package reshi.ppt.algorytmy.graphs;


import reshi.ppt.algorytmy.graphs.vertex.Vertex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcin Regulski on 27.05.2016.
 */
public class Main {
    public static void main(String[] args) {
        boolean directed = false;

        if(args.length < 2) {
            System.out.println("graphs <directed|undirected> <sourcefile>");
        }
        if(args[0].equals("directed")) {
            directed = true;
        }
        else if(args[0].equals("undirected")) {
            directed = false;
        }
        else {
            System.out.println("Graph type must be 'directed' or 'undirected'");
            System.exit(1);
        }
        List<EdgeData> edges = readGraph(args[1]);

        if(directed) {
            int sourceId = 0;
            try {
                sourceId = Integer.parseInt(args[2]);
            }
            catch (Exception e) {}
            List<Vertex> graph = GraphBuilder.buildDirectedGraph(edges);
            ShortestPath sp = new ShortestPath(graph);
            List<DirectedPath> paths = sp.dijkstra(graph.get(sourceId));
            paths.forEach(System.out::println);

        }
        else {
            List<Vertex> graph = GraphBuilder.buildUndirectedGraph(edges);
            MSTFactory mst = new MSTFactory(graph);
            MST prim = mst.createPrim();
            System.out.println("Prim: " + prim);
            MST kruskal = mst.createKruskal();
            System.out.println("Kruskal: " + kruskal);
        }
    }

    private static List<EdgeData> readGraph(String filename) {
        Path filePath = Paths.get(filename);
        List<EdgeData> edges = new ArrayList<>();
        try (InputStream in = Files.newInputStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            int vertexCount = Integer.parseInt(reader.readLine());
            int edgeCount = Integer.parseInt(reader.readLine());
            String line = reader.readLine();
            while(line != null) {
                String[] data = line.split(" ");
                EdgeData e = new EdgeData();
                e.sourceId = Integer.parseInt(data[0]);
                e.targetId = Integer.parseInt(data[1]);
                e.weight = Double.parseDouble(data[2]);
                edges.add(e);
                line = reader.readLine();
            }
        }
        catch (IOException ex) {
            System.err.println(ex);
            System.exit(1);
        }

        return edges;
    }


}

class EdgeData {
    public int sourceId;
    public int targetId;
    public double weight;
}