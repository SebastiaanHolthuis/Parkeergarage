package projectgroep.parkeergarage.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import projectgroep.parkeergarage.SettingsRepository;
import projectgroep.parkeergarage.logic.Settings;
import projectgroep.parkeergarage.view.*;
import projectgroep.parkeergarage.logic.ParkeerLogic;

import java.io.IOException;


public class Simulator extends Application {
    ParkeerLogic model;
    Thread modelThread;

    Pane root;
    Scene scene;
    Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        actualStart(primaryStage);
    }

    void actualStart(Stage primaryStage) throws IOException {
        model = new ParkeerLogic(SettingsRepository.loadSettings());
        primaryStage.setTitle("Parkeergarage simulator - ITV1C groep C");

        stage = primaryStage;

        root = FXMLLoader.load(getClass().getClassLoader().getResource("projectgroep/parkeergarage/view/parkeergarage.fxml"));
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        attachComponentsToLayout();
        addControlListeners();

        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });

        modelThread = new Thread(() -> {
            model.run();
        });

        modelThread.start();
    }


    void attachComponentsToLayout() {
        attachComponentToLayout(new SettingsView(model, this), "#settings");
        attachComponentToLayout(new LineCarChartView(model), "#linecarchart");
        attachComponentToLayout(new TotalEarnedChartView(model), "#totalearnedchart");
        attachComponentToLayout(new TextStatisticsView(model), "#textstatistics");
        attachComponentToLayout(new CarParkView(model), "#carpark");
//        attachComponentToLayout(new LegendView(model), "#legend");
    }


    void addControlListeners() {
        addToggleListener();
        initializeSlider();
        scene.lookup("#stepBack").setOnMouseClicked((e) -> model.stepBack(10));
        scene.lookup("#stepForward").setOnMouseClicked((e) -> model.tickMany(10));
    }

    void initializeSlider() {
        Slider tickPauseSlider = (Slider) scene.lookup("#tickPauseSlider");
        tickPauseSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                model.tickPause = 100 - newValue.intValue());
        tickPauseSlider.setValue(100 - model.tickPause);
    }

    void addToggleListener() {
        scene.lookup("#toggleRunning").setOnMouseClicked((e) -> {
            model.toggleRunning();
            ToggleButton source = (ToggleButton) e.getSource();
            source.setSelected(model.isRunning());
            if (model.isRunning())
                source.setText("Running...");
            else
                source.setText("Run");
        });
    }

    void attachComponentToLayout(AbstractView view, String lookupId) {
        ((Pane) scene.lookup(lookupId)).getChildren().add(view);
        model.addView(view);
    }

    public void restart(Settings settings) {
        SettingsRepository.saveSettings(settings);
        model.pause();
        modelThread.stop();

        try {
            actualStart(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
