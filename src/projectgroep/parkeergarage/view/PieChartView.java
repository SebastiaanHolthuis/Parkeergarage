package projectgroep.parkeergarage.view;

import java.awt.Dimension;
import java.awt.Image;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.Styler.LegendPosition;

import projectgroep.parkeergarage.logic.ParkeerLogic;

public class PieChartView extends AbstractView {

    private XYChart xyChart;

    private List<Double> yData;
    public static final String SERIES_NAME = "Omzet in euro's";

    public PieChartView(ParkeerLogic model) {
        super(model);
        go();
    }

    private void go() {
        JPanel chartPanel = new XChartPanel(getChart());
//        add(chartPanel);
        chartPanel.validate();

        // Simulate a data feed
        TimerTask chartUpdaterTask = new TimerTask() {

            @Override
            public void run() {
                updateData();

                javax.swing.SwingUtilities.invokeLater(() -> chartPanel.repaint());
            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(chartUpdaterTask, 0, 500);
    }

    public XYChart getChart() {
        yData = getRandomData(5);

        // Create Chart
        xyChart = new XYChartBuilder().width(600).height(475).theme(ChartTheme.Matlab).title("Omzet per dag").build();
        xyChart.getStyler().setLegendPosition(LegendPosition.OutsideS);
        xyChart.addSeries(SERIES_NAME, null, yData);

        return xyChart;
    }

    public void updateData() {
        // Get some new data
        List<Double> newData = getRandomData(1);

        yData.addAll(newData);

        // Limit the total number of points
        while (yData.size() > 7) {
            yData.remove(0);
        }

        xyChart.updateXYSeries(SERIES_NAME, null, yData, null);
    }

    private List<Double> getRandomData(int numPoints) {

        List<Double> data = new CopyOnWriteArrayList<Double>();
        for (int i = 0; i < numPoints; i++) {
            data.add(Math.random() * 100);
        }
        return data;
    }

}