package projectgroep.parkeergarage.fx;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import projectgroep.parkeergarage.logic.ParkeerLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Settings extends FXView {
    GridPane container = new GridPane();
    Button restartButton = new Button("Restart") {{
        setOnMouseClicked(e -> restart());
    }};

    private HashMap<String, TypedTextField> fields = new HashMap<>();

    public Settings(ParkeerLogic model) {
        getChildren().add(container);

        HashMap<String, Object> settings = (model.settings).asMap();
        List<String> keyList = new ArrayList<>(settings.keySet());
        for (int i = 0; i < keyList.size(); i++) {
            Label label = new Label(keyList.get(i));
            TypedTextField field = new TypedTextField(settings.get(keyList.get(i)));

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
        System.out.println(getSettingsMap());
    }

    @Override
    public void updateView() {

    }
}
