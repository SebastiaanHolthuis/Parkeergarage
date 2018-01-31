package projectgroep.parkeergarage.view;

import javax.swing.JTabbedPane;

import projectgroep.parkeergarage.logic.ParkeerLogic;

import java.util.HashMap;

public class ChartTabs extends AbstractView {
    JTabbedPane tabbedPane;

    public ChartTabs(ParkeerLogic parkeerLogic) {
        super(parkeerLogic);

        HashMap<AbstractView, String> views = new HashMap() {{
            put(new PieChartView(model), "Omzet");
            put(new LineCarChartView(model), "Aantal Auto's");
            put(new LineChartView(model), "?");
        }};

        tabbedPane = new JTabbedPane() {{
            views.forEach((chart, name) -> {
                add(chart, name);
                parkeerLogic.addView(chart);
            });
        }};

//        add(tabbedPane);
    }
}
