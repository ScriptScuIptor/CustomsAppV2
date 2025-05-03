package client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class GuestWindowController {

    @FXML
    private Button goToAdminButton;

    @FXML
    public void initialize() {
        // Обработка перехода на окно администратора
        goToAdminButton.setOnAction(e -> switchToAdminWindow());
    }

    // Переход на окно администратора
    private void switchToAdminWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/AdminWindow.fxml"));
            Stage stage = (Stage) goToAdminButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
