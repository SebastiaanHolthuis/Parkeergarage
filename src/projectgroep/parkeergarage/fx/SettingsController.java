package projectgroep.parkeergarage.fx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingsController {
    @FXML
    GridPane container;

    private HashMap<String, TypedTextField> fields = new HashMap<>();

    public void initialize() {
        HashMap<String, Object> settings = (Simulator.model.settings).asMap();
        List<String> keyList = new ArrayList<>(settings.keySet());
        for (int i = 0; i < keyList.size(); i++) {
            Label label = new Label(keyList.get(i));
            TextField field = new TypedTextField(settings.get(keyList.get(i)));

            container.add(label, 0, i);
            container.add(field, 1, i);
        }
    }


    private HashMap<String, Object> getSettingsMap() {
        HashMap<String, Object> results = new HashMap<>();
        fields.forEach((setting, field) ->
                results.put(setting, field.getValue()));
        System.out.println(results.toString());
        return results;
    }

    private void restart() {
        System.out.println(getSettingsMap());
    }

}
