package controllers;

import CourseWork.Presentation.MainAppManager;
import CourseWork.Server.Serializable.Auth.RegisterRequest;
import CourseWork.Server.Serializable.Auth.RegisterResponse;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class RegisterController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void onRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            showAlert("Ошибка", "Имя пользователя и пароль не должны быть пустыми", Alert.AlertType.WARNING);
            return;
        }

        try (Socket socket = new Socket("localhost", 5555);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            RegisterRequest request = new RegisterRequest(username, password);
            out.writeObject(request);
            out.flush();

            RegisterResponse response = (RegisterResponse) in.readObject();
            if (response.success) {
                showAlert("Успех", response.message, Alert.AlertType.INFORMATION);
            } else {
                showAlert("Ошибка", response.message, Alert.AlertType.ERROR);
            }

        } catch (IOException | ClassNotFoundException e) {
            showAlert("Сервер", "❌ Ошибка подключения к серверу.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void goToAuth() {
        Stage currentStage = (Stage) usernameField.getScene().getWindow();
        MainAppManager.showAuthWindow(currentStage);
    }
}
