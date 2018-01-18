package projectgroep.parkeergarage.view;

import javax.swing.JPanel;

import projectgroep.parkeergarage.logic.AbstractModel;

@SuppressWarnings("serial")
public class AbstractView extends JPanel {

	protected AbstractModel model;
	
	public AbstractView(AbstractModel model) {
		this.model = model;
	}
	
}
