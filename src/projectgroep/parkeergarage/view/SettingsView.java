package projectgroep.parkeergarage.view;

import java.awt.GridLayout;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import projectgroep.parkeergarage.SettingsRepository;
import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.logic.Settings;
import projectgroep.parkeergarage.main.Simulator;
import projectgroep.parkeergarage.runner.SimulatorRunner;


/**
 * Tijdelijk 'restart' button
 */


public class SettingsView extends AbstractView {
    private HashMap<String, TypedTextField> fields = new HashMap<>();
    private JButton restartButton = new JButton("Restart");
    Simulator sim;

    public SettingsView(ParkeerLogic model, Simulator sim) {
        super(model);
        this.sim = sim;
        setLayout(new GridLayout(0, 2));
        initializeFields();
        add(restartButton);
        restartButton.addActionListener(e -> handleRestart()); // TODO: naar contr
    }

    private void initializeFields() {
        (sim.getParkeerLogic().settings).asMap().forEach((setting, value) -> {
            TypedTextField field = new TypedTextField(value);
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
