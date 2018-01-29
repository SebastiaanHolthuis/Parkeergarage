package projectgroep.parkeergarage.view;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import projectgroep.parkeergarage.logic.ParkeerLogic;

public class ChartView extends AbstractView {
	
	private Dimension size;
	
	 public ChartView(ParkeerLogic model) {
	        super(model);
	 
	        size = new Dimension(0, 0);
	       
	    }
	 
	    private JPanel createChartPanel() {
	    	 String chartTitle = "Objects Movement Chart";
	    	    String xAxisLabel = "Omzet";
	    	    String yAxisLabel = "Dagen";
	    	 
	    	    XYDataset dataset = createDataset();
	    	 
	    	    JFreeChart chart = ChartFactory.createXYLineChart(chartTitle,
	    	            xAxisLabel, yAxisLabel, dataset);
	    	 
	    	    return new ChartPanel(chart);
	    }
	 
	    private XYDataset createDataset() {
	    	XYSeriesCollection dataset = new XYSeriesCollection();
	        XYSeries series1 = new XYSeries("Omzet:");
	        
	        
	        series1.add(model.getTotalEarned(), 100);
	        
	        dataset.addSeries(series1);
	        return dataset;
	    }
}


