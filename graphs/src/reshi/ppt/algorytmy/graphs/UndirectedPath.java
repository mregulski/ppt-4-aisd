package reshi.ppt.algorytmy.graphs;

import reshi.ppt.algorytmy.graphs.edge.UndirectedWeightedEdge;
import reshi.ppt.algorytmy.graphs.edge.WeightedEdge;
import reshi.ppt.algorytmy.graphs.vertex.Vertex;

import java.util.List;

/**
 * @author Marcin Regulski on 26.05.2016.
 */
public class UndirectedPath {
    private final List<UndirectedWeightedEdge> path;
    private final double weight;
    private final Vertex source;
    private final Vertex target;

    public UndirectedPath(List<UndirectedWeightedEdge> path) {
        this(path.get(0).getFrom(), path.get(path.size()-1).getTo(), path);
    }

    public UndirectedPath(Vertex source, Vertex target, List<UndirectedWeightedEdge> path) {
        this.source = source;
        this.target = target;
        this.path = path;
        weight = path.stream().map(WeightedEdge::getWeight).reduce((w1,w2) -> w1+w2).orElse(0.0);
    }


    @Override
    public String toString(){
        return  source + " <-> " + target + ": " + path + " (" + weight + ")";
    }
}
