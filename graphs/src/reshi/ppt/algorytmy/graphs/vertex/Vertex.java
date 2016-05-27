package reshi.ppt.algorytmy.graphs.vertex;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Marcin Regulski on 24.05.2016.
 */
public abstract class Vertex<T> {

    protected final T data;
    protected Map<Vertex, Double> adjacent;

    public T getData() {
        return data;
    }

    public Vertex(T data) {
        if(data == null) {
            throw new IllegalArgumentException("Vertex data cannot be null.");
        }
        this.data = data;
        adjacent = new LinkedHashMap<>();
    }

    public void addEdge(Vertex to, double weight) {
        if(!this.adjacent.containsKey(to)) {
            this.adjacent.put(to, weight);
            to.onAddEdge(this, weight);
        }
    }

    protected abstract void onAddEdge(Vertex from, double weight);

    public Map<Vertex, Double> getEdges(){
        return new HashMap<>(adjacent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex<?> vertex = (Vertex<?>) o;

        return data.equals(vertex.data);

    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
