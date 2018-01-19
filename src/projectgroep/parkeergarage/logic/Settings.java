package projectgroep.parkeergarage.logic;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Dit zou een singleton of een data object kunnen worden.
 * Hij wordt straks gebruikt door de ParkeerLogic en SettingsView.
 * @author reinvdwoerd
 */

public class Settings {
	/**
	 * All settings fields
	 */
    int numberOfFloors;
    int numberOfRows;
    int numberOfPlaces;
    int numberOfOpenSpots;

    int weekDayArrivals= 100; // average number of arriving cars per hour
    int weekendArrivals = 200; // average number of arriving cars per hour
    int weekDayPassArrivals= 50; // average number of arriving cars per hour
    int weekendPassArrivals = 5; // average number of arriving cars per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 5; // number of cars that can leave per minute
    

	
	/**
	 * Instance methods
	 */
	public Stream<String> fieldNames() {
		Field[] declaredFields =  getClass().getDeclaredFields();
		return Arrays.asList(declaredFields).stream().map(f -> f.getName());
	}
	
    /**
     * Save/Load
     */
	static Settings getSavedSettings () {
		return new Settings();
	}
}
