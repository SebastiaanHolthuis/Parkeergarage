package projectgroep.parkeergarage.logic;

import java.util.ArrayList;

import projectgroep.parkeergarage.view.SwingView;
import projectgroep.parkeergarage.view.View;

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
