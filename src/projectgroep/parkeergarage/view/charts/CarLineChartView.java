package projectgroep.parkeergarage.view.charts;

import projectgroep.parkeergarage.logic.ParkeerLogic;

import java.util.HashMap;
import java.util.function.Function;


public class CarLineChartView extends AbstractLineChartView {
    public CarLineChartView(ParkeerLogic model) {
        super("Aantal auto's", model, new HashMap<String, Function>() {{
            put("Adhoc auto's", x -> model.getAdHocCars().count());
            put("Pashouders", x -> model.getParkingPassCars().count());
            put("Reservaties", x -> model.getReservationCars().count());
        }});
    }
}