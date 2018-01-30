package projectgroep.parkeergarage.fx;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingsController {

    @FXML
    GridPane container;

    public void initialize() {
        System.out.println();

        HashMap<String, Object> map = (Simulator.model.settings).asMap();

        List<String> keyList = new ArrayList<String>(map.keySet());
        for (int i = 0; i < keyList.size(); i++) {
            Label label = new Label(keyList.get(i));
            TextField field = new TypedTextField(map.get(keyList.get(i)));

            container.add(label, 0, i);
            container.add(field, 1, i);
        }
    }

    class TypedTextField extends TextField {
        Object initialValue;


        public TypedTextField(Object value) {
            super(value.toString());
            initialValue = value;
        }

        public Object getValue() {
            try {
                return initialValue.getClass().getDeclaredConstructor(String.class).newInstance(getText());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                return initialValue;
            }
        }
    }
}
