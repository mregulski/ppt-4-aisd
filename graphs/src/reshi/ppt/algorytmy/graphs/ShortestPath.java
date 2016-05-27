package reshi.ppt.algorytmy.graphs;

import reshi.ppt.algorytmy.graphs.edge.DirectedWeightedEdge;
import reshi.ppt.algorytmy.graphs.vertex.Vertex;

import java.util.*;

/**
 * @author Marcin Regulski on 26.05.2016.
 */
public class ShortestPath {

    private List<Vertex> vertices;

    public ShortestPath(Collection<Vertex> vertices) {
        this.vertices = new ArrayList<>(vertices);
    }

    public List<DirectedPath> dijkstra(Vertex source) {
        if(source == null) {
            source = vertices.get(0);
        }
        Map<Vertex, Double> dist = new HashMap<>(vertices.size());
        Map<Vertex, Vertex> prev = new HashMap<>(vertices.size());
        List<DirectedPath> paths = new ArrayList<>();
        Comparator<Vertex> vertexComparator = (v, u) -> dist.get(v).equals(dist.get(u)) ? 0
                : dist.get(v) > dist.get(u)
                ? 1
                : -1;
        vertices.forEach(v -> {
            dist.put(v, Double.POSITIVE_INFINITY);
            prev.put(v, null);
        });

        dist.put(source, 0.0);

        MinQueue<Vertex> queue = new MinQueue<Vertex>(vertices, vertexComparator);
        while(!queue.isEmpty()) {
            Vertex u = queue.pop();
            Map<Vertex, Double> edges = u.getEdges();
            edges.forEach((v, w) -> {
                if(dist.get(v) > dist.get(u) + w) {
                    dist.put(v, dist.get(u) + w);
                    prev.put(v, u);
                        queue.decreaseKey(v);
                }
            });
        }

        for(Vertex target : vertices) {
            Vertex previous, current;
            current = target;
            List<DirectedWeightedEdge> path = new LinkedList<>();
            while (current != null) {
                previous = prev.get(current);
                if (previous != null) {
                    DirectedWeightedEdge edge = new DirectedWeightedEdge(previous,
                            current, (Double) previous.getEdges().get(current));
                    path.add(0, edge);
                }
                current = previous;
            }
            paths.add(new DirectedPath(source, target, path));
        }

        return paths;
    }

}
