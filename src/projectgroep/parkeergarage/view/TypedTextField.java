package projectgroep.parkeergarage.view;

import javax.swing.*;

public class TypedTextField extends JTextField {
    Object initialValue;

    TypedTextField(Object value) {
        super(value.toString());
        initialValue = value;
    }

    public Object getValue() {
        if (initialValue instanceof Integer) {
            return new Integer(getText());
        } else if (initialValue instanceof Double) {
            return new Double(getText());
        } else {
            return getText();
        }
    }
}
