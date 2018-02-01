package projectgroep.parkeergarage.logic.events;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.logic.cars.AdHocCar;
import projectgroep.parkeergarage.logic.cars.Car;
import projectgroep.parkeergarage.logic.cars.CarType;

public class Event {

	private ParkeerLogic model;

	private String name;

	private int[] startTime;

	private int durationHours;
	private int durationMinutes;

	private int expectedVisitors;

	private boolean started;

	private ArrayList<Car> visitors = new ArrayList<Car>();

	/**
	 * Constructor.
	 *
	 * @param name The name of the event.
	 * @param day The day that the event will take place.
	 * @param hour The hour that the event will start.
	 * @param minute The minute that the event will start.
	 * @param duration The total duration of the event.
	 * @param expectedVisitors The amount of visitors that are expected to show up at the event.
	 */
	public Event(ParkeerLogic model, String name, int day, int hour, int minute, int duration[], int expectedVisitors) {
		this.model = model;
		this.name = name;

		this.startTime = new int[3];
		this.startTime[0] = day;
		this.startTime[1] = hour;
		this.startTime[2] = minute;

		this.durationHours = duration[0];
		this.durationMinutes = duration[1];

		this.expectedVisitors = expectedVisitors;

		this.started = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int[] getStartTime() {
		return startTime;
	}

	public void setStartTime(int[] startTime) {
		this.startTime = startTime;
	}

	public int getDurationHours() {
		return durationHours;
	}

	public void setDurationHours(int durationHours) {
		this.durationHours = durationHours;
	}

	public int getDurationMinutes() {
		return durationMinutes;
	}

	public void setDurationMinutes(int durationMinutes) {
		this.durationMinutes = durationMinutes;
	}

	public int getExpectedVisitors() {
		return expectedVisitors;
	}

	public void setExpectedVisitors(int expectedVisitors) {
		this.expectedVisitors = expectedVisitors;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public ArrayList<Car> getVisitors() {
		return visitors;
	}

	public void setVisitors(ArrayList<Car> visitors) {
		this.visitors = visitors;
	}

	public void addVisitors() {
		IntStream.range(0, expectedVisitors).forEach(i -> {
			Random random = new Random();
			CarType type = CarType.AD_HOC;

			model.getEntranceCarQueue().addCar(new AdHocCar(model.getSettings().getDefaultPrice()));
		});
	}


//    public int getNumberOfCars() {
//        Random random = new Random();
//
//        double standardDeviation = expectedVisitors * 0.3;
//        double numberOfCarsPerHour = expectedVisitors + random.nextGaussian() * standardDeviation;
//
//        return (int) (Math.round(getCarMultiplier() * numberOfCarsPerHour / 60));
//    }
//
//    private double getCarMultiplier() {
//        double period = (2 * Math.PI) / 24;
//        double multiplier = (double) durationHours + (double) durationMinutes / 60;
//        return 0.4 + 0.6 * (1 + Math.sin(period * (multiplier - (14 - 6.5))));
//    }

}
