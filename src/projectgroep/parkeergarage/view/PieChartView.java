package projectgroep.parkeergarage.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import projectgroep.parkeergarage.logic.Location;
import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.logic.cars.AdHocCar;
import projectgroep.parkeergarage.logic.cars.Car;
import projectgroep.parkeergarage.logic.cars.ParkingPassCar;

public class PieChartView extends AbstractView {

    private Dimension size;
    private Image kekImage;

    public PieChartView(ParkeerLogic model) {
        super(model);

        size = new Dimension(0, 0);
    }

    @Override
    public void paintComponent(Graphics g) {
        if (kekImage == null) {
            return;
        }

        Dimension currentSize = getSize();

        if (size.equals(currentSize)) {
            g.drawImage(kekImage, 0, 0, null);
        } else {
            g.drawImage(kekImage, 0, 0, currentSize.width, currentSize.height, null);
        }
    }

    public void updateView() {
        if (!size.equals(getSize())) {
            size = getSize();
            kekImage = createImage(size.width, size.height);
        }

        Graphics graphics = kekImage.getGraphics();
        drawChart(graphics);

        repaint();
    }

    public void drawChart(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 1000, 1000);

        // Cirkel
        g.setColor(Color.LIGHT_GRAY);
        g.fillArc(12, 10, 180, 180, 0, 360);

        // Parking pass
        g.setColor(ParkingPassCar.COLOR);
        g.fillArc(12, 10, 180, 180, 0, (int) model.getParkingPassCars().count() - (int) model.getAdHocCars().count());

        // Ad Hoc
        g.setColor(AdHocCar.COLOR);
        g.fillArc(12, 10, 180, 180, 0, (int) model.getAdHocCars().count());
    }
}