package projectgroep.parkeergarage.fx;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import projectgroep.parkeergarage.logic.ParkeerLogic;


public class LineCarChartView extends FXView {
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

        adhoc.getData().add(new XYChart.Data(0, 0));
        parkingPass.getData().add(new XYChart.Data(0, 0));
        reservation.getData().add(new XYChart.Data(0, 0));


        adhoc.setName("Adhoc Cars");
        parkingPass.setName("ParkingPass Cars");
        reservation.setName("Reservation Cars");

        lineChart.getData().addAll(adhoc, parkingPass, reservation);

        getChildren().addAll(lineChart);
    }


    @Override
    public void updateView() {
        addDataPoint();
    }

    void addDataPoint() {
        adhoc.getData().add(new XYChart.Data(model.tickNum(), model.getAdHocCars().count()));
        parkingPass.getData().add(new XYChart.Data(model.tickNum(), model.getParkingPassCars().count()));
        reservation.getData().add(new XYChart.Data(model.tickNum(), model.getReservationCars().count()));
    }

    public void updateData() {

    }
}