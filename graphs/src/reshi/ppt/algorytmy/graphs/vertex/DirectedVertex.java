package reshi.ppt.algorytmy.graphs.vertex;

/**
 * @author Marcin Regulski on 26.05.2016.
 */
public class DirectedVertex<T> extends Vertex<T> {
    public DirectedVertex(T data) {
        super(data);
    }

    @Override
    protected void onAddEdge(Vertex from, double weight) {

    }
}
