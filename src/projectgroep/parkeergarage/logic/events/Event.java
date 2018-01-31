package projectgroep.parkeergarage.logic.events;

import java.util.ArrayList;

import projectgroep.parkeergarage.logic.cars.Car;

public class Event {
	
	private String name;
	
	private int[] startTime;
		
	private int durationHours;
	private int durationMinutes;
	
	private int expectedVisitors;

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
	public Event(String name, int day, int hour, int minute, int duration[], int expectedVisitors) {
		this.name = name;
		
		this.startTime = new int[3];
		this.startTime[0] = day;
		this.startTime[1] = hour;
		this.startTime[2] = minute;
		
		this.durationHours = duration[0];
		this.durationMinutes = duration[1];
		
		this.expectedVisitors = expectedVisitors;
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

	public ArrayList<Car> getVisitors() {
		return visitors;
	}

	public void setVisitors(ArrayList<Car> visitors) {
		this.visitors = visitors;
	}
	
}
