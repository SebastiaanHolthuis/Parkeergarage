package projectgroep.parkeergarage.view;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

/**
 * A type-aware and typesafe JTextField. In case the type cannot be created from a string, the initial value is returned.
 */
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
