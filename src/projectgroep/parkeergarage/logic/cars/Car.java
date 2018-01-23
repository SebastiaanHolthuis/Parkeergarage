package projectgroep.parkeergarage.logic.cars;

import java.awt.*;

import projectgroep.parkeergarage.logic.Location;

public abstract class Car {

    private Location location;
    private int minutesLeft;
    private boolean isPaying;
    private boolean hasToPay;
    private int priceToPay;
    
    /**
     * Constructor for objects of class Car
     */
    public Car(int priceToPay) {
    	this.priceToPay = priceToPay;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getMinutesLeft() {
        return minutesLeft;
    }

    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }
    
    public boolean getIsPaying() {
        return isPaying;
    }

    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    public boolean getHasToPay() {
        return hasToPay;
    }

    public void setHasToPay(boolean hasToPay) {
        this.hasToPay = hasToPay;
    }

    public void tick() {
        minutesLeft--;
    }
    
    public abstract Color getColor();

	public int getPriceToPay() {
		return priceToPay;
	}

	public void setPriceToPay(int priceToPay) {
		this.priceToPay = priceToPay;
	}
}