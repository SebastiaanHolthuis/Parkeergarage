package projectgroep.parkeergarage.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.logic.cars.AdHocCar;
import projectgroep.parkeergarage.logic.cars.ParkingPassCar;
import projectgroep.parkeergarage.logic.cars.ReservationCar;

public class LegendView extends AbstractView {

    private Dimension size;
    private Image legendImage;

    public LegendView(ParkeerLogic model) {
        super(model);

        size = new Dimension(200, 100);
        setSize(200, 100);
    }

    @Override
    public void paintComponent(Graphics g) {
        if (legendImage == null) {
            return;
        }

        Dimension currentSize = getSize();

        if (size.equals(currentSize)) {
            g.drawImage(legendImage, 0, 0, null);
        } else {
            g.drawImage(legendImage, 0, 0, currentSize.width, currentSize.height, null);
        }
    }

    public void updateView() {
        if (!size.equals(getSize())) {
            size = getSize();
            legendImage = createImage(size.width, size.height);
        }

        Graphics graphics = legendImage.getGraphics();
        drawChart(graphics);

        repaint();

	}
	
	public void drawChart(Graphics g) {	
		g.setFont(new Font("Arial", Font.PLAIN, 14));
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1000, 1000);
		
		// Ad Hoc
		g.setColor(AdHocCar.COLOR);
		g.fillRect(10, 10, 50, 20);
		
		g.setColor(Color.BLACK);
		g.drawString("Ad Hoc", 70, 25);
		
		// Parking Pass
		g.setColor(ParkingPassCar.COLOR);
		g.fillRect(10, 40, 50, 20);
		
		g.setColor(Color.BLACK);
		g.drawString("Parking Pass", 70, 55);
		
		// Reservation 
		g.setColor(ReservationCar.COLOR);
		g.fillRect(10, 70, 50, 20);
		
		g.setColor(Color.BLACK);
		g.drawString("Reservation Car", 70, 85);
	}

}