package projectgroep.parkeergarage.controller;

import java.awt.event.*;
import javax.swing.*;


public class ButtonController extends AbstractController implements ActionListener {

	private JButton stepOne;
	private JButton startSteps;
	private JButton stopSteps;
	private JTextField steps;
	
	public ButtonController() {
		setSize(450, 50);
		stepOne=new JButton("One step");
		stepOne.addActionListener(this);
		steps=new JTextField();
		startSteps=new JButton("Start");
		startSteps.addActionListener(this);
		stopSteps=new JButton("Stop");
		stopSteps.addActionListener(this);
		
		this.setLayout(null);
		add(stepOne);
		add(steps);
		add(startSteps);
		add(stopSteps);
		stepOne.setBounds(50, 10, 70, 30);
		steps.setBounds(140, 10, 70, 30);
		startSteps.setBounds(229, 10, 70, 30);
		stopSteps.setBounds(319, 10, 70, 30);

		setVisible(true);
}
}
