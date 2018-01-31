package projectgroep.parkeergarage.fx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import projectgroep.parkeergarage.SettingsRepository;
import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.view.*;

import javax.swing.*;
import java.io.IOException;


public class Simulator extends Application {
    public static ParkeerLogic model = new ParkeerLogic(SettingsRepository.loadSettings());
    TextStatisticsView textStatisticsView;
    Pane root;
    Scene scene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Parkeergarage simulator - ITV1C groep C");

        root = FXMLLoader.load(getClass().getClassLoader().getResource("projectgroep/parkeergarage/fx/parkeergarage.fxml"));
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        addFXComponents();
        addSwingComponents();
        addControlListeners();

        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });

        new Thread(() -> {
            model.run();
        }).start();
    }

    void addFXComponents() {

        addFXComponent(new projectgroep.parkeergarage.fx.Settings(model), "#settings");
        addFXComponent(new LineCarChartView(model), "#linecarchart");
        addFXComponent(new TotalEarnedChartView(model), "#totalearnedchart");
        addFXComponent(new TextStatisticsView(model), "#textstatistics");
        addFXComponent(new CarParkView(model), "#carpark");
    }

    void addSwingComponents() {
//        addSwingComponent(new TextStatisticsView(model), "#textstatistics");
//        addSwingComponent(new LegendView(model), "#legend");
    }

    void addControlListeners() {
        addToggleLister();
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

    void addToggleLister() {
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

    void addFXComponent(FXView view, String lookupId) {
        ((Pane) scene.lookup(lookupId)).getChildren().add(view);
        model.addView(view);
    }

    void addSwingComponent(View view, String lookupId) {
        SwingNode swingNode = new SwingNode();

        SwingUtilities.invokeLater(() -> {
            swingNode.setContent((JComponent) view);
        });

        model.addView(view);
        Pane pane = (Pane) scene.lookup(lookupId);
        pane.getChildren().add(swingNode);
    }

    public void restart(projectgroep.parkeergarage.logic.Settings settings) {
        SettingsRepository.saveSettings(settings);
        model.pause();

        Thread t = new Thread(() -> {
//            createInstances(settings);
//            initializeFrame();
            model.run();
        });

        t.start();
    }
}
