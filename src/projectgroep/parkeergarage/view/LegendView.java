package projectgroep.parkeergarage.view;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.logic.cars.AdHocCar;
import projectgroep.parkeergarage.logic.cars.ParkingPassCar;
import projectgroep.parkeergarage.logic.cars.ReservationCar;

public class LegendView extends AbstractView {
    ParkeerLogic model;
    private GraphicsContext g;

    public LegendView(ParkeerLogic model) {
        super();

        Canvas canvas = new Canvas(200, 100);
        g = canvas.getGraphicsContext2D();
        getChildren().add(canvas);
    }

    @Override
    public void updateView() {
        drawLegend();
    }

    public void drawLegend() {
        g.setStroke(Color.WHITE);
        g.setFill(Color.BLACK);

        drawLegendItem("Ad Hoc", AdHocCar.COLOR, 0);
        drawLegendItem("Abonnementhouder", ParkingPassCar.COLOR, 1);
        drawLegendItem("Reservatie", ReservationCar.COLOR, 2);
        drawLegendItem("Gereserveerde plek", Color.web("#8bba8b"), 3);
    }

    private void drawLegendItem(String text, Color color, int position) {
        g.setFill(color);
        g.fillRect(10, 10 + (position * 30), 50, 20);

        g.setStroke(Color.BLACK);
        g.strokeText(text, 70, 25 + (position * 30));
    }
}