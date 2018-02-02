package projectgroep.parkeergarage.view;

import projectgroep.parkeergarage.logic.ParkeerLogic;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.function.Function;


public class CarLineChartView extends AbstractLineChartView {
    public CarLineChartView(ParkeerLogic model) {
        super("Aantal auto's", model, new LinkedHashMap<String, Function>() {{
            put("Adhoc auto's", x -> model.getAdHocCars().count());
            put("Pashouders", x -> model.getParkingPassCars().count());
            put("Reservaties", x -> model.getReservationCars().count());
        }});
    }
}