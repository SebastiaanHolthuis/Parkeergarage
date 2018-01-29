package projectgroep.parkeergarage.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.logic.cars.AdHocCar;
import projectgroep.parkeergarage.logic.cars.Car;
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
        drawLegend(graphics);

        repaint();

	}
	
	public void drawLegend(Graphics g) {	
		g.setFont(new Font("Arial", Font.PLAIN, 14));
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1000, 1000);
		
		drawLegendItem(g, "Ad Hoc", AdHocCar.COLOR, 0);
		drawLegendItem(g, "Abonnementhouder", ParkingPassCar.COLOR, 1);
		drawLegendItem(g, "Reservatie", ReservationCar.COLOR, 2);
		drawLegendItem(g, "Gereserveerde plek", Color.decode("#8bba8b"), 3);
	}
	
	private void drawLegendItem(Graphics g, String text, Color color, int position) {
		g.setColor(color);
		g.fillRect(10, 10 + (position * 30), 50, 20);
		
		g.setColor(Color.BLACK);
		g.drawString(text, 70, 25 + (position * 30));
	}

}