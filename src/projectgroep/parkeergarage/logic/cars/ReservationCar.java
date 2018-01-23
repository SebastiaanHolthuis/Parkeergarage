package projectgroep.parkeergarage.logic.cars;

import java.util.Random;
import java.awt.*;

public class ReservationCar extends Car {
	private static final Color COLOR=Color.decode("#FFFF00");
	
    public ReservationCar(int priceToPay) {
    	super(priceToPay);

    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
        this.setPriceToPay(6);
    }
    
    public Color getColor(){
    	return COLOR;
    }
}
