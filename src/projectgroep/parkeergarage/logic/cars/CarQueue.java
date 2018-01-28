package projectgroep.parkeergarage.logic.cars;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class CarQueue implements Serializable {
    private Queue<Car> queue = new LinkedList<>();

    public boolean addCar(Car car) {
        return queue.add(car);
    }

    public Car removeCar() {
        return queue.poll();
    }

    public int carsInQueue() {
        return queue.size();
    }
}
