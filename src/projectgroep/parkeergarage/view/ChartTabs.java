package projectgroep.parkeergarage.view;

import javax.swing.JTabbedPane;

import projectgroep.parkeergarage.logic.ParkeerLogic;

public class ChartTabs extends AbstractView {
    PieChartView pieChartView;
    LineCarChartView lineCarChartView;
    LineChartView lineChartView;

    JTabbedPane tabbedPane;

    public ChartTabs(ParkeerLogic model) {
        super(model);

        pieChartView = new PieChartView(model);
        lineCarChartView = new LineCarChartView(model);
        lineChartView = new LineChartView(model);

        tabbedPane = new JTabbedPane() {{
            add(pieChartView, "Omzet");
            add(lineCarChartView, LineCarChartView.SERIES_NAME);
            add(lineChartView, "");
        }};

        add(tabbedPane);

        addViewsToModel();
    }

    void addViewsToModel() {
        model.addView(pieChartView);
        model.addView(lineCarChartView);
        model.addView(lineChartView);
    }
}
