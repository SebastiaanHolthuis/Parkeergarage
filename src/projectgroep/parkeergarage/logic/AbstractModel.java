package projectgroep.parkeergarage.logic;

import projectgroep.parkeergarage.view.View;

import java.util.ArrayList;

public class AbstractModel implements Runnable {

    protected ArrayList<View> views;

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

    public void addView(View view) {
        views.add(view);
    }


}
