package reshi.ppt.algorytmy.graphs;

import org.junit.Test;
import reshi.ppt.algorytmy.graphs.DisjointSet;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * @author Marcin Regulski on 26.05.2016.
 */
public class DisjointSetTest {
    @Test
    public void makeSet() throws Exception {
        String[] data = {"3","6","7","2","5","3","10","16","8","20","15"};
        List<String> list = Arrays.asList(data);
        DisjointSet<String> set = new DisjointSet<>(list);

        set.union("3", "6");
        assertThat(set.find("3"), is(equalTo(set.find("6"))));
        set.union("10", "16");
        assertThat(set.find("10"), is(equalTo(set.find("16"))));
        set.union("3", "7");
        set.union("7", "16");
        assertThat(set.find("10"), is(equalTo(set.find("7"))));
    }

    @Test
    public void find() throws Exception {

    }

    @Test
    public void union() throws Exception {

    }

}