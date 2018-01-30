package projectgroep.parkeergarage.fx;

import javafx.scene.control.TextField;

import java.lang.reflect.InvocationTargetException;

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
