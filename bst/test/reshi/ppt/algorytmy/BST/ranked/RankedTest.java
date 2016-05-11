package reshi.ppt.algorytmy.BST.ranked;

import org.junit.Assert;
import org.junit.Test;
import reshi.ppt.algorytmy.BST.BST;

import java.util.*;

/**
 * @author Marcin Regulski on 08.05.2016.
 */
public class RankedTest {
    Random rng = new Random();

    @Test
    public void randomSelect_rankOfRandomSameAsIndexInSorted() {
        for (int i = 0; i < 10000; i++) {
            ArrayList<Integer> data = new ArrayList<>();
            for (int j = 0; j < 1000; j++) {
                data.add(rng.nextInt(50000));
            }

            ArrayList<Integer> sorted = new ArrayList<>(data);
            sorted.sort(null);
            int rank;
            rank = rng.nextInt(data.size()-1)+1;
            int selected = Select.randomSelect(new ArrayList<>(data), rank);
            Assert.assertEquals((int) sorted.get(rank-1), selected);
        }
    }

    @Test
    public void select_rankOfRandomSameAsIndexInSorted() {
        ArrayList<Integer> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(rng.nextInt(5000));
        }

        ArrayList<Integer> sorted = new ArrayList<>(data);
        sorted.sort(null);
        int rank;
        for (int i = 0; i < 2500; i++) {
            rank = rng.nextInt(data.size()-1)+1;
            int selected = Select.select(new ArrayList<>(data), rank);
            Assert.assertEquals((int) sorted.get(rank-1), selected);
        }

    }

}