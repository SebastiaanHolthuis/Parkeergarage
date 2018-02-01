package projectgroep.parkeergarage.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import projectgroep.parkeergarage.logic.ParkeerLogic;


public class CarPieChartView extends AbstractView {
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
            new PieChart.Data("Iphone 5S", 13),
            new PieChart.Data("Samsung Grand", 25),
            new PieChart.Data("MOTO G", 10),
            new PieChart.Data("Nokia Lumia", 22));

    PieChart pieChart = new PieChart(pieChartData) {{
        setTitle("Verdeling auto's");
    }};

    ParkeerLogic model;


    public CarPieChartView(ParkeerLogic model) {
        super();

        this.model = model;

        setSeriesNames();

        getChildren().addAll(pieChart);
    }

    void setSeriesNames() {

    }

    @Override
    public void updateView() {
        Platform.runLater((Runnable) () -> {

        });
    }
}