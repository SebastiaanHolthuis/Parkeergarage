package projectgroep.parkeergarage.view;

import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JTextField;

import projectgroep.parkeergarage.logic.ParkeerLogic;


/**
 * Tijdelijk 'restart' button
 */


public class SettingsView extends AbstractView {	
	private HashMap<String, JTextField> fields;
	static String[] settings = new String[] {
		"numberOfFloors",
		"numberOfRows",
		"numberOfPlaces",
		"weekDayArrivals",
		"weekendArrivals",
		"weekDayPassArrivals",
		"weekendPassArrivals",
		"enterSpeed",
		"paymentSpeed",
		"exitSpeed"
	};

	public SettingsView(ParkeerLogic model) {
		super(model);
		setLayout(new GridLayout(0,2));
		initializeFields();
	}
	
	// TODO: duplicatie opruimen?
	private void initializeFields() {
		for (String setting : settings) {
			JTextField field = new JTextField();
			JLabel label = new JLabel(setting);
			add(label);
			add(field);
		}
	}
	
	@Override
	public void updateView() {
		repaint();
	}
}
