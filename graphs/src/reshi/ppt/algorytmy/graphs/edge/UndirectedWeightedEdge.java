package reshi.ppt.algorytmy.graphs.edge;

import reshi.ppt.algorytmy.graphs.vertex.Vertex;

/**
 * @author Marcin Regulski on 26.05.2016.
 */
public class UndirectedWeightedEdge extends WeightedEdge {

    public UndirectedWeightedEdge(Vertex from, Vertex to, double weight) {
        super(from, to, weight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UndirectedWeightedEdge edge = (UndirectedWeightedEdge) o;

        if (Double.compare(edge.weight, weight) != 0) return false;
        return (from.equals(((UndirectedWeightedEdge) o).from) && to.equals(((UndirectedWeightedEdge) o).to))
                || (from.equals(((UndirectedWeightedEdge) o).to) && to.equals(((UndirectedWeightedEdge) o).from));

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = 31 * (from.hashCode() + to.hashCode());
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
