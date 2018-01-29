package projectgroep.parkeergarage.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import projectgroep.parkeergarage.Utils;
import projectgroep.parkeergarage.logic.cars.Car;

import static projectgroep.parkeergarage.Utils.deepClone;

public class ReservationLogic {

    private ParkeerLogic model;
    private HashMap<Car, Location> reservations = new HashMap<Car, Location>();
    public static ArrayList<Car> cars = new ArrayList<Car>();

    public ReservationLogic(ParkeerLogic model) {
        this.model = model;
    }

    public ReservationLogic(ParkeerLogic model, ReservationSnapshot snapshot) {
        this.model = model;
        this.reservations = snapshot.reservations;
        cars = snapshot.cars;
    }

    public void addReservation(Car car, Location location) {
        if (!reservations.containsKey(car)) {
            int[] time = new int[3];
            time[0] = model.getHour();
            time[1] = model.getMinute() + 15;

            if (time[1] > 59) {
                time[0] = model.getHour() + 1;
                time[1] = 14;
            }

            if (location != null) {
                reservations.put(car, location);
                location.reserve(car);
                car.setEntranceTime(time);
            }
        }
    }

    public void removeReservation(Car car, Location location) {
        if (reservations.containsKey(car)) {
            reservations.remove(car, location);
            location.unreserve();
        }
    }

    public ArrayList<Location> getReservedLocations() {
        ArrayList<Location> toReturn = new ArrayList<Location>();

        for (Location location : reservations.values()) {
            toReturn.add(location);
        }

        return toReturn;
    }

    public ArrayList<Car> getReservationCars() {
        ArrayList<Car> toReturn = new ArrayList<Car>();

        for (Car car : reservations.keySet()) {
            toReturn.add(car);
        }

        return toReturn;
    }

    public HashMap<Car, Location> getReservations() {
        return reservations;
    }

    public void setReservations(HashMap<Car, Location> reservations) {
        this.reservations = reservations;
    }

    public ReservationSnapshot makeSnapshot() {
        return new ReservationSnapshot() {{
            reservations = (HashMap<Car, Location>) deepClone(getReservations());
            cars = (ArrayList<Car>) deepClone(getReservationCars());
        }};
    }
}
