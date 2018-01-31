package projectgroep.parkeergarage.logic;

import projectgroep.parkeergarage.view.AbstractView;

import java.util.ArrayList;

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
