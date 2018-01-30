package projectgroep.parkeergarage.view;

import javax.sound.sampled.Line;
import javax.swing.JTabbedPane;

import projectgroep.parkeergarage.logic.ParkeerLogic;

import java.util.List;

public class ChartTabs extends AbstractView {
    JTabbedPane tabbedPane;

    public ChartTabs(ParkeerLogic model) {
        super(model);

        AbstractView[] views = {
                new PieChartView(model),
                new LineCarChartView(model),
                new LineChartView(model)
        };

        tabbedPane = new JTabbedPane() {{
            for (AbstractView view : views) {
                add(view, view.SERIES_NAME);
                model.addView(pieChartView);
            }
        }};

        add(tabbedPane);
    }
}
