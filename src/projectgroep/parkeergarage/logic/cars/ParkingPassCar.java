package projectgroep.parkeergarage.logic.cars;

import java.util.Random;
import java.awt.*;

public class ParkingPassCar extends Car {
    public static final Color COLOR = Color.decode("#33A4EA");

    public ParkingPassCar() {
        Random random = new Random();
        int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }

    public Color getColor() {
        return COLOR;
    }
}
