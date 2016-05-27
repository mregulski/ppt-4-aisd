package reshi.ppt.algorytmy.graphs;

import java.util.*;


/**
 * @author Marcin Regulski on 26.05.2016.
 */
public class DisjointSet<T> {

    private Map<T, Node<T>> items;

    public DisjointSet(Collection<T> items) {
        this.items = new HashMap<>(items.size());
        items.forEach(this::makeSet);
    }

    public DisjointSet() {
        this.items = new HashMap<>();
    }

    public void makeSet(T element) {
        if(!items.keySet().contains(element)) {
            Node<T> node = new Node<>(element);
            items.put(element, node);
        }
    }

    public T find(T element) {
        return find(items.get(element)).getData();
    }

    public void union(T first, T second) {
        union(items.get(first), items.get(second));
    }

    private void union(Node<T> first, Node<T> second) {
        Node<T> x = find(first);
        Node<T> y = find(second);
        if(x.equals(y)) { return; }
        if(x.rank > y.rank) {
            y.parent = x;
        }
        else {
            x.parent = y;
            if(x.rank == y.rank) {
                y.rank++;
            }
        }
    }

    /**
     * return node that is root of looked-for node's set with path compression
     * (hook the looked-for node directly to the root)
     * @param node node whose parent is being searched
     * @return set identity of node (set's root element)
     */
    private Node<T> find(Node<T> node) {
        if(node != node.parent) {
            node.parent = find(node.parent);
        }
        return node.parent;
//        while(node != node.parent) {
//            node = node.parent;
//        }
//        return node;
    }

    class Node<T> {
        Node<T> parent;
        final T data;

        T getData() {
            return this.data;
        }
        int rank;
        Node(T data) {
            this.rank = 0;
            this.data = data;
            this.parent = this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node<?> node = (Node<?>) o;

            return data.equals(node.data);

        }

        @Override
        public int hashCode() {
            return data.hashCode();
        }
    }
}
