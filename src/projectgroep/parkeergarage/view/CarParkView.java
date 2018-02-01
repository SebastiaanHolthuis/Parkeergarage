package projectgroep.parkeergarage.view;


import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import projectgroep.parkeergarage.logic.Location;
import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.logic.cars.Car;


public class CarParkView extends Canvas implements View {
    ParkeerLogic model;
    private GraphicsContext graphicsContext;
    ScrollPane scrollPane;

    public CarParkView(ParkeerLogic model) {
        super();

        this.model = model;

        Canvas canvas = new Canvas(canvasWidth(), canvasHeight());
        graphicsContext = canvas.getGraphicsContext2D();
        scrollPane = new ScrollPane();


        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 0.0);

        scrollPane.setContent(canvas);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPannable(true);

    }

    @Override
    public void updateView() {
        Platform.runLater(() -> {
            model.locations().forEach(location -> {
                Car car = model.getCarAt(location);
                Color color = car == null ? Color.web("#8bba8b") : car.getColor();
//                System.out.println("update");

                if (model.getReservationLogic().getReservations().values().contains(location)) {
                    if (car != null) {
                        color = car.getColor();
                    }
                } else {
                    if (car == null && location.getFloor() == 0 && location.getRow() < 2) {
                        color = Color.web("#ADDAF7"); // Blue
                    } else if (car == null) {
                        color = Color.web("#F0839E"); // Magenta
                    } else {
                        color = car.getColor();
                    }
                }

                drawPlace(location, color);
            });
        });
    }

    double canvasWidth() {
        return model.getNumberOfFloors() * 260 + (1 + (int) Math.floor(model.getNumberOfRows() * 0.5)) * 75 + (model.getNumberOfRows() % 2) * 20;
    }

    double canvasHeight() {
        return 60 + model.getNumberOfPlaces() * 10;
    }

    private void drawPlace(Location location, Color color) {
        graphicsContext.setFill(color);
        graphicsContext.fillRect(
                location.getFloor() * 260 + (1 + (int) Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                60 + location.getPlace() * 10,
                20 - 1,
                10 - 1); // TODO use dynamic size or constants
    }
}