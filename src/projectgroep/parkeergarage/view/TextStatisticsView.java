package projectgroep.parkeergarage.view;

import java.awt.Dimension;

import javax.swing.JLabel;

import projectgroep.parkeergarage.logic.ParkeerLogic;

public class TextStatisticsView extends AbstractView {

	private Dimension 		size;
	private JLabel			test;
	
	public TextStatisticsView(ParkeerLogic model) {
		super(model);
		
		size = new Dimension(0, 0);
		addComponents();
	}
	
	public void updateView() {
		repaint();
	}
	
	private void addComponents() {
		test = new JLabel("Test 12345");
		add(test);
	}

}
