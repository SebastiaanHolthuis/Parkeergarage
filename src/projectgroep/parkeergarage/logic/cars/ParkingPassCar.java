package projectgroep.parkeergarage.logic.cars;

import java.awt.Color;
import java.util.Random;

public class ParkingPassCar extends Car {
    public static final Color COLOR = Color.decode("#33A4EA");

    public ParkingPassCar(int priceToPay) {
        super(priceToPay);

        Random random = new Random();
        int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }

    @Override
	public Color getColor() {
        return COLOR;
    }
}
