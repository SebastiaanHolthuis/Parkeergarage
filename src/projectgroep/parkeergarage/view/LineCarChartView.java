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
    private XYChart xyChart1;
    private ArrayList addHOC = new ArrayList() {{
        add(0);
    }};
    private ArrayList parkingPass = new ArrayList() {{
        add(0);
    }};

    private ArrayList reservationCar = new ArrayList() {{
        add(0);
    }};

    public static final String SERIES_NAME = "AddHOC Cars";
    public static final String SERIES_NAME1 = "ParkingPass Cars";
    public static final String SERIES_NAME2 = "Reservation Cars";

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
        timer.scheduleAtFixedRate(chartUpdaterTask, 0, 100);
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
        xyChart.addSeries(SERIES_NAME, null, addHOC);
        xyChart.addSeries(SERIES_NAME2, null, reservationCar);
        xyChart.addSeries(SERIES_NAME1, null, parkingPass);


        return xyChart;
    }


    @Override
    public void updateView() {
        addDataPoint();
    }

    void addDataPoint() {
        addHOC.add(model.getAdHocCars().count());
        parkingPass.add(model.getParkingPassCars().count());
        reservationCar.add(model.getReservationCars().count());
    }

    public void updateData() {
        xyChart.updateXYSeries(SERIES_NAME, null, addHOC, null);
        xyChart.updateXYSeries(SERIES_NAME1, null, parkingPass, null);
        xyChart.updateXYSeries(SERIES_NAME2, null, reservationCar, null);
    }

}