package projectgroep.parkeergarage;

import javax.swing.*;
import nl.hanze.t12.life.logic.*;

public abstract class AbstractView extends JPanel {
	private static final long serialVersionUID = 6437976554496769048L;
	protected Simulator simulator;

	public AbstractView(Simulator life) {
		this.simulator=life;
//		life.addView(this);
	}
	
	public Simulator getModel() {
		return simulator;
	}
	
	public void updateView() {
		repaint();
	}
}
