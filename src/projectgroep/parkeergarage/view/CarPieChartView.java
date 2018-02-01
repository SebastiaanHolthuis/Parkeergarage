package projectgroep.parkeergarage.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import projectgroep.parkeergarage.logic.ParkeerLogic;


public class CarPieChartView extends AbstractView {
    PieChart.Data adHoc = new PieChart.Data("Adhoc auto's", 0);
    PieChart.Data passHolder = new PieChart.Data("Pashouders", 0);
    PieChart.Data reservations = new PieChart.Data("Reservaties", 0);
    PieChart.Data openSpots = new PieChart.Data("Leeg", 0);

    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
            adHoc, passHolder, reservations, openSpots
    );

    PieChart pieChart = new PieChart(pieChartData) {{
        setTitle("Verdeling auto's");
    }};

    ParkeerLogic model;


    public CarPieChartView(ParkeerLogic model) {
        super();
        this.model = model;
        getChildren().addAll(pieChart);
    }

    @Override
    public void updateView() {
        Platform.runLater(() -> {
            adHoc.setPieValue(model.getAdHocCars().count());
            passHolder.setPieValue(model.getParkingPassCars().count());
            reservations.setPieValue(model.getReservationCars().count());
            openSpots.setPieValue(model.getNumberOfOpenSpots());
        });
    }
}