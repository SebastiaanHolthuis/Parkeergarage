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

public class TextStatisticsView extends AbstractView {

	private Dimension 			size;
				private JLabel aantalauto;
				private JLabel aantalauto2;
	
	public TextStatisticsView(ParkeerLogic model) {
		super(model);
		
		size = new Dimension(0, 2);
		setLayout(new GridLayout(0,1));
		addComponents();
	}
	
	public void updateView() {
		aantalauto.setText("Aantal auto's: " + (540 - model.getNumberOfOpenSpots()));
		repaint();
	}
	
	private void addComponents() {
		Box box = Box.createVerticalBox();
		add(box);
		
		JLabel parkeerstats = new JLabel("Parkeer garage statistics:");
		parkeerstats.setFont(new Font("Ariel", Font.BOLD, 20));
        box.add( parkeerstats );

        aantalauto = new JLabel("Aantal auto's: ");
        box.add( aantalauto );
        
        JLabel beschikbaar = new JLabel("Beschikbare plaatsen: ") ;
        box.add( beschikbaar );
        
        JLabel gemiddeld = new JLabel("Gemiddelde auto's per dag: ");
        box.add( gemiddeld );
        
        JLabel omzet = new JLabel("Omzet per dag: "  );
        box.add( omzet );
        
	
	}

}
