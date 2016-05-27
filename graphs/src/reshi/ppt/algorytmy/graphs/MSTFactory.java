package reshi.ppt.algorytmy.graphs;

import reshi.ppt.algorytmy.graphs.edge.UndirectedWeightedEdge;
import reshi.ppt.algorytmy.graphs.vertex.Vertex;

import java.util.*;

/**
 * @author Marcin Regulski on 26.05.2016.
 */
public class MSTFactory {

    private final List<Vertex> vertices;
    private final List<UndirectedWeightedEdge> edges;


    public MSTFactory(List<Vertex> vertices) {
        this.vertices = vertices;

        Set<UndirectedWeightedEdge> addedEdges = new HashSet<>();
        for(Vertex v : vertices) {
            v.getEdges().forEach((u, w) -> addedEdges.add(new UndirectedWeightedEdge(v, (Vertex) u, (Double) w)));
        }
        edges = new ArrayList<>(addedEdges);
        System.out.println("done");

    }

    public MST createKruskal() {
        Set<UndirectedWeightedEdge> mst = new HashSet<>(edges.size());
        DisjointSet<Vertex> reachable = new DisjointSet<>(vertices);

        Comparator<UndirectedWeightedEdge> edgeComparator = (e, f) -> Double.compare(e.getWeight(), f.getWeight());

        MinQueue<UndirectedWeightedEdge> queue = new MinQueue<>(edges, edgeComparator);

        while(!queue.isEmpty()) {
            UndirectedWeightedEdge e = queue.pop();
            if(!reachable.find(e.getFrom()).equals(reachable.find(e.getTo()))) {
                mst.add(e);
                reachable.union(e.getFrom(), e.getTo());
            }
        }


        return new MST(mst);
    }

    public MST createPrim() {
        Map<Vertex, Double> cost = new HashMap<>(vertices.size());
        Map<Vertex, Vertex> prev = new HashMap<>(vertices.size());

        // comparator to order vertices by their cost
        Comparator<Vertex> vertexComparator = (v, u) -> cost.get(v).equals(cost.get(u)) ? 0
                : cost.get(v) > cost.get(u)
                    ? 1
                    : -1;
        vertices.forEach((v) -> {
            cost.put(v, Double.POSITIVE_INFINITY);
            prev.put(v, null);
        });
        List<UndirectedWeightedEdge> mst = new ArrayList<>();
        Vertex start = vertices.get(0);
        cost.put(start, 0.0);

        // elements not in mst
        MinQueue<Vertex> queue = new MinQueue<>(vertices, vertexComparator);
        Set<Vertex> done = new HashSet<>(vertices.size());
        while(!queue.isEmpty()) {
            Vertex v = queue.pop();
            done.add(v);
            Map<Vertex, Double> edges = v.getEdges();
            edges.forEach((z, w) -> {
                if (!done.contains(z) && cost.get(z).compareTo(w) > 0) {
                    cost.put(z, w);
                    prev.put(z, v);
                }
            });

        }
        for(Vertex v : vertices) {
            Vertex previous = prev.get(v);
            if(previous != null) {
                mst.add(new UndirectedWeightedEdge(v, previous, (Double) v.getEdges().get(previous)));
            }
        }
        return new MST(mst);
    }

}
