package projectgroep.parkeergarage.logic.cars;

import javafx.scene.paint.Color;

import java.util.Random;

public class AdHocCar extends Car {
    public static final Color COLOR = Color.web("#D1345B");

    public AdHocCar(double priceToPay) {
        super(priceToPay);

        Random random = new Random();
        int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }

    @Override
    public Color getColor() {
    	if (isCrookedParking()) return Color.BLACK;
    	
        return COLOR;
    }
}
