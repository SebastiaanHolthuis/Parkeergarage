package projectgroep.parkeergarage.logic;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Set;

public abstract class MapLike implements Serializable {

    /**
     * Constructors
     */
    public MapLike() {
    }

    public MapLike(HashMap<String, Object> map) {

    }


    /**
     * Getters
     */
    public Set<String> keys() {
        return asMap().keySet();
    }

    public HashMap<String, Object> asMap() {
        Field[] declaredFields = getClass().getDeclaredFields();
        HashMap<String, Object> result = new HashMap<>();
        for (Field field : declaredFields) {
            try {
                result.put(field.getName(), field.get(this));
            } catch (IllegalArgumentException | IllegalAccessException e) {
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return asMap().toString();
    }
}
