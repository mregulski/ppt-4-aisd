package reshi.common;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.sql.Time;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.Collections;
import java.util.List;

/**
 * @author Marcin Regulski on 10.05.2016.
 */
public class TimesPlot {

    private final NumberAxis xAxis;
    private final NumberAxis yAxis;

    public LineChart<Number, Number> getChart() {
        return lineChart;
    }

    private final LineChart<Number, Number> lineChart;

    private TemporalUnit timeUnit = ChronoUnit.NANOS;
    private int baseMargin = 35;
    private final double SCALE_DELTA = 1.1;

    public TimesPlot() {
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setOnScroll(event -> {
            if(event.getDeltaY() == 0) {
                return;
            }
            double scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA : 1 / SCALE_DELTA;
            lineChart.setScaleX(lineChart.getScaleX() * scaleFactor);
            lineChart.setScaleY(lineChart.getScaleY() * scaleFactor);

        });

        lineChart.setOnMousePressed(event -> {
            if(event.getClickCount() == 2) {
                lineChart.setScaleX(1.0);
                lineChart.setScaleY(1.0);
            }
        });
    }

    public TimesPlot setXLabel(String label) {
        xAxis.setLabel(label);
        return this;
    }

    public TimesPlot setYLabel(String label) {
        yAxis.setLabel(label);
        return this;
    }

    public TimesPlot setTitle(String title) {
        lineChart.setTitle(title);
        return this;
    }

    public TimesPlot setNodeMargin(int margin) {
        baseMargin = margin;
        return this;
    }

    public TimesPlot setUnit(TemporalUnit unit) {
        timeUnit = unit;
        return this;
    }



    public void addSeries(String name, List<Long> xValues, List<Double> yValues) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(name);
        ObservableList<XYChart.Data<Number, Number>> data = series.getData();
        Double min = Collections.min(yValues);
        Double max = Collections.max(yValues);
        int seriesIdx = lineChart.getData().size();
        for (int i = 0; i < yValues.size(); i++) {
            final XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(xValues.get(i), yValues.get(i));
            int topMargin = getTopMargin((Double) dataPoint.getYValue(), min, max);
            int leftMargin = 0;
            if(i <= 5) {
                leftMargin += baseMargin;
            }
            if(i <= 2) {
                leftMargin += baseMargin;
            }


            dataPoint.setNode(new HoverPaneNode(dataPoint, seriesIdx, topMargin, leftMargin));
            data.add(dataPoint);
        }
        lineChart.getData().add(series);
    }

    private int getTopMargin(Double yValue, Double min, Double max) {
        int topMargin;
        Double yThreshold = (double) timeUnit.getDuration().toNanos();
//        Double yThreshold = 0.1;
        if (yValue <=  min + yThreshold) {
            topMargin = -baseMargin;
        }
        else if (yValue >= min - yThreshold) {
            topMargin = baseMargin;
        }
        else topMargin = 0;
        return topMargin;
    }

}


class HoverPaneNode extends StackPane {
    final Label label;
    HoverPaneNode(XYChart.Data dataPoint, int seriesNumber, int topMargin, int leftMargin) {
        setPrefSize(13,13);
        label = createLabel(dataPoint, seriesNumber);
        setOnMouseEntered(event -> {
            getChildren().setAll(label);
            setCursor(Cursor.NONE);
            toFront();
            setMargin(label, new Insets(topMargin, 0, 0, leftMargin));
        });

        setOnMouseExited(event -> {
            getChildren().clear();
            setCursor(Cursor.CROSSHAIR);
        });
    }

    private Label createLabel(XYChart.Data dataPoint, int seriesNumber) {

        @SuppressWarnings("MalformedFormatString")
        final Label label = new Label(String.
                format("%d items\ntime: %.2f",
                        dataPoint.getXValue(),
                        dataPoint.getYValue())
        );
        label.getStyleClass()
                .addAll("default-color"+seriesNumber, "chart-line-symbol", "chart-series-line");
        label.setStyle("-fx-font-size: 13; -fx-font-weight: bold;");
        label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        return label;
    }


}
