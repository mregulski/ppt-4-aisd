package reshi.ppt.algorytmy.graphs.edge;

import reshi.ppt.algorytmy.graphs.vertex.Vertex;

/**
 * @author Marcin Regulski on 26.05.2016.
 */
public class DirectedWeightedEdge extends WeightedEdge {
    public DirectedWeightedEdge(Vertex from, Vertex to, double weight) {
        super(from, to, weight);
    }

}
