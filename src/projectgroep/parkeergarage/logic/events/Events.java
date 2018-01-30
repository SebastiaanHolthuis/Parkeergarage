package projectgroep.parkeergarage.logic.events;

import java.util.ArrayList;

import projectgroep.parkeergarage.logic.ParkeerLogic;

public class Events {

	private ParkeerLogic model;
	private ArrayList<Event> events = new ArrayList<Event>();
	
	/**
	 * Constructor.
	 * 
	 * @param model The ParkeerLogic model instance.
	 */
	public Events(ParkeerLogic model) {
		this.model = model;
	}
	
	/**
	 * Add an instance of an Event object to the events ArrayList.
	 * 
	 * @param event The event to add
	 */
	public void addEvent(Event event) {
		if (!events.contains(event)) events.add(event);
	}
	
	/**
	 * Add a new Event to the events ArrayList.
	 * 
	 * @param name The name of the event.
	 * @param day The day that the event will take place.
	 * @param hour The hour that the event will start.
	 * @param minute The minute that the event will start.
	 * @param duration The total duration of the event.
	 * @param expectedVisitors The amount of visitors that are expected to show up at the event.
	 */
	public void addEvent(String name, int day, int hour, int minute, int[] duration, int expectedVisitors) {
		events.add(new Event(name, expectedVisitors, expectedVisitors, expectedVisitors, duration, expectedVisitors));				
	}
	
	/**
	 * Remove an Event from the events ArrayList.
	 * 
	 * @param event The event to remove.
	 */
	public void removeEvent(Event event) {
		if (events.contains(event)) events.remove(event);
	}
	
	/**
	 * Remove an Event from the events ArrayList by its name.
	 * 
	 * @param name The name of the event.
	 */
	public void removeEvent(String name) {
		events.forEach(event -> {
			if (event.getName().equals(name)) events.remove(event);
		});
	}

}
