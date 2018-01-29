package projectgroep.parkeergarage.logic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import projectgroep.parkeergarage.logic.cars.AdHocCar;
import projectgroep.parkeergarage.logic.cars.Car;
import projectgroep.parkeergarage.logic.cars.CarQueue;
import projectgroep.parkeergarage.logic.cars.ParkingPassCar;
import projectgroep.parkeergarage.logic.cars.ReservationCar;

public class ParkeerLogic extends AbstractModel {
    public Settings settings;

    private int numberOfOpenSpots;
    private Car[][][] cars;

    private int day = 0;
    private int hour = 0;
    private int minute = 0;
    private int week = 0;

    private boolean running;

    private double totalEarned = 0;

    private static final String AD_HOC = "1";
    private static final String PASS = "2";
    private static final String RESERVED = "3";

    private List<Car> skippedCars = new ArrayList<>();

    private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;

    public CarQueue getEntrancePassQueue() {
        return entrancePassQueue;
    }

    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;

    private LocationLogic locationLogic;

    public int tickPause = 100;

    public Stack<Snapshot> history = new Stack<>();
    private ReservationLogic reservationLogic;

    public ParkeerLogic(Settings settings) {
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();

        this.settings = settings;
        this.numberOfOpenSpots = settings.numberOfFloors * settings.numberOfRows * settings.numberOfPlaces;
        this.cars = new Car[settings.numberOfFloors][settings.numberOfRows][settings.numberOfPlaces];

        this.locationLogic = new LocationLogic(this);
        this.reservationLogic = new ReservationLogic(this);
    }

    public void run() {
        running = true;

        while (true) {
            System.out.print(""); // HACK: niet verwijderen!
            if (running)
                tickSimulator();
        }
    }

    public void pause() {
        running = false;
    }

    public void play() {
        running = true;
    }


    public void saveSnapshot() {
        Snapshot sn = new Snapshot();

        sn.entranceCarQueue = (CarQueue) deepClone(entranceCarQueue);
        sn.entrancePassQueue = (CarQueue) deepClone(entrancePassQueue);
        sn.paymentCarQueue = (CarQueue) deepClone(paymentCarQueue);
        sn.exitCarQueue = (CarQueue) deepClone(exitCarQueue);
        sn.cars = (Car[][][]) deepClone(cars);
        sn.numberOfOpenSpots = numberOfOpenSpots;
        sn.day = day;
        sn.hour = hour;
        sn.minute = minute;
        sn.week = week;
        sn.locationLogic = locationLogic;
        sn.reservationLogic = reservationLogic;
        sn.skippedCars = skippedCars;
        sn.totalEarned = totalEarned;

        history.push(sn);
    }

    /**
     * This method makes a "deep clone" of any Java object it is given.
     */
    public static Object deepClone(Object object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void stepBack(int steps) {
        IntStream.range(0, steps).forEach(i -> {
            if (history.size() > 1) history.pop();
        });


        history.peek().asMap().forEach((k, v) -> {
            try {
                Field field = getClass().getDeclaredField(k);
                field.set(this, v);
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            }
        });

        updateViews();
    }


    public void tickMany(int ticks) {
        IntStream.range(0, ticks).forEach(tick -> tickSimulator());
    }

    public void tickSimulator() {
        advanceTime();
        handleExit();
        updateViews();

        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        handleEntrance();
        saveSnapshot();
    }

    private void advanceTime() {
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
            week++;
        }

    }

    public String translateDay(int day) {

        switch (day) {
            case 0:
                return "Monday";
            case 1:
                return "Tuesday";
            case 2:
                return "Wednesday";
            case 3:
                return "Thursday";
            case 4:
                return "Friday";
            case 5:
                return "Saturday";
            case 6:
                return "Sunday";
            default:
                return "";
        }

    }

    public String translateTime(int hour, int minute) {
        return hour + ":" + minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getDay() {
        String result = (translateDay(day % 7));
        return result;
    }

    public String getTime() {
        String result = (translateTime(hour, minute));
        return result;
    }


    private void handleEntrance() {
        carsArriving();
        carsEntering(entrancePassQueue);
        carsEntering(entranceCarQueue);
    }

    private void handleExit() {
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }

    private void updateViews() {
        tick();
        notifyViews();
    }

    private void carsArriving() {
        int numberOfCars = getNumberOfCars(settings.weekDayArrivals, settings.weekendArrivals);
        addArrivingCars(numberOfCars, AD_HOC);
        numberOfCars = getNumberOfCars(settings.weekDayPassArrivals, settings.weekendPassArrivals);
        addArrivingCars(numberOfCars, PASS);
        numberOfCars = getNumberOfCars(settings.weekDayResArrivals, settings.weekendResArrivals);
        addArrivingCars(numberOfCars, RESERVED);
    }

    private void carsEntering(CarQueue queue, Location... location) {
        int i = 0;
        // Remove car from the front of the queue and assign to a parking space.
        while (queue.carsInQueue() > 0 &&
                getNumberOfOpenSpots() > 0 &&
                i < settings.enterSpeed) {

            Car car = queue.removeCar();

            Location freeLocation = null;

            if (car instanceof ReservationCar && getReservationLogic().getReservations().containsKey(car)) {
                freeLocation = getReservationLogic().getReservations().get(car);
                setCarAt(freeLocation, car);
                getReservationLogic().removeReservation(car, freeLocation);
            } else {
                freeLocation = getFirstFreeLocation(car);
            }

            setCarAt(freeLocation, car);
            i++;
        }
    }

    public boolean setCarAt(Location location, Car car) {
        if (location == null || !locationIsValid(location)) {
            return false;
        }

        Car oldCar = getCarAt(location);

        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            location.setTaken(true);
            numberOfOpenSpots--;
            return true;
        }

        return false;
    }

    public CarQueue getEntranceCarQueue() {
        return entranceCarQueue;
    }

    public void setEntranceCarQueue(CarQueue entranceCarQueue) {
        this.entranceCarQueue = entranceCarQueue;
    }

    private void carsReadyToLeave() {
        // Add leaving cars to the payment queue.
        Car car = getFirstLeavingCar();
        while (car != null) {
            if (car.getHasToPay()) {
                car.setIsPaying(true);
                paymentCarQueue.addCar(car);
            } else {
                carLeavesSpot(car);
            }
            car = getFirstLeavingCar();
        }
    }

    private void carsPaying() {
        // Let cars pay.
        int i = 0;
        while (paymentCarQueue.carsInQueue() > 0 && i < settings.paymentSpeed) {
            Car car = paymentCarQueue.removeCar();
            // TODO Handle payment.
            totalEarned += car.getPriceToPay(); // houdt nog geen rekening met het aantal uur dat de auto er staat
            carLeavesSpot(car);
            i++;
        }
    }

    private void carsLeaving() {
        // Let cars leave.
        int i = 0;
        while (exitCarQueue.carsInQueue() > 0 && i < settings.exitSpeed) {
            exitCarQueue.removeCar();
            i++;
        }
    }

    public Stream<Car> getAllCars() {
        List<Car> results = new ArrayList<>();

        for (Car[][] floor : cars)
            for (Car[] row : floor)
                for (Car car : row)
                    if (car != null) results.add(car);
        return results.stream();
    }

    public Stream<Car> getParkingPassCars() {
        return getAllCars().filter((c) -> (c instanceof ParkingPassCar));
    }

    public Stream<Car> getReservationCars() {
        return getAllCars().filter((c) -> (c instanceof ReservationCar));
    }

    public Stream<Car> getAdHocCars() {
        return getAllCars().filter((c) -> (c instanceof AdHocCar));
    }


    private int getNumberOfCars(int weekDay, int weekend) {
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5
                ? weekDay
                : weekend;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int) Math.round(numberOfCarsPerHour / 60);
    }

    private boolean queueTooLongFor(String type) {
        if (type == PASS)
            return entrancePassQueue.carsInQueue() >= settings.maxQueue;
        else
            return entranceCarQueue.carsInQueue() >= settings.maxQueue;
    }

    private boolean fuckThatQueue() {
        boolean result = (new Random()).nextDouble() < settings.skipChance;
        return result;
    }

    public int getSkipCount() {
        return skippedCars.size();
    }


    private void carLeavesSpot(Car car) {
        removeCarAt(car.getLocation());

        exitCarQueue.addCar(car);
    }

    public void tick() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
    }

    public int getNumberOfFloors() {
        return settings.numberOfFloors;
    }

    public int getNumberOfRows() {
        return settings.numberOfRows;
    }

    public int getNumberOfPlaces() {
        return settings.numberOfPlaces;
    }

    public int getNumberOfOpenSpots() {
        return numberOfOpenSpots;
    }

    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    public Car removeCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }

        Car car = getCarAt(location);

        if (reservationLogic.getReservations().containsKey(car)) {
            reservationLogic.removeReservation(car, location);
        }

        location.setTaken(false);

        if (car == null) {
            return null;
        }

        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        numberOfOpenSpots++;
        return car;
    }

    public Location getFirstFreeLocation(Car car) {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = (!(car instanceof ParkingPassCar) && floor == 0) ? settings.numberOfPassHolderRows : 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null) {
                        if (!reservationLogic.getReservedLocations().contains(location)) {
                            return location;
                        }
                    }
                }
            }
        }

        return null;
    }

    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }

    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= settings.numberOfFloors || row < 0 || row > settings.numberOfRows || place < 0 || place > settings.numberOfPlaces) {
            return false;
        }
        return true;
    }

    public LocationLogic getLocationLogic() {
        return locationLogic;
    }

    public void setLocationLogic(LocationLogic locationLogic) {
        this.locationLogic = locationLogic;
    }

    public double getTotalEarned() {
        return totalEarned;
    }

    public void setTotalEarned(double totalEarned) {
        this.totalEarned = totalEarned;
    }

    private void addArrivingCars(int numberOfCars, String type) {
        for (Car car : reservationLogic.getReservationCars()) {
            if (car.getEntranceTime()[0] == getHour() && car.getEntranceTime()[1] == getMinute()) {
                entranceCarQueue.addCar(car);
            }
        }

        IntStream.range(0, numberOfCars).forEach(i -> {
            Car newCar;
            switch (type) {
                case PASS:
                    newCar = new ParkingPassCar(0);
                    break;
                case RESERVED:
                    newCar = new ReservationCar(6);
                    break;
                case AD_HOC:
                default:
                    newCar = new AdHocCar(settings.defaultPrice);
                    break;
            }

            if (!(newCar instanceof ReservationCar)) {
                if (queueTooLongFor(type) && fuckThatQueue()) {
                    skippedCars.add(newCar);
                } else {
                    if (newCar instanceof ParkingPassCar) {
                        entrancePassQueue.addCar(newCar);
                    } else {
                        entranceCarQueue.addCar(newCar);
                    }
                }
            } else {
                Location location = getFirstFreeLocation(newCar);
                reservationLogic.addReservation(newCar, location);
            }
        });
    }

    private void handleReservations() {
        Random random = new Random();
        int chance = random.nextInt(100);

        if (chance < 10) {
            ReservationCar car = new ReservationCar(6);
            Location location = getFirstFreeLocation(car);
            reservationLogic.addReservation(car, location);
        }
    }


    public ReservationLogic getReservationLogic() {
        return reservationLogic;
    }

    public void setReservationLogic(ReservationLogic reservationLogic) {
        this.reservationLogic = reservationLogic;
    }

}
