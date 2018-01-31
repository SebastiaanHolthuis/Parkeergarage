package projectgroep.parkeergarage.view;

import javax.swing.JPanel;

import projectgroep.parkeergarage.fx.View;
import projectgroep.parkeergarage.logic.AbstractModel;
import projectgroep.parkeergarage.logic.ParkeerLogic;

@SuppressWarnings("serial")
public abstract class SwingView extends JPanel implements View {

    protected ParkeerLogic model;

    public SwingView(ParkeerLogic model) {
        this.model = model;
    }

    public AbstractModel getModel() {
        return model;
    }

    public void updateView() {
        repaint();
    }

}
