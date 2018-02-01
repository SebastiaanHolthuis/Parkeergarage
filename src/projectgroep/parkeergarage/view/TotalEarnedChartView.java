package projectgroep.parkeergarage.view;

import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import projectgroep.parkeergarage.logic.ParkeerLogic;


public class TotalEarnedChartView extends AbstractView {
    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();

    Series totalEarned = new Series();

    LineChart<String, Number> lineChart = new LineChart(xAxis, yAxis) {{
        setTitle("Totaal verdiend");
    }};

    ParkeerLogic model;


    public TotalEarnedChartView(ParkeerLogic model) {
        super();
        this.model = model;

        totalEarned.setName(lineChart.getTitle());

        lineChart.getData().add(totalEarned);
        getChildren().addAll(lineChart);
    }

    @Override
    public void updateView() {
        Platform.runLater(() ->
                totalEarned.getData().add(new XYChart.Data(model.tickNum(), model.getTotalEarned())));
    }
}