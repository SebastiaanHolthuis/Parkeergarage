package projectgroep.parkeergarage.view;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.Styler.LegendPosition;

import projectgroep.parkeergarage.logic.ParkeerLogic;

public class LineCarChartView extends AbstractView {

    private XYChart xyChart;
    private ArrayList yData = new ArrayList() {{
        add(0);
    }};

    public static final String SERIES_NAME = "Aantal Auto's";

    public LineCarChartView(ParkeerLogic model) {
        super(model);
        go();
    }

    void go() {
        JPanel chartPanel = new XChartPanel(makeChart());
        add(chartPanel);
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
        timer.scheduleAtFixedRate(chartUpdaterTask, 0, 2000);
    }

    XYChart makeChart() {
        // Create Chart
        xyChart = new XYChartBuilder()
                .width(600)
                .height(475)
                .theme(ChartTheme.Matlab)
                .title(SERIES_NAME)
                .build();

        xyChart.getStyler().setLegendPosition(LegendPosition.OutsideS);
        xyChart.addSeries(SERIES_NAME, null, yData);

        return xyChart;
    }

    public void updateView() {
        addDataPoint();
    }

    void addDataPoint() {
        yData.add(model.getAdHocCars().count());
    }

    public void updateData() {
        xyChart.updateXYSeries(SERIES_NAME, null, yData, null);
    }

}