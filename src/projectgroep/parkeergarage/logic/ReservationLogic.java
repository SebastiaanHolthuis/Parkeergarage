package projectgroep.parkeergarage.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import projectgroep.parkeergarage.logic.cars.Car;

public class ReservationLogic {

	private ParkeerLogic model;
	private HashMap<Car, Location> reservations = new HashMap<Car, Location>();
	
	public ReservationLogic(ParkeerLogic model) {
		this.model = model;
	}
	
	public void addReservation(Car car, Location location) {
		if (!reservations.containsKey(car)) {
			reservations.put(car, location);
			location.reserve(car);
		}
	}
	
	public void removeReservation(Car car, Location location) {
		if (reservations.containsKey(car)) {
			reservations.remove(car);
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
	
}
