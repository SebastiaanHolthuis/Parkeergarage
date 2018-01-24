package projectgroep.parkeergarage.controller;

import java.awt.event.*;
import javax.swing.*;

import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.view.SettingsView;


public class ButtonController extends AbstractController  {
	private JButton Apply;
	private JButton Stop;
	private JFrame screen;
	private ParkeerLogic parkeerlogic;
	
	public void AddButtons(){
	
		   JButton OneStep = new JButton("+1");
	        OneStep.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        	}
	        });
	 Apply = new JButton("Start");
     Apply.setBounds(10, 574, 73, 23);
     screen.getContentPane().add(Apply);

     Stop = new JButton("Stop");
     Stop.setBounds(141, 572,73, 26);
     
     screen.getContentPane().add(Stop);
     OneStep.setBounds(144, 624, 73, 23);
     screen.getContentPane().add(OneStep);
     
     JButton StepBack = new JButton("-1");
     StepBack.setBounds(10, 624, 73, 23);
     screen.getContentPane().add(StepBack);
     
     JButton Reset = new JButton("Reset");
     Reset.setBounds(66, 681, 89, 23);
     screen.getContentPane().add(Reset);

	}

	public void actionPerformed(ActionEvent e) {
	if (e.getSource() == Stop) {
		parkeerlogic.stop();
	}
      //Apply.addActionListener(e -> parkeerlogic.run());
	}
}
