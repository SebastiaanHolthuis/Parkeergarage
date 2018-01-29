package projectgroep.parkeergarage.logic;

import projectgroep.parkeergarage.logic.cars.Car;
import projectgroep.parkeergarage.logic.cars.CarQueue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    ReservationSnapshot reservationSnapshot;

    List<Car> skippedCars;

    double totalEarned;


}
