package projectgroep.parkeergarage.logic;

import projectgroep.parkeergarage.logic.cars.Car;
import projectgroep.parkeergarage.logic.cars.CarQueue;

import java.util.List;

public class Snapshot extends MapLike {
    CarQueue entranceCarQueue;
    CarQueue entrancePassQueue;
    CarQueue paymentCarQueue;
    CarQueue exitCarQueue;

    Car[][][] cars;

    int numberOfOpenSpots;

    int day;
    int hour;
    int minute;
    int week;

    LocationLogic locationLogic;
    ReservationLogic reservationLogic;

    List<Car> skippedCars;

    double totalEarned;
}
