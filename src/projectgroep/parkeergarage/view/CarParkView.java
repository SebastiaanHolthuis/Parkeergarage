package projectgroep.parkeergarage.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import projectgroep.parkeergarage.logic.Location;
import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.logic.cars.Car;

public class CarParkView extends AbstractView {

	private Dimension 		size;
	private Image			carParkImage;
	
	public CarParkView(ParkeerLogic model) {
		super(model);
		
		size = new Dimension(0, 0);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if (carParkImage == null) {
            return;
        }

        Dimension currentSize = getSize();
        
        if (size.equals(currentSize)) {
            g.drawImage(carParkImage, 0, 0, null);
        } else {
            g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
        }
	}

	public void updateView() {
		if (!size.equals(getSize())) {
            size = getSize();
            carParkImage = createImage(size.width, size.height);
        }
		
        Graphics graphics = carParkImage.getGraphics();
        
        for (int floor = 0; floor < model.getNumberOfFloors(); floor++) {
            for (int row = 0; row < model.getNumberOfRows(); row++) {
                for (int place = 0; place < model.getNumberOfPlaces(); place++) {
                	Location location = new Location(floor, row, place);
                		if(floor == 0 && row < 2) {
                			model.getParkingPassLocations().add(location);
                		}
                    Car car = model.getCarAt(location);
                    Color color = car == null ? Color.white : car.getColor();
                    drawPlace(graphics, location, color);
                }
            }
        }
        
        
        
        repaint();
	}
	
	 private void drawPlace(Graphics graphics, Location location, Color color) {
         graphics.setColor(color);
         graphics.fillRect(
                 location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                 60 + location.getPlace() * 10,
                 20 - 1,
                 10 - 1); // TODO use dynamic size or constants

     }
}
