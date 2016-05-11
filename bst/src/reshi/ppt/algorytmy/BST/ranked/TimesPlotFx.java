package reshi.ppt.algorytmy.BST.ranked;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.Collections;
import java.util.List;

/**
 * @author Marcin Regulski on 10.05.2016.
 */
public class TimesPlotFx {

    private final NumberAxis xAxis;
    private final NumberAxis yAxis;

    public LineChart<Number, Number> getChart() {
        return lineChart;
    }

    private final LineChart<Number, Number> lineChart;
    private final int baseMargin = 35;


    public TimesPlotFx() {
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        lineChart = new LineChart<>(xAxis, yAxis);
        initialize();
    }

    private void initialize() {
        xAxis.setLabel("data size");
        yAxis.setLabel("time [ns]");

    }

    public void addSeries(String name, List<Long> xValues, List<Double> yValues) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(name);
        ObservableList<XYChart.Data<Number, Number>> data = series.getData();
        Double min = Collections.min(yValues);
        Double max = Collections.max(yValues);

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


            dataPoint.setNode(new HoverPaneNode(dataPoint, topMargin, leftMargin));
            data.add(dataPoint);
        }
        lineChart.getData().add(series);
    }

    private int getTopMargin(Double yValue, Double min, Double max) {
        int topMargin;
        Double yThreshold = 100000.0;
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
    HoverPaneNode(XYChart.Data dataPoint, int topMargin, int leftMargin) {
        setPrefSize(15,15);
        label = createLabel(dataPoint);
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

    private Label createLabel(XYChart.Data dataPoint) {

        @SuppressWarnings("MalformedFormatString")
        final Label label = new Label(String.
                format("%d items\ntime: %.2fns",
                        dataPoint.getXValue(),
                        dataPoint.getYValue())
        );
        label.getStyleClass().addAll("default-color2", "chart-line-symbol", "chart-series-line");
        label.setStyle("-fx-font-size: 13; -fx-font-weight: bold; ");
        label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        return label;
    }


}
