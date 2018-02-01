package projectgroep.parkeergarage.logic;

import static projectgroep.parkeergarage.Utils.deepClone;
import static projectgroep.parkeergarage.logic.cars.CarType.AD_HOC;
import static projectgroep.parkeergarage.logic.cars.CarType.PARKINGPASS;
import static projectgroep.parkeergarage.logic.cars.CarType.RESERVED;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import projectgroep.parkeergarage.logic.cars.AdHocCar;
import projectgroep.parkeergarage.logic.cars.Car;
import projectgroep.parkeergarage.logic.cars.CarQueue;
import projectgroep.parkeergarage.logic.cars.CarType;
import projectgroep.parkeergarage.logic.cars.ParkingPassCar;
import projectgroep.parkeergarage.logic.cars.ReservationCar;
import projectgroep.parkeergarage.logic.events.Event;
import projectgroep.parkeergarage.logic.events.Events;

public class ParkeerLogic extends AbstractModel {
    public Settings settings;

    private int numberOfOpenSpots;
    private Car[][][] cars;

    private int day = 0;
    private int hour = 0;
    private int minute = 0;
    private int week = 0;

    // Run switches
    private volatile boolean running;
    private boolean alive = true;

    /**
     * Earnings
     */
    private double totalEarned = 0;
    private int parkingPassEarnings;

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

    public History history;
    private ReservationLogic reservationLogic;
    private Events events;

    private int expectedEventVisitors;

    public int tickNum() {
        return (week * 7 * 24 * 60) + (day * 24 * 60) + (hour * 60) + (minute);
    }

    public ParkeerLogic(Settings settings) {
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();

        this.settings = settings;
        this.numberOfOpenSpots = settings.numberOfFloors * settings.numberOfRows * settings.numberOfPlaces;
        this.cars = new Car[settings.numberOfFloors][settings.numberOfRows][settings.numberOfPlaces];

        this.history = new History(settings.maxHistory);
        this.locationLogic = new LocationLogic(this);
        this.reservationLogic = new ReservationLogic(this);
        initializeEvents();
    }

    @Override
    public void run() {
        running = true;

        while (alive) {
            if (running)
                tickSimulator();
        }
    }

    public void toggleRunning() {
        running = !running;
    }

    public boolean isRunning() {
        return running;
    }

    public void kill() {
        alive = false;
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
        sn.reservationSnapshot = reservationLogic.makeSnapshot();
        sn.skippedCars = skippedCars;
        sn.totalEarned = totalEarned;

        history.saveSnapshot(sn);
    }

    public void stepBack(int steps) {
        Snapshot lastStep = history.getStepsBack(steps);

        lastStep.asMap().forEach((k, v) -> {
            try {
                Field field = getClass().getDeclaredField(k);
                field.set(this, v);
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            }
        });

        reservationLogic = new ReservationLogic(this, lastStep.reservationSnapshot);

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
                return "Maandag";
            case 1:
                return "Dinsdag";
            case 2:
                return "Woensdag";
            case 3:
                return "Donderdag";
            case 4:
                return "Vrijdag";
            case 5:
                return "Zaterdag";
            case 6:
                return "Zondag";
            default:
                return "";
        }

    }

    public String translateTime(int hour, int minute) {
        return hour + ":" + minute;
    }

    public int getTimeToPay() {
        return day + hour + minute;
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
        return (translateDay(day % 7));
    }

    public String getTime() {
        return (translateTime(hour, minute));
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

    private void initializeEvents() {
        int[] duration = new int[2];
        duration[0] = 1;
        duration[1] = 30;

        events = new Events(this);
        events.addEvent("Koopavond", 0, 18, 30, duration, 50);
        events.addEvent("Kermis", 3, 19, 30, duration, 300);
    }

    private void handleStartingEvents() {
        int[] startTime = new int[3];
        startTime[0] = day;
        startTime[1] = hour;
        startTime[2] = minute;

        ArrayList<Event> startingEvents = events.getEventsByStartTime(startTime);

        for (Event event : startingEvents) {
            this.expectedEventVisitors += event.getExpectedVisitors();
        }
    }

    private void updateViews() {
        tick();
        notifyViews();
    }

    private void carsArriving() {
        int numberOfCars = getNumberOfCars(settings.weekDayArrivals, settings.weekendArrivals);
        addArrivingCars(numberOfCars, AD_HOC);
        numberOfCars = getNumberOfCars(settings.weekDayPassArrivals, settings.weekendPassArrivals);
        addArrivingCars(numberOfCars, PARKINGPASS);
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
            car.timeEntering[0] = day;
            car.timeEntering[1] = hour;
            car.timeEntering[2] = minute;

            Location freeLocation = null;

            if (car instanceof ParkingPassCar) {
                parkingPassEarnings += 100;
            }

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

            car.timeLeaving[0] = day;
            car.timeLeaving[1] = hour;
            car.timeLeaving[2] = minute;

            // TODO Handle payment.

            int days = 0;
            int hours = 0;
            int minutes = 0;

            days = car.timeLeaving[0] - car.timeEntering[0];
            hours = car.timeLeaving[1] - car.timeEntering[1];
            minutes = car.timeLeaving[2] - car.timeEntering[2];

            if ((car.timeLeaving[0] - car.timeEntering[0]) > 0) {
                minutes += (days * 24 * 60);
            }
            if ((car.timeLeaving[1] - car.timeEntering[1]) > 0) {
                minutes += (hours * 60);
            }

            if (car instanceof ReservationCar) {
                totalEarned += (minutes * 0.02);
            } else if (car instanceof AdHocCar) {
                totalEarned += (minutes * 0.02);
            } else {
                totalEarned += 100;
            }

//            totalEarned += (minutes * 0.04); // houdt nog geen rekening met het aantal uur dat de auto er staat
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
        double numberOfCarsPerHour = (averageNumberOfCarsPerHour + expectedEventVisitors) + random.nextGaussian() * standardDeviation;

        return (int) (Math.round(getCarMultiplier() * numberOfCarsPerHour / 60));
    }

    private double getCarMultiplier() {
        double period = (2 * Math.PI) / 24;
        double multiplier = (double) hour + (double) minute / 60;
        return 0.4 + 0.6 * (1 + Math.sin(period * (multiplier - (14 - 6.5))));
    }

    private boolean queueTooLongFor(CarType type) {
        if (type == PARKINGPASS)
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

    public Collection<Location> locations() {
        Collection<Location> locations = new ArrayList<>();

        for (int floor = 0; floor < getNumberOfFloors(); floor++)
            for (int row = 0; row < getNumberOfRows(); row++)
                for (int place = 0; place < getNumberOfPlaces(); place++)
                    locations.add(new Location(floor, row, place));

        return locations;
    }

    public void tick() {
        for (Location location : locations()) {
            Car car = getCarAt(location);
            if (car != null)
                car.tick();
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
        for (Location location : locations()) {
            Car car = getCarAt(location);
            if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying())
                return car;
        }
        return null;
    }

    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        return floor >= 0 && floor < settings.numberOfFloors && row >= 0 && row <= settings.numberOfRows && place >= 0 && place <= settings.numberOfPlaces;
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

    private void addArrivingCars(int numberOfCars, CarType type) {
        for (Car car : reservationLogic.getReservationCars()) {
            if (car.getEntranceTime()[0] == getHour() && car.getEntranceTime()[1] == getMinute() && !entranceCarQueue.getQueue().contains(car)) {
                entranceCarQueue.addCar(car);
            }
            if (car instanceof ReservationCar) {
                totalEarned += 2; // Voegt 1 euro toe aan de totalEarned voordat de gereserveerde auto al op de plek staat.
            }
        }

        IntStream.range(0, numberOfCars).forEach(i -> {
            Car newCar;
            switch (type) {
                case PARKINGPASS:
                    newCar = new ParkingPassCar(0);
                    break;
                case RESERVED:
                    newCar = new ReservationCar(settings.defaultPrice);
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

    public int getParkingPassEarnings() {
        return parkingPassEarnings;
    }

    public void setParkingPassEarnings(int parkingPassEarnings) {
        this.parkingPassEarnings = parkingPassEarnings;
    }

}
