package reshi.ppt.algorytmy.BST.ranked;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.GradientPaintTransformer;


import java.awt.*;
import java.util.List;

/**
 * @author Marcin Regulski on 09.05.2016.
 */
public class TimesPlot extends ApplicationFrame {
    XYDataset dataset;

    public TimesPlot(String title, XYDataset dataset) {
        super(title);
        this.dataset = dataset;
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new Dimension(1000, 540));
        setContentPane(chartPanel);
    }

    public JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart("Time", "data size", "time", dataset);
        chart.addSubtitle(new TextTitle("le subtitleeeeeeeeeeeeeeeeee o macarena"));
        chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot) chart.getPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        plot.setRenderer(0, new XYSplineRenderer());
        XYSplineRenderer renderer = (XYSplineRenderer) plot.getRenderer();
        chart.getLegend().setFrame(BlockBorder.NONE);
        return chart;
    }

    public static XYSeries createXYSeries(Comparable key, List<Long> x, List<Double> y) {
        if(x.size() != y.size()) {
            throw new IllegalArgumentException("x and y series must have the same length!");
        }
        XYSeries series = new XYSeries(key, true);
        for (int i = 0; i < x.size(); i++) {
            series.add(x.get(i), y.get(i));
        }
        return series;
    }

    public static XYSeriesCollection colllectSeries(XYSeries... series) {
        XYSeriesCollection collected = new XYSeriesCollection();
        for (XYSeries xy : series) {
            collected.addSeries(xy);
        }
        return collected;
    }

}
