package projectgroep.parkeergarage.main;

import javax.swing.*;
import java.awt.*;

import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.view.CarParkView;

public class Simulator {
	
	private ParkeerLogic 	parkeerLogic;
	private CarParkView 	carParkView;
	
	private JFrame			screen;
	private Container		contentPane;

	public Simulator(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
		createInstances(numberOfFloors, numberOfRows, numberOfPlaces);		
		initializeFrame();		
	}
	
	private void createInstances(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
		screen					= new JFrame();
		parkeerLogic 			= new ParkeerLogic(numberOfFloors, numberOfRows, numberOfPlaces);
		carParkView 			= new CarParkView(parkeerLogic);
		parkeerLogic.addView(carParkView);
	}
	
	private void initializeFrame() {
		screen.setTitle("Parkeergarage simulator - ITV1C groep C");
		screen.setPreferredSize(new Dimension(800, 500));
		screen.setLocationRelativeTo(null);
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane 	= screen.getContentPane();
		contentPane.add(carParkView, BorderLayout.CENTER);
		
		screen.pack();
		screen.setVisible(true);
		
		carParkView.updateView();
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
