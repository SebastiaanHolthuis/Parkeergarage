package projectgroep.parkeergarage.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.function.Function;

import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import projectgroep.parkeergarage.logic.ParkeerLogic;

public class TextStatisticsView extends AbstractView {
    private Dimension size;
    private JTable table;

    public TextStatisticsView(ParkeerLogic model) {
        super(model);
        addComponents();
    }

    @Override
    public void updateView() {
<<<<<<< HEAD
        table.getModel().setValueAt(model.getNumberOfOpenSpots(), 0, 1);
        table.getModel().setValueAt(model.getAllCars().count(), 1, 1);
        table.getModel().setValueAt(model.getParkingPassCars().count(), 2, 1);
        table.getModel().setValueAt(model.getReservationCars().count(), 3, 1);
        table.getModel().setValueAt(model.getEntranceCarQueue().carsInQueue(), 4, 1);
        table.getModel().setValueAt(model.getEntrancePassQueue().carsInQueue(), 5, 1);
        table.getModel().setValueAt("ï¿½" + model.getTotalEarned(), 6, 1);
        table.getModel().setValueAt(model.getSkipCount(), 7, 1);
        table.getModel().setValueAt("ï¿½" + model.getSkipCount() * 4, 8, 1);// price to pay ophalen.
        table.getModel().setValueAt(model.getDay(), 9, 1);
        table.getModel().setValueAt(model.getTime(), 10, 1);
        
=======
        Function[] statistics = {
                x -> model.getNumberOfOpenSpots(),
                x -> model.getAllCars().count(),
                x -> model.getParkingPassCars().count(),
                x -> model.getReservationCars().count(),
                x -> model.getEntranceCarQueue().carsInQueue(),
                x -> model.getEntrancePassQueue().carsInQueue(),
                x -> "€" + model.getTotalEarned(),
                x -> model.getSkipCount(),
                x -> "€" + model.getSkipCount() * 4,
                x -> model.getDay(),
                x -> model.getTime(),
                x -> model.history.stepsBack()
        };

        for (int i = 0; i < statistics.length; i++)
            table.getModel().setValueAt(statistics[i].apply(null), i, 1);

>>>>>>> branch 'develop' of https://github.com/SebastiaanHolthuis/Parkeergarage.git
        repaint();
    }

    private void addComponents() {
        table = new JTable();
        table.setEnabled(false);
        table.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 0, 0)));
        table.setModel(new DefaultTableModel(
<<<<<<< HEAD
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
        		{"Dag", new Integer(0)},
        		{"Tijd", new Integer(0)},
        	},
        	new String[] {
        		"Variabele", "Waarde"
        	}
=======
                new Object[][]{
                        {"Beschikbare plaatsen", new Integer(0)},
                        {"Aantal auto's", new Integer(0)},
                        {"Aantal pashouders", new Integer(0)},
                        {"Aantal reservaties", new Integer(0)},
                        {"Auto's in queue", new Integer(0)},
                        {"Pashouders in queue", new Integer(0)},
                        {"Totaal verdiend", new Integer(0)},
                        {"Skippende auto's", new Integer(0)},
                        {"Misgelopen omzet", new Integer(0)},
                        {"Dagen", new Integer(0)},
                        {"Tijd", new Integer(0)},
                        {"Stappen terug", new Integer(0)},
                },
                new String[]{
                        "Variabele", "Waarde"
                }
>>>>>>> branch 'develop' of https://github.com/SebastiaanHolthuis/Parkeergarage.git
        ) {
            boolean[] columnEditables = new boolean[]{
                    true, false
            };

            @Override
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
