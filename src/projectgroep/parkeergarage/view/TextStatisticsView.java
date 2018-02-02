package projectgroep.parkeergarage.view;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.function.Function;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import projectgroep.parkeergarage.logic.ParkeerLogic;

public class TextStatisticsView extends AbstractView {
    ParkeerLogic model;
    HashMap<String, Label> valueLabels = new HashMap<>();
    HashMap<String, Function> statistics;
    GridPane container = new GridPane() {{
        setVgap(20);
        setHgap(80);
        setPadding(new Insets(25, 25, 25, 25));
    }};

    public TextStatisticsView(ParkeerLogic model) {
        super();

        DecimalFormat df = new DecimalFormat("#.00");

        statistics = new LinkedHashMap<String, Function>() {{
            put("Beschikbare plaatsen", x -> model.getNumberOfOpenSpots());
            put("Aantal auto's in garage", x -> model.getAllCars().count());
            put("Aantal pashouders in garage", x -> model.getParkingPassCars().count());
            put("Aantal reservatie auto's in garage", x -> model.getReservationCars().count());
            put("Aantal reserveringen", x -> model.getReservationLogic().getReservations());
            put("Auto's in queue", x -> model.getEntranceCarQueue().carsInQueue());
            put("Pashouders in queue", x -> model.getEntrancePassQueue().carsInQueue());
            put("Totaal verdiend", x -> "€" + (df.format(model.getTotalEarned() + model.getParkingPassEarnings())));
            put("Vandaag verdiend", x -> "€" + (df.format(model.getDayEarnings())));
            put("Skippende auto's", x -> model.getSkipCount());
            put("Misgelopen omzet", x -> "€" + model.getSkipCount() * 4);
            put("Dagen", x -> model.getDay());
            put("Tijd", x -> model.getTime());
        }};

        this.model = model;

        getChildren().add(container);

        int i = 0; // Waarom, waarom, geen mapWithIndex?
        for (String statistic : statistics.keySet()) {
            Label keyLabel = new Label(statistic);
            Label valueLabel = new Label("");

            keyLabel.getStyleClass().add("statisticskey");

            container.add(keyLabel, 0, i);
            container.add(valueLabel, 1, i);
            valueLabels.put(statistic, valueLabel);
            i++;
        }
    }

    @Override
    public void updateView() {
        Platform.runLater(() ->
                statistics.forEach((statistic, f) ->
                        valueLabels.get(statistic).setText(f.apply(null).toString())));
    }
}
