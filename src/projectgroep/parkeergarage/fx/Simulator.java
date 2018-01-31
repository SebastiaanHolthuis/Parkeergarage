package projectgroep.parkeergarage.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import projectgroep.parkeergarage.SettingsRepository;
import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.logic.Settings;

import java.io.IOException;


public class Simulator extends Application {
    public static ParkeerLogic model = new ParkeerLogic(SettingsRepository.loadSettings());

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Parkeergarage simulator - ITV1C groep C");

        Pane root = FXMLLoader.load(getClass().getClassLoader().getResource("projectgroep/parkeergarage/fx/parkeergarage.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

//        model.run();
    }
}
