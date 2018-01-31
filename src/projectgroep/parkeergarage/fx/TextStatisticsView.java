package projectgroep.parkeergarage.fx;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import projectgroep.parkeergarage.logic.ParkeerLogic;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;

public class TextStatisticsView extends FXView {
    ParkeerLogic model;
    HashMap<String, Label> valueLabels = new HashMap<>();
    HashMap<String, Function> statistics;
    GridPane container = new GridPane() {{
        setVgap(8);
        setHgap(50);
        setPadding(new Insets(10, 10, 10, 10));
    }};

    public TextStatisticsView(ParkeerLogic model) {
        super();

        statistics = new LinkedHashMap<String, Function>() {{
            put("Beschikbare plaatsen", x -> model.getNumberOfOpenSpots());
            put("Aantal auto's", x -> model.getAllCars().count());
            put("Aantal pashouders", x -> model.getParkingPassCars().count());
            put("Aantal reservaties", x -> model.getReservationCars().count());
            put("Auto's in queue", x -> model.getEntranceCarQueue().carsInQueue());
            put("Pashouders in queue", x -> model.getEntrancePassQueue().carsInQueue());
            put("Totaal verdiend", x -> "€" + model.getTotalEarned());
            put("Skippende auto's", x -> model.getSkipCount());
            put("Misgelopen omzet", x -> "€" + model.getSkipCount() * 4);
            put("Dagen", x -> model.getDay());
            put("Tijd", x -> model.getTime());
            put("Stappen terug", x -> model.history.stepsBack());
        }};

        this.model = model;

        getChildren().add(container);

        int i = 0; // Waarom, waarom, geen mapWithIndex?
        for (String statistic : statistics.keySet()) {
            Label keyLabel = new Label(statistic);
            Label valueLabel = new Label("");

            container.add(keyLabel, 0, i);
            container.add(valueLabel, 1, i);
            valueLabels.put(statistic, valueLabel);
            i++;
        }
    }

    @Override
    public void updateView() {
        Platform.runLater(() -> {
            statistics.forEach((statistic, f) -> {
                valueLabels.get(statistic).setText(f.apply(null).toString());
            });
        });
    }
}
