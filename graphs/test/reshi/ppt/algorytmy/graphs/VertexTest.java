package reshi.ppt.algorytmy.graphs;

import org.junit.Test;
import reshi.ppt.algorytmy.graphs.vertex.UndirectedVertex;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * @author Marcin Regulski on 26.05.2016.
 */
public class VertexTest {
    @Test
    public void addEdge() throws Exception {

    }

    @Test
    public void testEquals() throws Exception {
        UndirectedVertex a = new UndirectedVertex<>("first");
        UndirectedVertex b = new UndirectedVertex<>("first");
        assertThat(a, equalTo(b));
    }

    @Test
    public void diffData_hasDiffHash() throws Exception {
        UndirectedVertex a = new UndirectedVertex<>("first");
        UndirectedVertex b = new UndirectedVertex<>("second");
        assertThat(a.hashCode(), not(equalTo(b.hashCode())));
    }

    @Test
    public void sameData_hasSameHash() throws Exception {
        UndirectedVertex a = new UndirectedVertex<>("first");
        UndirectedVertex b = new UndirectedVertex<>("first");
        assertThat(a.hashCode(), is(equalTo(b.hashCode())));
    }



}