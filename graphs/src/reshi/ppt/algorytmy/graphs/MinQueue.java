package reshi.ppt.algorytmy.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * @author Marcin Regulski on 24.05.2016.
 */
public class MinQueue<T> {

    public ArrayList<T> heap;
    private Comparator<T> comparator;

    List<T> getHeap() {
        return new ArrayList<>(heap);
    }

    /**
     * Create an empty queue
     */
    public MinQueue(Comparator<T> comparator){
        this.comparator = comparator;
        this.heap = new ArrayList<>();
    }

    public MinQueue(Collection<T> items, Comparator<T> comparator) {
        this.comparator = comparator;
        this.heap = new ArrayList<>(items);
        for(int i = (this.heap.size()-1)/2; i >= 0; i--) {
            heapify(i);
        }

    }

    public boolean isEmpty() {
        return heap.size() == 0;
    }

    private int parent(int idx) { return (idx-1)/2 ; }
    private int left(int idx) { return 2*idx+1; }
    private int right(int idx) { return 2*(idx+1); }

    public T parent(T el) { return heap.get(parent(heap.indexOf(el))); }
    public T left(T el) { return heap.get(left(heap.indexOf(el))); }
    public T right(T el) { return heap.get(right(heap.indexOf(el))); }

    public void insert(T elem) {
        int elemIdx = heap.size();
        heap.add(elem);
        while(elemIdx > 0 && comparator.compare(heap.get(elemIdx), heap.get(parent(elemIdx))) < 0) {
            T tmp = heap.get(parent(elemIdx));
            heap.set(parent(elemIdx), heap.get(elemIdx));
            heap.set(elemIdx, tmp);
            elemIdx = parent(elemIdx);
        }

    }

    public T pop() {
        T top =  heap.get(0);
        heap.set(0, heap.get(heap.size()-1));
        heapify(0);
        heap.remove(heap.size()-1);
        return top;
    }

    public void decreaseKey(T elem) {
        decreaseKey(heap.indexOf(elem));
    }

    private void decreaseKey(int index) {
        while(index > 0 && comparator.compare(heap.get(parent(index)), heap.get(index)) > 0) {
            T tmp = heap.get(index);
            heap.set(index, heap.get(parent(index)));
            heap.set(parent(index), tmp);
        }
    }

    public void heapify(int index) {
        int left = left(index);
        int right = right(index);
        int min = index;
        if(left < heap.size() && comparator.compare(heap.get(left), heap.get(min)) < 0) {
            min = left;
        }
        if(right < heap.size() && comparator.compare(heap.get(right), heap.get(min)) < 0) {
            min = right;
        }

        if(min != index) {
            T tmp = heap.get(index);
            heap.set(index, heap.get(min));
            heap.set(min, tmp);
            heapify(min);
        }
    }





}
