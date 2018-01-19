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


/**
 * Tijdelijk 'restart' button
 */


public class SettingsView extends AbstractView {	
	private HashMap<String, JTextField> fields = new HashMap<String, JTextField>();
	private JButton restartButton = new JButton("Restart");

	public SettingsView(ParkeerLogic model) {
		super(model);
		setLayout(new GridLayout(0,2));
		initializeFields();
		add(restartButton);
		restartButton.addActionListener((e) -> getSettingsMap());
	}
	
	private void initializeFields() {
		(new Settings()).fieldNames().forEach(setting -> {
			JTextField field = new JTextField();
			JLabel label = new JLabel(setting);
			add(label);
			add(field);
			fields.put(setting, field);		
		});
	}

	private HashMap<String, Integer> getSettingsMap() {
		HashMap<String, Integer> results = new HashMap<String, Integer>();
		fields.forEach((setting, field) -> 
			results.put(setting, Integer.parseInt(field.getText())));
		System.out.println(results.toString());
		return results;
	}
	
	@Override
	public void updateView() {
		repaint();
	}
}
