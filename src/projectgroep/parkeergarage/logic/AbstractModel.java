package projectgroep.parkeergarage.logic;

import java.util.ArrayList;

import projectgroep.parkeergarage.view.SwingView;

public class AbstractModel implements Runnable {

    protected ArrayList<SwingView> views;

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

    public void addView(SwingView view) {
        views.add(view);
    }


}
