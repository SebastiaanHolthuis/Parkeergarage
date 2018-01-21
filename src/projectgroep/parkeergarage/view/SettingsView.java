package projectgroep.parkeergarage.view;

import java.awt.GridLayout;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.logic.Settings;
import projectgroep.parkeergarage.main.Simulator;
import projectgroep.parkeergarage.runner.SimulatorRunner;


/**
 * Tijdelijk 'restart' button
 */


public class SettingsView extends AbstractView {	
	private HashMap<String, JTextField> fields = new HashMap();
	private JButton restartButton = new JButton("Restart");
	Simulator sim;
	
	public SettingsView(ParkeerLogic model, Simulator sim) {
		super(model);
		this.sim = sim;
		setLayout(new GridLayout(0,2));
		initializeFields();
		add(restartButton);
		restartButton.addActionListener(e -> handleRestart()); // TODO: naar contr
	}
	
	private void initializeFields() {
		(new Settings()).asMap().forEach((setting, value) -> {
			JTextField field = new JTextField(value.toString());
			JLabel label = new JLabel(setting);
			add(label);
			add(field);
			fields.put(setting, field);		
		});
	}

	private HashMap<String, Integer> getSettingsMap() {
		HashMap<String, Integer> results = new HashMap();
		fields.forEach((setting, field) -> 
			results.put(setting, Integer.parseInt(field.getText())));
		System.out.println(results.toString());
		return results;
	}
	
	private void handleRestart() {
		SimulatorRunner.restart();
	}
	
	@Override
	public void updateView() {
		repaint();
	}
}
