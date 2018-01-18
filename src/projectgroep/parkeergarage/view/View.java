package projectgroep.parkeergarage.view;

import javax.swing.JPanel;

import projectgroep.parkeergarage.logic.Model;

@SuppressWarnings("serial")
public class View extends JPanel {

	protected Model model;
	
	public View(Model model) {
		this.model = model;
	}
	
}
