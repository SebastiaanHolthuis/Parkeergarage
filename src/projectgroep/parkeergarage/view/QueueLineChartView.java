package projectgroep.parkeergarage.view;

import projectgroep.parkeergarage.logic.ParkeerLogic;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.function.Function;


public class QueueLineChartView extends AbstractLineChartView {
    public QueueLineChartView(ParkeerLogic model) {
        super("Wachtrijen", model, new LinkedHashMap<String, Function>() {{
            put("Adhoc auto's", x -> model.getEntranceCarQueue().carsInQueue());
            put("Pashouders", x -> model.getEntrancePassQueue().carsInQueue());
        }});
    }
}