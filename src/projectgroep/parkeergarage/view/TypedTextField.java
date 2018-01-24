package projectgroep.parkeergarage.view;

import javax.swing.*;

public class TypedTextField extends JTextField {
    public Class type;

    TypedTextField(Object value) {
        super(value.toString());
        type = value.getClass();
    }

    public Integer getValue() {
        return Integer.parseInt(getText());
    }
}
