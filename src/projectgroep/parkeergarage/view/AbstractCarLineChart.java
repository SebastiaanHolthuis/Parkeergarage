package projectgroep.parkeergarage.view;

import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import projectgroep.parkeergarage.logic.ParkeerLogic;

import java.util.HashMap;
import java.util.function.Function;


abstract class AbstractLineChartView extends AbstractView {
    String title;
    protected HashMap<String, Function> updaters;
    protected HashMap<String, XYChart.Series> series = new HashMap<>();

    private NumberAxis xAxis = new NumberAxis();
    private NumberAxis yAxis = new NumberAxis();
    protected LineChart<String, Number> lineChart = new LineChart(xAxis, yAxis);

    ParkeerLogic model;


    AbstractLineChartView(String title, ParkeerLogic model, HashMap<String, Function> updaters) {
        this.model = model;
        this.title = title;
        this.updaters = updaters;

        lineChart.setTitle(title);

        updaters.forEach((name, updater) -> {
            XYChart.Series newSeries = new XYChart.Series();
            newSeries.setName(name);
            lineChart.getData().add(newSeries);
            series.put(name, newSeries);
        });

        getChildren().add(lineChart);
    }

    @Override
    public void updateView() {
        Platform.runLater(() -> {
            updaters.forEach((name, updater) -> {
                series.get(name).getData().add(new XYChart.Data(model.tickNum(), updater.apply(model)));
            });
        });
    }
}
