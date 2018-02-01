package projectgroep.parkeergarage.logic.cars;

import java.io.Serializable;
import java.util.Random;

import javafx.scene.paint.Color;
import projectgroep.parkeergarage.logic.Location;

public abstract class Car implements Serializable {

    private Location location;
    private int minutesLeft;
    private boolean isPaying;
    private boolean hasToPay;
    private double priceToPay;
    private int[] entranceTime;
    public int[] timeEntering = {0,0,0};
    public int[] timeLeaving = {0,0,0};    
    private boolean crookedParking = false;
    
//    private ArrayList<Double> timeEntering = new ArrayList<>();

    /**
     * Constructor for objects of class Car
     *
     * @param priceToPay
     */
    public Car(double priceToPay) {
        this.priceToPay = priceToPay;
        
        Random rand = new Random();
        if (rand.nextInt(1000) <= 5) crookedParking = true;
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

    public double getPriceToPay() {
        return priceToPay;
    }

    public void setPriceToPay(double priceToPay) {
        this.priceToPay = priceToPay;
    }

    public int[] getEntranceTime() {
        return entranceTime;
    }

    public void setEntranceTime(int[] entranceTime) {
        this.entranceTime = entranceTime;
    }

	public boolean isCrookedParking() {
		return crookedParking;
	}

	public void setCrookedParking(boolean crookedParking) {
		this.crookedParking = crookedParking;
	}

//    private static stayMinutes (int hours, int minutes) {
//        (hours * 60) +
//    }
}