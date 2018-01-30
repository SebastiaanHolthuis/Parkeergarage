
package projectgroep.parkeergarage.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import projectgroep.parkeergarage.logic.cars.Car;

public class LocationLogic {

    private HashMap<Location, String> locations = new HashMap<Location, String>();
    
    private ParkeerLogic model;

    public LocationLogic(ParkeerLogic model) {
        this.model = model;
    }

    public void addLocation(Location location, String carType) {
        if (!locations.containsKey(location)) {
            locations.put(location, carType);
        }
    }

    public void removeLocation(Location location) {
        if (locations.containsKey(location)) {
            locations.remove(location);
        }
    }

    public Boolean existsInMap(Location location) {
        return locations.containsKey(location);
    }

    public ArrayList<Location> getLocations(String carType) {
        ArrayList<Location> toReturn = new ArrayList<Location>();

        for (Map.Entry<Location, String> entry : locations.entrySet()) {
            if (entry.getValue().equals(carType)) {
                toReturn.add(entry.getKey());
            }
        }

        return toReturn;
    }

}
