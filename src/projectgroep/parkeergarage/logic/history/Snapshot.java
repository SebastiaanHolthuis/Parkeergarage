package projectgroep.parkeergarage.logic.history;

import java.util.List;

import projectgroep.parkeergarage.logic.cars.Car;
import projectgroep.parkeergarage.logic.cars.CarQueue;
import projectgroep.parkeergarage.logic.locations.LocationLogic;

public class Snapshot extends MapLike {
	public CarQueue entranceCarQueue;
    public CarQueue entrancePassQueue;
    public CarQueue paymentCarQueue;
    public CarQueue exitCarQueue;

    public Car[][][] cars;

    public int numberOfOpenSpots;

    public int day;
    public int hour;
    public int minute;
    public int week;

    public LocationLogic locationLogic;
    public ReservationSnapshot reservationSnapshot;

    public List<Car> skippedCars;

    public double totalEarned;
}
