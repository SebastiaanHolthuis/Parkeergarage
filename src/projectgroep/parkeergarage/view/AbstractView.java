package projectgroep.parkeergarage.view;

import javax.swing.JPanel;

import projectgroep.parkeergarage.logic.AbstractModel;
import projectgroep.parkeergarage.logic.ParkeerLogic;

@SuppressWarnings("serial")
public class AbstractView extends JPanel {

	protected ParkeerLogic model;
	
	public AbstractView(ParkeerLogic model) {
		this.model = model;
	}
	
	public AbstractModel getModel() {
		return model;
	}
	
	public void updateView() {
		repaint();
	}
	
}
