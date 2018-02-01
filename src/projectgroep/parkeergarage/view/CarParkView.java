package projectgroep.parkeergarage.view;


import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.logic.cars.Car;
import projectgroep.parkeergarage.logic.locations.Location;


public class CarParkView extends Canvas implements View {
    ParkeerLogic model;
    GraphicsContext graphicsContext;
    Image background = new Image("file:img/transp_bg.png");
    int tileSize = 50;

    public CarParkView(ParkeerLogic model) {
        super(canvasWidth(model), canvasHeight(model));
        this.model = model;
        graphicsContext = getGraphicsContext2D();

        Platform.runLater(() -> {
            drawBackground();
        });
    }

    static double canvasWidth(ParkeerLogic model) {
        int base = (model.getNumberOfFloors() * 260);
        int a = ((1 + (int) Math.floor(model.getNumberOfRows() * 0.5)) * 75);
        int b = ((model.getNumberOfRows() % 2) * 20);
        int total = base + a + b - 215;

        return Math.max(1200, total);
    }

    static double canvasHeight(ParkeerLogic model) {
        int total = 120 + model.getNumberOfPlaces() * 10;

        return Math.max(900, total);
    }


    @Override
    public void updateView() {
        Platform.runLater(() -> {
            model.locations().forEach(location -> {
                Car car = model.getCarAt(location);
                Color color = car == null ? Color.web("#8bba8b") : car.getColor();

                if (model.getReservationLogic().getReservations().values().contains(location)) {
                    if (car != null) {
                        color = car.getColor();
                    }
                } else {
                    if (car == null && location.getFloor() == 0 && location.getRow() < model.getSettings().getNumberOfPassHolderRows()) {
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

    private void drawBackground() {
        for (int x = 0; x < canvasWidth(model); x += tileSize)
            for (int y = 0; y < canvasHeight(model); y += tileSize)
                graphicsContext.drawImage(background, x, y, tileSize, tileSize);
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