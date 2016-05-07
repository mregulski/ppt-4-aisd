package reshi.ppt.algorytmy.augmented;

import reshi.ppt.algorytmy.BST.BSTNode;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

/**
 * @author Marcin
 * @date 2016-05-01
 */
public class Main {
    static RankedBST<Integer, String> tree;
    public static void main(String[] args) {
        tree = new RankedBST<>();
        int[] numbers = {25,3,34,1,7,31,4,16,48,40,21,45,35,8,29,41,5,42,46,25,30,32,49};
        Map<Integer, String> vals = new LinkedHashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            vals.put(numbers[i], "\'#" + i + "'");
        }

        tree.insert(vals);
        tree.prettyPrint();
        testSelect(15);
        testRemove(1);
        testSelect(15);
        testRank(8);
        testRemove(16);
        testSelect(15);
        testRank(8);
        testRemove(40);
        testSelect(15);
        testRank(8);
    }

    private static void testRemove(int i) {
        tree.delete(tree.search(i));
        logl("\nremoving " + i);
        tree.prettyPrint();
    }

    private static void testSelect(int i) {
        logl("select(" + i + ") = " + tree.select(i));
    }

    private static void testRank(int i) {
        RankedNode n = (RankedNode<Integer, String>)tree.search(i);
        logl("rank(" + n+ ") = " + tree.rank(n));
    }

    private static String randomString(int length) {
        StringBuilder x = new StringBuilder("\"");
        Random r = new Random();
        for (int i = 0; i < length; i++) {
            x.append(Integer.toString(r.nextInt(32), 32));
        }
        x.append("\"");
        return x.toString();
    }

    private static void logl(Object x) {
        if (x != null) {
            log(x);
            System.out.println();
        }
    }

    private static void log(Object x) {
        if (x != null) {
            System.out.print(x.toString());
        }
    }
}
