package projectgroep.parkeergarage.fx;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import projectgroep.parkeergarage.SettingsRepository;
import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.logic.Settings;
import projectgroep.parkeergarage.view.SettingsView;
import projectgroep.parkeergarage.view.SwingView;
import projectgroep.parkeergarage.view.TextStatisticsView;

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

//        model.run();
        addSwingComponents();
    }

    void addSwingComponents() {
        addSwingComponent(new TextStatisticsView(model), "#textstatistics");
        addSwingComponent(new SettingsView(model, this), "#settings");
    }

    void addSwingComponent(SwingView view, String lookupId) {
        SwingNode swingNode = new SwingNode();

        SwingUtilities.invokeLater(() -> {
            swingNode.setContent((JComponent) view);
        });

        model.addView(view);

        AnchorPane pane = (AnchorPane) scene.lookup(lookupId);
        pane.getChildren().add(swingNode);
    }

    public void restart(Settings s) {
        System.out.println(s);
    }
}
