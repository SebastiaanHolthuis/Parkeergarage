package projectgroep.parkeergarage.view.charts;

import projectgroep.parkeergarage.logic.ParkeerLogic;

import java.util.HashMap;
import java.util.function.Function;


public class QueueLineChartView extends AbstractLineChartView {
    public QueueLineChartView(ParkeerLogic model) {
        super("Wachtrijen", model, new HashMap<String, Function>() {{
            put("Adhoc auto's", x -> model.getEntranceCarQueue().carsInQueue());
            put("Pashouders", x -> model.getEntrancePassQueue().carsInQueue());
        }});
    }
}