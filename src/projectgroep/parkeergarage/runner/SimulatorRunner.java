package projectgroep.parkeergarage.runner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import projectgroep.parkeergarage.SettingsRepository;
import projectgroep.parkeergarage.main.Simulator;

import java.io.IOException;

public class SimulatorRunner extends Application {
    public static void main(String[] args) {
        new Thread(() -> launch()).start();

        Simulator simulator = new Simulator(SettingsRepository.loadSettings());
        simulator.getParkeerLogic().run();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Hello World!");

        Pane root = FXMLLoader.load(getClass().getClassLoader().getResource("parkeergarage.fxml"));

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}