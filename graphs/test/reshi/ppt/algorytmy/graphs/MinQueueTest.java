package reshi.ppt.algorytmy.graphs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * @author Marcin Regulski on 26.05.2016.
 */
public class MinQueueTest {

    @Test
    public void insert() {
        Integer[] data = {3,6,7,2,5,3,10,16,8,20,15};
        List<Integer> nums = Arrays.asList(data);
        MinQueue<Integer> q = new MinQueue<>(nums, Comparator.<Integer>naturalOrder());
        System.out.println(q.getHeap());
        assertMinHeapProperty(q);
    }

    @Test
    public void pop_insert() {
        Integer[] data = {3,6,7,2,5,3,10,16,8,20,15};
        List<Integer> nums = Arrays.asList(data);
        MinQueue<Integer> q = new MinQueue<>(nums, Comparator.<Integer>naturalOrder());
        q.pop();
        q.insert(9);
        assertMinHeapProperty(q);
    }

    private int specialLen = 12;
    @Test
    public void decreaseKey() {
        String[] data = {"ala", "ma", "kota", "a", "dwa koty", "miewają", "zły humor"};
        ArrayList<String> list = new ArrayList<>(Arrays.asList(data));
        String special = "SUPERSPECIAL";
        list.add(special);
        MinQueue<String> q = new MinQueue<String>(list, (s1,s2)->len(s1)-len(s2));
        specialLen = 1;
        q.decreaseKey(special);
        System.out.println(q.getHeap());

    }

    private int len(String s) {
        if(s.equals("SUPERSPECIAL")) {
            System.out.println("this is unique");
            return specialLen;
        }
        else {
            return s.length();
        }
    }

    @Test
    public void popOrder() {
        Integer[] data = {3,6,7,2,5,3,10,16,8,20,15};
        List<Integer> nums = Arrays.asList(data);
        MinQueue<Integer> q = new MinQueue<>(nums, Comparator.<Integer>naturalOrder());
        List<Integer> pops = new ArrayList<>();
        while(!q.isEmpty()) {
            pops.add(q.pop());
        }
        System.out.println(pops);
        System.out.println(q.getHeap());
    }
    @Test
    public void pop() {
        Integer[] data = {20,5,10,12,3,6};
        List<Integer> nums = Arrays.asList(data);
        MinQueue<Integer> q = new MinQueue<>(nums, Comparator.<Integer>naturalOrder());
        Integer x = q.pop();
        assertMinHeapProperty(q);

    }

    private void assertMinHeapProperty(MinQueue<Integer> q) {
        List<Integer> heap = q.getHeap();

        for(int i = 0; i < heap.size(); i++ ) {
            Integer element = heap.get(i);
            Assert.assertThat("min-heap property", element, is(greaterThanOrEqualTo(q.parent(element))));
        }
    }

}