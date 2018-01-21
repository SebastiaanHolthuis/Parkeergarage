package projectgroep.parkeergarage.logic;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;


/**
 * FIXME: dirty catch blocks
 */
public class Settings {
	/**
	 * All settings fields
	 */
    int numberOfFloors =3;
    int numberOfRows = 6;
    int numberOfPlaces = 30;

    int weekDayArrivals= 100; // average number of arriving cars per hour
    int weekendArrivals = 200; // average number of arriving cars per hour
    int weekDayPassArrivals= 50; // average number of arriving cars per hour
    int weekendPassArrivals = 5; // average number of arriving cars per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 5; // number of cars that can leave per minute
    
    /**
     * Constructors
     */
    public Settings() {}
    public Settings(HashMap<String, Integer> map) {
    	map.forEach((k, v) -> {
		    try {
		    	Field field = getClass().getDeclaredField(k);
				field.setInt(this, v);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {}
    	});
    }
	
	/**
	 * Getters
	 */
	public HashMap<String, Integer> asMap() {
		Field[] declaredFields =  getClass().getDeclaredFields();
		HashMap<String, Integer> result = new HashMap<>();
		for (Field field : declaredFields) {
			try {
				result.put(field.getName(), (Integer) field.get(this));
			} catch (IllegalArgumentException e) { } catch (IllegalAccessException e) { }
		}
		
		return result;
	}
	
	public Set<String> keys() {
		return asMap().keySet();
	}
	
    /**
     * Save/Load
     */
	static Settings getSavedSettings () {
		return new Settings();
	}
}
