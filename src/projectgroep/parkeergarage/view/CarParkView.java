package projectgroep.parkeergarage.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import projectgroep.parkeergarage.logic.Location;
import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.logic.cars.Car;
import projectgroep.parkeergarage.logic.cars.ReservationCar;

public class CarParkView extends AbstractView {

    private Dimension size;
    private Image carParkImage;

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

        model.locations().forEach(location -> {
            Car car = model.getCarAt(location);
            Color color = car == null ? Color.decode("#8bba8b") : car.getColor();

            if (model.getReservationLogic().getReservations().values().contains(location)) {
                if (car != null) {
                    color = car.getColor();
                }
            } else {
                if (car == null && location.getFloor() == 0 && location.getRow() < 2) {
                    color = Color.decode("#ADDAF7"); // Blue
                } else if (car == null) {
                    color = Color.decode("#F0839E"); // Magenta
                } else {
                    color = car.getColor();
                }
            }

            drawPlace(graphics, location, color);
        });

        repaint();

    }

    private void drawPlace(Graphics graphics, Location location, Color color) {
        graphics.setColor(color);
        graphics.fillRect(
                location.getFloor() * 260 + (1 + (int) Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                60 + location.getPlace() * 10,
                20 - 1,
                10 - 1); // TODO use dynamic size or constants

    }
}