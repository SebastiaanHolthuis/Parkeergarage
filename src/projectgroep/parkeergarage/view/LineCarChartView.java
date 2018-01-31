package projectgroep.parkeergarage.view;

import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import projectgroep.parkeergarage.logic.ParkeerLogic;


public class LineCarChartView extends AbstractView {
    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();

    Series adhoc = new Series();
    Series parkingPass = new Series();
    Series reservation = new Series();

    LineChart<String, Number> lineChart = new LineChart(xAxis, yAxis) {{
        setTitle("Aantal Auto's");
    }};

    ParkeerLogic model;


    public LineCarChartView(ParkeerLogic model) {
        super();

        this.model = model;

        setSeriesNames();

        lineChart.getData().addAll(adhoc, parkingPass, reservation);

        getChildren().addAll(lineChart);
    }

    void setSeriesNames() {
        adhoc.setName("Adhoc Cars");
        adhoc.getNode().getStyleClass().add("series-adhoc");

        parkingPass.setName("ParkingPass Cars");
        parkingPass.getNode().getStyleClass().add("series-parkingpass");

        reservation.setName("Reservation Cars");
        reservation.getNode().getStyleClass().add("series-reservation");

    }

    @Override
    public void updateView() {
        Platform.runLater((Runnable) () -> {
            adhoc.getData().add(new XYChart.Data(model.tickNum(), model.getAdHocCars().count()));
            parkingPass.getData().add(new XYChart.Data(model.tickNum(), model.getParkingPassCars().count()));
            reservation.getData().add(new XYChart.Data(model.tickNum(), model.getReservationCars().count()));
        });
    }
}