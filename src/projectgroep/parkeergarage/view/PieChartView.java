package projectgroep.parkeergarage.view;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import projectgroep.parkeergarage.logic.ParkeerLogic;

public class PieChartView extends AbstractView {

    private Dimension size;
    private Image kekImage;

    public PieChartView(ParkeerLogic model) {
        super(model);

        size = new Dimension(0, 0);
    }

    public void updateView() {
    	add(createChartPanel());
    	repaint();
    }
    private JPanel createChartPanel() {
   	    PieDataset dataset = createDataset(); 	 
   	    JFreeChart chart = ChartFactory.createPieChart("Pie chart", dataset, true, false, false);
   	    chart.setBorderVisible(false);
   	    return new ChartPanel(chart);
   }

    private DefaultPieDataset createDataset() {

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Ad Hoc", model.getAdHocCars().count());
        dataset.setValue("Parking Pass", model.getParkingPassCars().count());

        return dataset;
    }

}