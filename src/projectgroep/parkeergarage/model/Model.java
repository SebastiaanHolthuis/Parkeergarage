package projectgroep.parkeergarage.model;

import java.util.ArrayList;

import projectgroep.parkeergarage.view.View;

public class Model implements Runnable {

	private ArrayList<View> views;
	
	public Model() {
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
