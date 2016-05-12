package reshi.ppt.algorytmy.BST.ranked;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import reshi.common.BasicLogger;
import reshi.common.TimesPlot;
import reshi.ppt.algorytmy.BST.BST;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * @author Marcin Regulski on 10.05.2016.
 */
public class TimesTest extends Application {
    private BasicLogger logger = new BasicLogger(System.out);
    private int maxDataSize = 10000;
    private int testsPerAverage = 100;
    private int step = 500;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Map<String, ArrayList<Double>> results = new HashMap<>();
        int resultSetSize = maxDataSize/step;
        results.put("select", new ArrayList<>(resultSetSize));
        results.put("random", new ArrayList<>(resultSetSize));
        results.put("treeSelect", new ArrayList<>(resultSetSize));
        results.put("treeCreate", new ArrayList<>(resultSetSize));

        for (int size = step; size <= maxDataSize; size+=step) {
            Map<String, Double> testResults = timeTest(size, testsPerAverage);
            testResults.forEach((k, v) -> results.get(k).add(testResults.get(k)));
        }

        List<Long> xRange = LongStream.rangeClosed(1, maxDataSize/step)
                .boxed()
                .map((x)->x*step)
                .collect(Collectors.toList());
        TimesPlot plot = new TimesPlot();
        results.forEach((key, v) -> plot.addSeries(key, xRange, results.get(key)));

        Scene scene = new Scene(plot.getChart(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Map<String, Double> timeTest(int dataSize, int testsPerAverage) {
        Random rng = new Random();

        ArrayList<Integer> dataCopy;
        // make sure there are no duplicates for tree tests
        Set<Integer> generated = new LinkedHashSet<>();
        while(generated.size() < dataSize) {
            Integer next = rng.nextInt(dataSize * 10);
            generated.add(next);
        }
        // convert set to a list for indexed access.
        List<Integer> data = new ArrayList<>(generated);

        RankedBST<Integer, String> premadeTree = new RankedBST<>();
        insertArray(data, premadeTree);

        Map<String, Double> results = new HashMap<>();
        Instant start, end;

        List<Integer> ranks = IntStream
                .rangeClosed(1, testsPerAverage)
                .boxed()
                .map((x)->rng.nextInt(dataSize-1)+1)
                .collect(Collectors.toList());


        start = Instant.now();
        for(int i = 0; i < testsPerAverage; i++) {
            RankedBST<Integer, Integer> aTree = new RankedBST<>();
            for (int j = 0; j < data.size(); j++) {
                aTree.insert(data.get(j), data.get(j));
            }
        }
        end = Instant.now();
        results.put("treeCreate", getAvgTime(start, end));

        start = Instant.now();
        for (int i = 0; i < testsPerAverage; i++) {
            premadeTree.select(ranks.get(i));
        }
        end = Instant.now();
        results.put("treeSelect", getAvgTime(start, end));

        start = Instant.now();
        for(int i = 0; i < testsPerAverage; i++) {
            dataCopy = new ArrayList<>(data);
            Select.select(dataCopy, ranks.get(i));
        }
        end = Instant.now();
        results.put("select", getAvgTime(start, end));

        start = Instant.now();
        for(int i = 0; i < testsPerAverage; i++) {
            dataCopy = new ArrayList<>(data);
            Select.randomSelect(dataCopy, ranks.get(i));
        }
        end = Instant.now();
        results.put("random", getAvgTime(start, end));
        System.out.print("\rDone size: " + dataSize);
        return results;
    }

    private double getAvgTime(Instant start, Instant end) {
        return Duration.between(start, end).toNanos()/(double) testsPerAverage;
    }

    private void insertArray(List<Integer> numbers, BST<Integer, String> tree) {
        Map<Integer, String> vals = new LinkedHashMap<>();
        for (int i = 0; i < numbers.size(); i++) {
            vals.put(numbers.get(i), "'#" + i + "'");
        }
        tree.insert(vals);
    }
}
