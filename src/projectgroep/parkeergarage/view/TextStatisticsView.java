package projectgroep.parkeergarage.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;

import projectgroep.parkeergarage.logic.ParkeerLogic;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.MatteBorder;

public class TextStatisticsView extends AbstractView {

	private Dimension 			size;
	private JTable table;
	
	public TextStatisticsView(ParkeerLogic model) {
		super(model);
		
		size = new Dimension(0, 2);
		setLayout(new GridLayout(0,1));
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0, 0)));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"Aantal auto's", new Integer(0)},
				{"Beschikbare plaatsen", new Integer(0)},
			},
			new String[] {
				"Variabele", "Waarde"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(125);
		add(table);
		addComponents();
	}
	
	public void updateView() {
		table.getModel().setValueAt(((model.getNumberOfFloors()*model.getNumberOfPlaces()*model.getNumberOfRows()) - model.getNumberOfOpenSpots()), 0, 1);
		table.getModel().setValueAt(model.getNumberOfOpenSpots(), 1, 1);
		repaint();
	}
	
	private void addComponents() {
        
        
	
	}

}
