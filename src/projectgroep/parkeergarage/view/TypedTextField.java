package projectgroep.parkeergarage.view;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class TypedTextField extends JTextField {
    Object initialValue;

    TypedTextField(Object value) {
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
