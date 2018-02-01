package projectgroep.parkeergarage.controller;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.main.Simulator;
import projectgroep.parkeergarage.view.*;

public class Controller {
    ParkeerLogic model;
    Simulator simulator;
    Scene scene;
    Pane timelinePane;

    public Controller(ParkeerLogic model, Simulator simulator, Scene scene) {
        this.model = model;
        this.simulator = simulator;
        this.scene = scene;

        attachComponentsToLayout();
        initializeCarPark();
        initializeTimeline();
        initializeSlider();
        addStepListeners();
        addToggleListener();
    }

    void attachComponentsToLayout() {
        attachComponentToLayout(new SettingsView(model, simulator), "#settings");
        attachComponentToLayout(new CarLineChartView(model), "#carlinechart");
        attachComponentToLayout(new QueueLineChartView(model), "#queuelinechart");
        attachComponentToLayout(new CarPieChartView(model), "#carpiechart");
        attachComponentToLayout(new TotalEarnedChartView(model), "#totalearnedchart");
        attachComponentToLayout(new TextStatisticsView(model), "#textstatistics");
    }

    void initializeCarPark() {
        CarParkView c = new CarParkView(model);
        ((ScrollPane) scene.lookup("#carpark")).setContent(c);
        model.addView(c);
    }

    void initializeSlider() {
        Slider tickPauseSlider = (Slider) scene.lookup("#tickPauseSlider");
        tickPauseSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                model.tickPause = 101 - newValue.intValue());
        tickPauseSlider.setValue(101 - model.tickPause);
    }

    void addStepListeners() {
        scene.lookup("#stepBack").setOnMouseClicked((e) -> model.stepBack(10));
        scene.lookup("#stepForward").setOnMouseClicked((e) -> model.tickMany(10));
    }

    void initializeTimeline() {
        timelinePane = (Pane) scene.lookup("#timelinepane");
        Slider timelineSlider = (Slider) scene.lookup("#timelineSlider");
//        tickPauseSlider.valueProperty().addListener((observable, oldValue, newValue) ->
//
    }

    void addToggleListener() {
        scene.lookup("#toggleRunning").setOnMouseClicked((e) -> {
            model.toggleRunning();
            ToggleButton source = (ToggleButton) e.getSource();
            source.setSelected(model.isRunning());
            source.setText(model.isRunning() ? "Running..." : "Run");

            timelinePane.setDisable(model.isRunning()); // FIXME: eigen view of controller?
        });
    }

    void attachComponentToLayout(AbstractView view, String lookupId) {
        ((Pane) scene.lookup(lookupId)).getChildren().add(view);
        model.addView(view);
    }

}
