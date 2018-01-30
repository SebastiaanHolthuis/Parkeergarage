package projectgroep.parkeergarage.logic.cars;

import java.awt.Color;
import java.util.Random;

public class AdHocCar extends Car {
    public static final Color COLOR = Color.decode("#D1345B");

    public AdHocCar(double priceToPay) {
        super(priceToPay);

        Random random = new Random();
        int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }

    public Color getColor() {
        return COLOR;
    }
}
