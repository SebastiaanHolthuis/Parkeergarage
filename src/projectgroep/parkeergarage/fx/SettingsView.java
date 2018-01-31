package projectgroep.parkeergarage.fx;

import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;

import projectgroep.parkeergarage.fx.Simulator;
import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.logic.Settings;
import projectgroep.parkeergarage.view.SwingView;
import projectgroep.parkeergarage.view.TypedTextField;

/**
 * Tijdelijk 'restart' button
 */


public class SettingsView extends SwingView {
    private HashMap<String, projectgroep.parkeergarage.view.TypedTextField> fields = new HashMap<>();
    private JButton restartButton = new JButton("Restart");
    Simulator sim;

    public SettingsView(ParkeerLogic model, Simulator sim) {
        super(model);
        this.sim = sim;
        setLayout(new GridLayout(0, 2));
        initializeFields();
        add(restartButton);
        restartButton.addActionListener(e -> handleRestart());
    }

    private void initializeFields() {
        (model.settings).asMap().forEach((setting, value) -> {
            projectgroep.parkeergarage.view.TypedTextField field = new TypedTextField(value);
            JLabel label = new JLabel(setting);
            add(label);
            add(field);
            fields.put(setting, field);
        });
    }

    private HashMap<String, Object> getSettingsMap() {
        HashMap<String, Object> results = new HashMap<>();
        fields.forEach((setting, field) ->
                results.put(setting, field.getValue()));
        System.out.println(results.toString());
        return results;
    }

    // TODO: move to controller
    private void handleRestart() {
        sim.restart(new Settings(getSettingsMap()));
    }

    @Override
    public void updateView() {
        repaint();
    }
}