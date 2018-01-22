package projectgroep.parkeergarage.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import projectgroep.parkeergarage.logic.ParkeerLogic;
import java.awt.Label;
import javax.swing.Box;
import javax.swing.JLabel;

public class TextStatisticsView extends AbstractView {

	private Dimension 			size;
	private JTable table;
	
	@SuppressWarnings("serial")
	public TextStatisticsView(ParkeerLogic model) {
		super(model);
		
		size = new Dimension(0, 2);
		setLayout(new GridLayout(0,1));
		
		addComponents();
	}
	
	public void updateView() {
		table.getModel().setValueAt(model.getAllCars().count(), 0, 1);
		table.getModel().setValueAt(model.getNumberOfOpenSpots(), 1, 1);
		table.getModel().setValueAt( model.getReservedCars().count(), 2, 1);
		table.getModel().setValueAt( model.getParkingPassCars().count(), 3, 1);
		repaint();
	}
	
	private void addComponents() {      
		table = new JTable();
		table.setEnabled(false);
		table.setFillsViewportHeight(true);
		table.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"Aantal auto's", new Integer(0)},
				{"Beschikbare plaatsen", new Integer(0)},
				{"Aantal reservatie's", new Integer(0)},
				{"Aantal pashouder's", new Integer(0)},
			},
			new String[] {
				"Variabele", "Waarde"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(125);
		table.setRowHeight(40);
		
		add(table);
	}
}
