package projectgroep.parkeergarage.logic;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;


/**
 * FIXME: dirty catch blocks
 */
public class Settings implements Serializable {
    /**
     * All settings fields
     */
    int numberOfFloors = 3;
    int numberOfRows = 6;
    int numberOfPlaces = 30;
    int numberOfPassHolderRows = 2;
    int numberOfResFloors = 1;
    int numberOfResRows = 2;
    int defaultPrice = 4;

    int weekDayArrivals = 100; // average number of arriving AdHoc cars per hour
    int weekendArrivals = 200; // average number of arriving AdHoc cars per hour
    int weekDayPassArrivals = 50; // average number of arriving Passholder cars per hour
    int weekendPassArrivals = 5; // average number of arriving Passholder cars per hour
    int weekDayResArrivals = 25; // average number of arriving Reserved cars per hour
    int weekendResArrivals = 10; // average number of arriving Reserved cars per hour

    int enterSpeed = 8; // number of cars that can enter per minute
    int paymentSpeed = 5; // number of cars that can pay per minute
    int exitSpeed = 7; // number of cars that can leave per minute

    /**
     * Constructors
     */
    public Settings() {
    }

    public Settings(HashMap<String, Integer> map) {
        map.forEach((k, v) -> {
            try {
                Field field = getClass().getDeclaredField(k);
                field.setInt(this, v);
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            }
        });
    }

    /**
     * Getters
     */
    public HashMap<String, Integer> asMap() {
        Field[] declaredFields = getClass().getDeclaredFields();
        HashMap<String, Integer> result = new HashMap<>();
        for (Field field : declaredFields) {
            try {
                result.put(field.getName(), (Integer) field.get(this));
            } catch (IllegalArgumentException | IllegalAccessException e) {
            }
        }

        return result;
    }

    public Set<String> keys() {
        return asMap().keySet();
    }
}
