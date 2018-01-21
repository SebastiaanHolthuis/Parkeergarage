package projectgroep.parkeergarage.logic.cars;

import java.util.Random;
import java.awt.*;

public class ReservedCar extends Car {
	private static final Color COLOR=Color.yellow;
	
    public ReservedCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }
    
    public Color getColor(){
    	return COLOR;
    }
}
