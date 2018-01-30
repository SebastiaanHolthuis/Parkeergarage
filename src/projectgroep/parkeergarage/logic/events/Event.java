package projectgroep.parkeergarage.logic.events;

import java.util.ArrayList;

import projectgroep.parkeergarage.logic.cars.Car;

public class Event {
	
	private String name;
	
	private int day;
	private int hour;
	private int minute;
		
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
	public Event(String name, int day, int hour, int minute, int[] duration, int expectedVisitors) {
		this.name = name;
		
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		
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

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
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
