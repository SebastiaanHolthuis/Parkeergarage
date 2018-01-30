package projectgroep.parkeergarage.logic;

import java.io.Serializable;

import projectgroep.parkeergarage.logic.cars.Car;

public class Location implements Serializable {

    private int floor;
    private int row;
    private int place;

    private Boolean forParkingPass;
    private Boolean forReservation;
    private Boolean taken;

    private Car reservationCar;

    /**
     * Constructor for objects of class Location
     */
    public Location(int floor, int row, int place) {
        this.floor = floor;
        this.row = row;
        this.place = place;
        this.taken = false;
        this.forReservation = false;
        this.forParkingPass = false;
    }

    /**
     * Implement content equality.
     */
    @Override
	public boolean equals(Object obj) {
        if (obj instanceof Location) {
            Location other = (Location) obj;
            return floor == other.getFloor() && row == other.getRow() && place == other.getPlace();
        } else {
            return false;
        }
    }

    /**
     * Return a string of the form floor,row,place.
     *
     * @return A string representation of the location.
     */
    @Override
	public String toString() {
        return floor + "," + row + "," + place;
    }

    /**
     * Use the 10 bits for each of the floor, row and place
     * values. Except for very big car parks, this should give
     * a unique hash code for each (floor, row, place) tupel.
     *
     * @return A hashcode for the location.
     */
    @Override
	public int hashCode() {
        return (floor << 20) + (row << 10) + place;
    }

    /**
     * @return The floor.
     */
    public int getFloor() {
        return floor;
    }

    /**
     * @return The row.
     */
    public int getRow() {
        return row;
    }

    /**
     * @return The place.
     */
    public int getPlace() {
        return place;
    }

    /**
     * @return the forParkingPass
     */
    public Boolean isForParkingPass() {
        return forParkingPass;
    }

    /**
     * @param forParkingPass the forParkingPass to set
     */
    public void setForParkingPass(Boolean forParkingPass) {
        this.forParkingPass = forParkingPass;
    }

    public Boolean isForReservation() {
        return forReservation;
    }

    public void setForReservation(Boolean forReservation) {
        this.forReservation = forReservation;
    }

    public Boolean isTaken() {
        return taken;
    }

    public void setTaken(Boolean taken) {
        this.taken = taken;
    }

    public void reserve(Car car) {
        if (!isForReservation()) {
            setForReservation(true);
            setReservationCar(car);
        }
    }

    public void unreserve() {
        if (isForReservation()) {
            setForReservation(false);
            setReservationCar(null);
        }
    }

    public Car getReservationCar() {
        return reservationCar;
    }

    public void setReservationCar(Car reservationCar) {
        this.reservationCar = reservationCar;
    }

}