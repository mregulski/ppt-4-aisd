package reshi.ppt.algorytmy.graphs;

import reshi.ppt.algorytmy.graphs.edge.UndirectedWeightedEdge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Marcin Regulski on 27.05.2016.
 */
public class MST {
    private final List<UndirectedWeightedEdge> edges;
    private final double weight;

    public MST(Collection<UndirectedWeightedEdge> edges) {
        this.edges = new ArrayList<>(edges);
        this.weight = edges.stream().map(UndirectedWeightedEdge::getWeight).reduce((a,b)->a+b).orElse(0.0);
    }

    @Override
    public String toString() {
        return edges + " (" + weight + ")";
    }
}
