package projectgroep.parkeergarage.logic;

import java.lang.reflect.Field;
import java.util.HashMap;

public abstract class MapLike {
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
