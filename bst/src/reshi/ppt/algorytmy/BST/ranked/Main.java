package reshi.ppt.algorytmy.BST.ranked;


import javafx.application.Application;
import reshi.common.BasicLogger;
import reshi.ppt.algorytmy.BST.BST;

import java.util.*;

/**
 * @author Marcin
 * @date 2016-05-01
 */
public class Main {
    static RankedBST<Integer, String> tree;
    static BasicLogger logger = new BasicLogger(System.out);


    public static void main(String[] args) {
//        manualTest();
//        if(true) {
//            return;
//        }
        Application.launch(TimesTest.class, null);
//        int dataSize = 1000;
//        int testsPerAverage = 1000;
//        int step = 100;
//        ArrayList<Double> avgSelect = new ArrayList<>(dataSize/step);
//        ArrayList<Double> avgRandom = new ArrayList<>(dataSize/step);
//        ArrayList<Double> avgTree = new ArrayList<>(dataSize/step);
//        for (int i = step; i <= dataSize; i+=step) {
//            Map<String,Double> avgs = timeTest(i, testsPerAverage);
//            avgSelect.add(avgs.get("select"));
//            avgRandom.add(avgs.get("random"));
//        }
//
//        List<Long> xRange = LongStream
//                .rangeClosed(1, dataSize/step)
//                .boxed()
//                .map((x)->x*step)
//                .collect(Collectors.toList());
//
//
//        XYSeries selectSeries = TimesPlot.createXYSeries("select", xRange, avgSelect);
//        XYSeries randomSeries = TimesPlot.createXYSeries("random", xRange, avgRandom);
//        TimesPlot plot = new TimesPlot("times", TimesPlot
//                .colllectSeries(selectSeries, randomSeries));
//
//        plot.pack();
//        RefineryUtilities.centerFrameOnScreen(plot);
//        plot.setVisible(true);

    }


    private static Map<String, Double> timeTest(int dataSize, int testsPerAverage) {
        Random rng = new Random();
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < dataSize; i++) {
            data.add(rng.nextInt(dataSize * 10));
        }

        RankedBST<Integer, String> bst = new RankedBST<>();
        insertArray(data, bst);

        long tSelect = 0;
        long tRandom = 0;
        long tTree = 0;
//        Instant start,end;
        long start, end;

        ArrayList<Integer> dataCopy;
//        start = Instant.now();
        start = System.currentTimeMillis();
        for(int i = 0; i < testsPerAverage; i++) {
            dataCopy = new ArrayList<>(data);
            Select.select(dataCopy, rng.nextInt(dataSize-1)+1);
        }
//        end = Instant.now();
        end = System.currentTimeMillis();
        tSelect = end-start;
        logger.logln("done select:",dataSize);
//        tSelect = Duration.between(start, end).getNano();
//        start = Instant.now();
        start = System.currentTimeMillis();
        for(int i = 0; i < testsPerAverage; i++) {
            dataCopy = new ArrayList<>(data);
            Select.randomSelect(dataCopy, rng.nextInt(dataSize-1)+1);
        }
//        end = Instant.now();
        end = System.currentTimeMillis();
        tRandom = end-start;
        logger.logln("done random:",dataSize);
//        tRandom = Duration.between(start, end).getNano();
        logger.log("","",".");
        logger.log("","","\r");
        Map<String, Double> results = new HashMap<>();
        results.put("select", tSelect/(double) testsPerAverage);
        results.put("random", tRandom/(double) testsPerAverage);
        return results;
    }

    private static void manualTest() {
        tree = new RankedBST<>();
        Integer[] numbers = {25,3,34,1,7,31,4,16,48,40,21,45,35,8,29,41,5,42,46,26,30,32,49,13};

        Map<Integer, String> vals = new LinkedHashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            vals.put(numbers[i], "'#" + i + "'");
        }

        tree.insert(vals);

//        tree.prettyPrint();
//        testSelect(15);
//        testRemove(1);
//        testSelect(15);
//        testRank(8);
//        testRemove(16);
//        testSelect(15);
//        testRank(8);
//        testRemove(40);
//        testSelect(15);
//        testRank(8);


        logger.logln("5th: ", Select.randomSelect(new ArrayList<>(Arrays.asList(numbers)), 5));
        logger.logln("rank of key=7:", tree.rank((RankedNode<Integer, String>) tree.search(7)));
//        logger.logln("5th: ",tree.select(5));
        tree.inOrder((x)-> logger.log(" ", "", x.getKey()));
        logger.logln("");
        logger.logln("1st:", Select.select(new ArrayList<>(Arrays.asList(numbers)), 1));
        logger.logln("4th:", Select.select(new ArrayList<>(Arrays.asList(numbers)), 4));
        logger.logln("12th:", Select.select(new ArrayList<>(Arrays.asList(numbers)), 12));
        logger.logln("22th:", Select.select(new ArrayList<>(Arrays.asList(numbers)), 22));
    }

    private static void testRemove(int i) {
        tree.delete(tree.search(i));
        logger.logln("\nremoving " + i);
        tree.prettyPrint();
    }

    private static void testSelect(int i) {
        logger.logln("select(" + i + ") = " + tree.select(i));
    }

    private static void testRank(int i) {
        RankedNode n = (RankedNode<Integer, String>)tree.search(i);
        logger.logln("rank(" + n+ ") = " + tree.rank(n));
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

    private static void insertArray(List<Integer> numbers, BST<Integer, String> tree) {
        Map<Integer, String> vals = new LinkedHashMap<>();
        for (int i = 0; i < numbers.size(); i++) {
            vals.put(numbers.get(i), "'#" + i + "'");
        }
        tree.insert(vals);
    }


}
