package projectgroep.parkeergarage.view;

import javax.swing.JTextField;

import projectgroep.parkeergarage.logic.ParkeerLogic;

public class SettingsView extends AbstractView {
	
	public JTextField t1;


	public SettingsView(ParkeerLogic model) {
		super(model);
		t1 = new JTextField(30);
		add(t1);
	}
	
	@Override
	public void updateView() {
		
		repaint();
	}
}
