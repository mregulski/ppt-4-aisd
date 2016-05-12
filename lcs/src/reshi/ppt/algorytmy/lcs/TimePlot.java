package reshi.ppt.algorytmy.lcs;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

/**
 * @author Marcin Regulski on 12.05.2016.
 */
public class TimePlot {
    private final NumberAxis timeAxis;
    private final NumberAxis sizeAxis;
    private LineChart<Number, Number> chart;

    public TimePlot() {
        this.timeAxis = new NumberAxis();
        this.sizeAxis = new NumberAxis();
        this.chart = new LineChart<>(sizeAxis, timeAxis);
        sizeAxis.setLabel("Alphabets");
        timeAxis.setLabel("Time");

    }
}
