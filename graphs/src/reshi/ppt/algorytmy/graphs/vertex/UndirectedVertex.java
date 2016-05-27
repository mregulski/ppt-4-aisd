package reshi.ppt.algorytmy.graphs.vertex;

/**
 * @author Marcin Regulski on 26.05.2016.
 */
public class UndirectedVertex<T> extends Vertex<T>{

    public UndirectedVertex(T data) {
        super(data);
    }


    protected void onAddEdge(Vertex from, double weight)
    {
        this.adjacent.put(from, weight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UndirectedVertex<?> vertex = (UndirectedVertex<?>) o;

        return data.equals(vertex.data);

    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

}
