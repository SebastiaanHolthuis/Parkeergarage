package projectgroep.parkeergarage.main;

import javax.swing.*;
import java.awt.*;

import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.view.CarParkView;
import projectgroep.parkeergarage.view.SettingsView;

public class Simulator {
	
	private ParkeerLogic 	parkeerLogic;
	private CarParkView 	carParkView;
	private SettingsView 	settingsView;
	
	private JFrame			screen;
	private JFrame			settingsScreen;
	
	private Container		contentPane;
	private Container		settingsContentPane;

	public Simulator(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
		createInstances(numberOfFloors, numberOfRows, numberOfPlaces);		
		initializeFrame();		
		initializeSettingsFrame();
	}
	
	private void createInstances(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
		screen					= new JFrame();
		settingsScreen		    = new JFrame();
		
		parkeerLogic 			= new ParkeerLogic(numberOfFloors, numberOfRows, numberOfPlaces);
		
		carParkView 			= new CarParkView(parkeerLogic);
		settingsView 			= new SettingsView(parkeerLogic);
		
		parkeerLogic.addView(carParkView);
		parkeerLogic.addView(settingsView);
	}
	
	private void initializeFrame() {
		screen.setTitle("Parkeergarage simulator - ITV1C groep C");
		screen.setPreferredSize(new Dimension(900, 600));
		screen.setResizable(false);
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane 	= screen.getContentPane();
		contentPane.add(carParkView, BorderLayout.CENTER);
		
		screen.pack();
		screen.setLocationRelativeTo(null);
		screen.setVisible(true);
		
		carParkView.updateView();
	}
	
	private void initializeSettingsFrame() {
		settingsScreen.setTitle("Settings");
		settingsScreen.setPreferredSize(new Dimension(900, 600));
		settingsScreen.setResizable(false);
		settingsScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		settingsContentPane 	= settingsScreen.getContentPane();
		settingsContentPane.add(settingsView);
		
		settingsScreen.pack();
		settingsScreen.setLocationRelativeTo(null);
		settingsScreen.setVisible(true);
		
	}
	
	public ParkeerLogic getParkeerLogic() {
		return parkeerLogic;
	}
	
	public CarParkView getCarParkView() {
		return carParkView;
	}
	
	public Container getContentPane() {
		return screen.getContentPane();
	}
}
