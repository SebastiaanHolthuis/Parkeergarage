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
import projectgroep.parkeergarage.controller.Controller;
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
    Controller controller;

    @Override
    public void start(Stage primaryStage) throws IOException {
        model = new ParkeerLogic(SettingsRepository.loadSettings());

        primaryStage.setTitle("Parkeergarage simulator - ITV1C groep C");

        stage = primaryStage;

        root = FXMLLoader.load(getClass().getClassLoader().getResource("projectgroep/parkeergarage/view/layout.fxml"));
        scene = new Scene(root);

        controller = new Controller(model, this, scene);

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });

        modelThread = new Thread(() -> {
            model.run();
        });

        modelThread.start();
    }

    public void restart(Settings settings) {
        SettingsRepository.saveSettings(settings);
        model.kill();
        modelThread.stop();

        try {
            start(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
