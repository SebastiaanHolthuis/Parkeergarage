package projectgroep.parkeergarage.logic;

import projectgroep.parkeergarage.logic.cars.Car;

import java.util.ArrayList;
import java.util.HashMap;


public class ReservationSnapshot extends MapLike {
    public HashMap<Car, Location> reservations = new HashMap<>();
    public ArrayList<Car> cars = new ArrayList<>();
}
