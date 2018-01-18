package projectgroep.parkeergarage.logic;

import java.util.ArrayList;

import projectgroep.parkeergarage.view.AbstractView;

public class AbstractModel implements Runnable {

	private ArrayList<AbstractView> views;
	
	public AbstractModel() {
		views = new ArrayList<>();
	}
	
	@Override
	public void run() {
		notifyViews();
	}
	
	private void notifyViews() {
		views.forEach(v -> v.repaint());
	}

}
