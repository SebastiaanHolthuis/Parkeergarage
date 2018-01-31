package projectgroep.parkeergarage.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.logic.Settings;
import projectgroep.parkeergarage.main.Simulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingsView extends AbstractView {
    Simulator simulator;

    GridPane container = new GridPane() {{
        setVgap(8);
        setHgap(15);
        setPadding(new Insets(10, 10, 10, 10));
    }};

    Button restartButton = new Button("Restart") {{
        setOnMouseClicked(e -> restart());
        setMaxWidth(140);
    }};

    private HashMap<String, TypedTextField> fields = new HashMap<>();

    public SettingsView(ParkeerLogic model, Simulator simulator) {
        getChildren().add(container);

        HashMap<String, Object> settings = (model.settings).asMap();
        List<String> keyList = new ArrayList<>(settings.keySet());
        for (int i = 0; i < keyList.size(); i++) {
            Label label = new Label(keyList.get(i));
            TypedTextField field = new TypedTextField(settings.get(keyList.get(i)));

            field.setMaxWidth(140); // FIXME: beetje rommelig al die layout code

            container.add(label, 0, i);
            container.add(field, 1, i);
            fields.put(keyList.get(i), field);
        }

        container.add(restartButton, 1, keyList.size() + 1);
    }


    private HashMap<String, Object> getSettingsMap() {
        HashMap<String, Object> results = new HashMap<>();
        fields.forEach((setting, field) ->
                results.put(setting, field.getValue()));
        System.out.println(results.toString());
        return results;
    }

    private void restart() {
        simulator.restart(new Settings(getSettingsMap()));
    }

    @Override
    public void updateView() {

    }
}
