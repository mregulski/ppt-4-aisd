package reshi.ppt.algorytmy.lcs;/**
 * @author Marcin Regulski on 12.05.2016.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import reshi.common.TimesPlot;
import reshi.common.util.StringUtil;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class TimesTest extends Application {

    private int maxTotalSize = 500;
    private int repeats = 100;
    private int step = 10;

    private static final Map<String, String> alphabets = new HashMap<>();
    private static final Random rng = new Random();
    static
    {
        alphabets.put("lower", "abcdefghijklmnopqrstuvwxyz");
        alphabets.put("alpha", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        alphabets.put("alphanum", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
        alphabets.put("keyboard", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789[]\\;',./!@#$%^&*()_+=-|}{:?><\"`~");
        alphabets.put("weird", "abcefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*(*)_+}{:\"<>?,./❤☀☆☂☻♞☯☭☢€→ąęśżźćłóńАаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЪъЫыЬьЭэЮюЯяกขฃษ฿ฬฝ๕๚");
    }
    @Override
    public void start(Stage primaryStage) {
        Map<String, List<Double>> results = new HashMap<>();

        alphabets.forEach((k,v) -> results.put(k, new ArrayList<>()));
        List<Long> xRange = LongStream.rangeClosed(1, maxTotalSize/step)
                .boxed()
                .map((x)->x*step)
                .collect(Collectors.toList());

        for(int totalSize = step; totalSize <= maxTotalSize; totalSize += step) {
            Map<String, Double> testResults = testTimes(totalSize, repeats);
            testResults.forEach((k, v) -> results.get(k).add(testResults.get(k)));
        }
        TimesPlot plot = new TimesPlot();
        plot
                .setTitle("Average times of LCS for random strings (" + repeats + " repeats)")
                .setYLabel("time" + "[ms]")
                .setXLabel("string length")
                .setUnit(ChronoUnit.MILLIS);
        results.forEach((k, v) -> plot.addSeries(k, xRange, results.get(k)));
        Scene scene = new Scene(plot.getChart(), 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private Map<String,Double> testTimes(int totalSize, int repeats) {
        Map<String, Double> results = new HashMap<>();
        alphabets.forEach((k,v) -> {

            double totalTime = 0.0;
            for(int i = 0; i < repeats; i++) {
                int stringSize = rng.nextInt(totalSize)+1;
                String first, second;
                first = StringUtil.randomString(stringSize, v);
                second = StringUtil.randomString(stringSize, v);

                Instant start = Instant.now();
                    new LongestCommonSubsequence(first, second).get();
                Instant end = Instant.now();

                totalTime += Duration.between(start,end).toMillis();
            }

            results.put(k, totalTime/(double)repeats);
        });

        System.out.print("\rDone: " + totalSize);
        return results;
    }

    private double getAvgTime(Instant start, Instant end) {
        return Duration.between(start, end).toNanos()/(double) repeats;
    }

}
