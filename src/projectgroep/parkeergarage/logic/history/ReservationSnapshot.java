package projectgroep.parkeergarage.logic.history;

import java.util.ArrayList;
import java.util.HashMap;

import projectgroep.parkeergarage.logic.cars.Car;
import projectgroep.parkeergarage.logic.locations.Location;


public class ReservationSnapshot extends MapLike {
    public HashMap<Car, Location> reservations = new HashMap<>();
    public ArrayList<Car> cars = new ArrayList<>();
}
