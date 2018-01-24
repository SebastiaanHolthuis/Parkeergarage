package projectgroep.parkeergarage.controller;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.main.Simulator;

@SuppressWarnings("serial")
public class ButtonController extends AbstractController {
	 JButton Apply = new JButton("Start");
	 JButton OneStep = new JButton("+1");
     JButton Stop = new JButton("Stop");     
     JButton StepBack = new JButton("-1");
     
     JButton Reset = new JButton("Reset");
     ParkeerLogic parkeerLogic;
     Simulator sim;
     
	public ButtonController(Simulator simulator, ParkeerLogic parkeerLogic) {
		this.parkeerLogic = parkeerLogic;
		this.sim = simulator;
		 Apply.setBounds(10, 572, 73, 26);
		 simulator.getScreen().getContentPane().add(Apply);
		 Apply.addActionListener(this);
		 
	     Stop.setBounds(141, 572,73, 26);
	     simulator.getScreen().getContentPane().add(Stop);
	     Stop.addActionListener(this);

	     OneStep.setBounds(144, 624, 73, 23);
	     simulator.getScreen().getContentPane().add(OneStep);
	    
	     StepBack.setBounds(10, 624, 73, 23);
	     simulator.getScreen().getContentPane().add(StepBack);
	     
	     Reset.setBounds(66, 681, 89, 23);
	     simulator.getScreen().getContentPane().add(Reset);
	     Reset.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == Stop) {
			parkeerLogic.stop();
		}
		if (e.getSource() == Reset) {
			sim.restart(parkeerLogic.settings);
		}
		if (e.getSource() == Apply) {
			parkeerLogic.start();
		}

	}
}
