package projectgroep.parkeergarage.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import projectgroep.parkeergarage.SettingsRepository;
import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.logic.Settings;
import projectgroep.parkeergarage.view.*;

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

        root = FXMLLoader.load(getClass().getClassLoader().getResource("projectgroep/parkeergarage/view/layout.fxml"));
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
        attachComponentToLayout(new CarLineChartView(model), "#carlinechart");
        attachComponentToLayout(new CarPieChartView(model), "#carpiechart");
        attachComponentToLayout(new TotalEarnedChartView(model), "#totalearnedchart");
        attachComponentToLayout(new TextStatisticsView(model), "#textstatistics");

        CarParkView c = new CarParkView(model);
        ((ScrollPane) scene.lookup("#carpark")).setContent(c);
        model.addView(c);
//        stage.widthProperty().addListener((obs, oldVal, newVal) -> c.resizeScrollPane());
//        stage.heightProperty().addListener((obs, oldVal, newVal) -> c.resizeScrollPane());
//        stage.setOnShown(((e) -> c.resizeScrollPane()));
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
                model.tickPause = 101 - newValue.intValue());
        tickPauseSlider.setValue(101 - model.tickPause);
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
        model.kill();
        modelThread.stop();

        try {
            actualStart(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
