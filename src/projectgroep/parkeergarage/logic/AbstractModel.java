package projectgroep.parkeergarage.logic;

import java.util.ArrayList;

import projectgroep.parkeergarage.view.AbstractView;

public class AbstractModel implements Runnable {

    protected ArrayList<AbstractView> views;

    public AbstractModel() {
        views = new ArrayList<>();
    }

    @Override
    public void run() {
        notifyViews();
    }

    protected void notifyViews() {
        views.forEach(v -> v.updateView());
    }

    public void addView(AbstractView view) {
        views.add(view);
    }


}
