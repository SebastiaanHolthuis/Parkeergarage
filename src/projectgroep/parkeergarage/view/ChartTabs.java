package projectgroep.parkeergarage.view;

import projectgroep.parkeergarage.logic.ParkeerLogic;

import javax.swing.*;

public class ChartTabs extends AbstractView {
    PieChartView pieChartView;
    JTabbedPane tabbedPane;

    public ChartTabs(ParkeerLogic model) {
        super(model);

        pieChartView = new PieChartView(model);

        tabbedPane = new JTabbedPane() {{
            add(pieChartView, "Omzet");
        }};

        add(tabbedPane);

        addViewsToModel();
    }

    void addViewsToModel() {
        model.addView(pieChartView);
    }
}
