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
import projectgroep.parkeergarage.logic.cars.ParkingPassCar;

public class TextStatisticsView extends AbstractView {

    private Dimension size;
    private JLabel aantalauto;
    private JLabel beschikbaar;
    private JLabel pashouder;
    private JLabel reservatie;

    public TextStatisticsView(ParkeerLogic model) {
        super(model);

        size = new Dimension(0, 2);
        setLayout(new GridLayout(0, 1));
        addComponents();
    }

    public void updateView() {
        aantalauto.setText("Totaal aantal auto's: " + model.getAllCars().count());
        beschikbaar.setText("Beschikbare plaatsen: " + model.getNumberOfOpenSpots());
        pashouder.setText("Aantal pashouders: " + model.getParkingPassCars().count());
        reservatie.setText("Aantal reservatie's: " + model.getReservedCars().count());
        repaint();
    }

    private void addComponents() {
        Box box = Box.createVerticalBox();
        add(box);

        JLabel parkeerstats = new JLabel("Parkeer garage statistics:");
        parkeerstats.setFont(new Font("Ariel", Font.BOLD, 20));
        box.add(parkeerstats);

        aantalauto = new JLabel("Totaal aantal auto's: ");
        box.add(aantalauto);

        beschikbaar = new JLabel("Beschikbare plaatsen: ");
        box.add(beschikbaar);

        pashouder = new JLabel("Aantal Pashouders: ");
        box.add(pashouder);

        reservatie = new JLabel("Aantal reservatie's :");
        box.add(reservatie);

        JLabel gemiddeld = new JLabel("Gemiddelde auto's per dag: ");
        box.add(gemiddeld);

        JLabel omzet = new JLabel("Omzet per dag: ");
        box.add(omzet);


    }

}
