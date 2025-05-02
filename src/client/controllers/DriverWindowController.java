package client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;

public class DriverWindowController {

    @FXML
    private ListView<String> vehicleListView;

    @FXML
    private Button addVehicleButton;

    @FXML
    public void initialize() {
        addVehicleButton.setOnAction(e -> addVehicle());
    }

    private void addVehicle() {
        // Логика добавления транспортного средства
        System.out.println("Добавление транспортного средства");
    }
}
