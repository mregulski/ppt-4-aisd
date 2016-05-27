package reshi.ppt.algorytmy.graphs.edge;

import reshi.ppt.algorytmy.graphs.vertex.Vertex;

/**
 * @author Marcin Regulski on 26.05.2016.
 */
public abstract class WeightedEdge {
    protected final Vertex from;
    protected final Vertex to;
    protected final double weight;

    public WeightedEdge(Vertex from, Vertex to, double weight) {
        this.to = to;
        this.weight = weight;
        this.from = from;
    }

    public double getWeight() {
        return weight;
    }

    public Vertex getFrom() {
        return from;
    }

    public Vertex getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "(" + from + ", " + to + ", " +
                weight +
                ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeightedEdge edge = (WeightedEdge) o;

        if (Double.compare(edge.weight, weight) != 0) return false;
        if (!from.equals(edge.from)) return false;
        return to.equals(edge.to);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = from.hashCode();
        result = 31 * result + to.hashCode();
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
