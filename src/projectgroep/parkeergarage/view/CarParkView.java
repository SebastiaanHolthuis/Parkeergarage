package projectgroep.parkeergarage.view;


import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import projectgroep.parkeergarage.logic.Location;
import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.logic.cars.Car;


public class CarParkView extends AbstractView {
    ParkeerLogic model;
    private GraphicsContext graphicsContext;
    ScrollPane container;

    public CarParkView(ParkeerLogic model) {
        super();

        this.model = model;

        Canvas canvas = new Canvas(865, 1000);
        graphicsContext = canvas.getGraphicsContext2D();

        container = new ScrollPane();

        container.setPrefHeight(800);
        container.setPrefWidth(600);
        container.setContent(canvas);
        container.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        container.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        getChildren().add(container);
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

    private void drawPlace(Location location, Color color) {
        graphicsContext.setFill(color);
        graphicsContext.fillRect(
                location.getFloor() * 260 + (1 + (int) Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                60 + location.getPlace() * 10,
                20 - 1,
                10 - 1); // TODO use dynamic size or constants
    }
}