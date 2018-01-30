package projectgroep.parkeergarage.fx;

import javafx.scene.layout.VBox;
import projectgroep.parkeergarage.logic.AbstractModel;
import projectgroep.parkeergarage.logic.ParkeerLogic;

public abstract class View extends VBox {
    protected ParkeerLogic model;

    public View(ParkeerLogic model) {
        this.model = model;
    }

    public AbstractModel getModel() {
        return model;
    }

    public void updateView() {

    }
}
