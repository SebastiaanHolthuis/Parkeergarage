package projectgroep.parkeergarage.logic.cars;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import projectgroep.parkeergarage.logic.Location;

public abstract class Car implements Serializable {

    private Location location;
    private int minutesLeft;
    private boolean isPaying;
    private boolean hasToPay;
    private double priceToPay;
    private int[] entranceTime;
    public ArrayList<Double> timeEntering;
    public ArrayList<Double> timeLeaving;    
//    private ArrayList<Double> timeEntering = new ArrayList<>();

    /**
     * Constructor for objects of class Car
     *
     * @param priceToPay
     */
    public Car(double priceToPay) {
        this.priceToPay = priceToPay;
        ArrayList<Double> timeEntering = new ArrayList<>();
        ArrayList<Double> timeLeaving = new ArrayList<>();
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
}