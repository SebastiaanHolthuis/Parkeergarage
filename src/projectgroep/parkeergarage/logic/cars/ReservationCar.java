package projectgroep.parkeergarage.logic.cars;

import java.awt.Color;
import java.util.Random;

public class ReservationCar extends Car {
    public static final Color COLOR = Color.decode("#008000");

    public ReservationCar(double priceToPay) {
        super(priceToPay);

        Random random = new Random();
        int stayMinutes = (int) (15 + random.nextFloat() * 3 * 10);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
        this.setPriceToPay(6);
    }

    @Override
	public Color getColor() {
        return COLOR;
    }
}
