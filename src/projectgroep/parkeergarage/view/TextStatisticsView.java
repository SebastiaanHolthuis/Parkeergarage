package projectgroep.parkeergarage.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.logic.Settings;

import java.awt.Label;
import javax.swing.Box;
import javax.swing.JLabel;

public class TextStatisticsView extends AbstractView {


    private Dimension size;
    private JTable table;

    @SuppressWarnings("serial")
    public TextStatisticsView(ParkeerLogic model) {
        super(model);
        addComponents();
    }

    public void updateView() {
        table.getModel().setValueAt(model.getNumberOfOpenSpots(), 0, 1);
        table.getModel().setValueAt(model.getAllCars().count(), 1, 1);
        table.getModel().setValueAt(model.getParkingPassCars().count(), 2, 1);
        table.getModel().setValueAt(model.getReservationCars().count(), 3, 1);
        table.getModel().setValueAt(model.getEntranceCarQueue().carsInQueue(), 4, 1);
        table.getModel().setValueAt(model.getEntrancePassQueue().carsInQueue(), 5, 1);
        table.getModel().setValueAt("�" + model.getTotalEarned(), 6, 1);
        table.getModel().setValueAt(model.getSkipCount(), 7, 1);
        table.getModel().setValueAt("�" + model.getSkipCount() * 4, 8, 1);// price to pay ophalen.
        table.getModel().setValueAt(model.getDate(), 9, 1);
        repaint();
    }

    private void addComponents() {
        table = new JTable();
        table.setEnabled(false);
        table.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        table.setModel(new DefaultTableModel(
        	new Object[][] {
        		{"Beschikbare plaatsen", new Integer(0)},
        		{"Aantal auto's", new Integer(0)},
        		{"Aantal pashouders", new Integer(0)},
        		{"Aantal reservaties", new Integer(0)},
        		{"Auto's in queue", new Integer(0)},
        		{"Pashouders in queue", new Integer(0)},
        		{"Totaal verdiend", new Integer(0)},
        		{"Skippende auto's", new Integer(0)},
        		{"Misgelopen omzet", new Integer(0)},
        		{"Tijd", new Integer(0)},
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
