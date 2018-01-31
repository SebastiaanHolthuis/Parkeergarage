package projectgroep.parkeergarage.logic;

import java.util.List;

import projectgroep.parkeergarage.logic.cars.Car;
import projectgroep.parkeergarage.logic.cars.CarQueue;

public class Snapshot extends MapLike {
    CarQueue entranceCarQueue;
    CarQueue entrancePassQueue;
    CarQueue paymentCarQueue;
    CarQueue exitCarQueue;

    public Car[][][] cars;

    int numberOfOpenSpots;

    int day;
    int hour;
    int minute;
    int week;

    LocationLogic locationLogic;
    ReservationSnapshot reservationSnapshot;

    List<Car> skippedCars;

    public double totalEarned;
}
