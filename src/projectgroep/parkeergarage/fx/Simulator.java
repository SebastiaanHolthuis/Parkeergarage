package projectgroep.parkeergarage.fx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import projectgroep.parkeergarage.SettingsRepository;
import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.logic.Settings;
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

    void addSwingComponents() {
        addSwingComponent(new TextStatisticsView(model), "#textstatistics");
        addSwingComponent(new SettingsView(model, this), "#settings");
//        addSwingComponent(new CarParkView(model), "#carpark");
//        addSwingComponent(new LegendView(model), "#legend");
    }

    void addControlListeners() {
        scene.lookup("#play").setOnMouseClicked((e) -> model.play());
        scene.lookup("#pause").setOnMouseClicked((e) -> model.pause());
        scene.lookup("#stepBack").setOnMouseClicked((e) -> model.stepBack(10));
        scene.lookup("#stepForward").setOnMouseClicked((e) -> model.tickMany(10));

        Slider tickPauseSlider = (Slider) scene.lookup("#tickPauseSlider");
        tickPauseSlider.setOnMouseClicked((e) -> model.tickPause = (int) (100 - tickPauseSlider.getValue()));
        tickPauseSlider.setValue(100 - model.tickPause);
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

    public void restart(Settings s) {
        System.out.println(s);
    }
}
