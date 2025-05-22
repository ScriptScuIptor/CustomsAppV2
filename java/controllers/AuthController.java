package controllers;

import CourseWork.Core.CourseWork.Domain.Roles;
import CourseWork.Core.CourseWork.Domain.User;
import CourseWork.Presentation.MainAppManager;
import CourseWork.Server.Serializable.Auth.AuthRequest;
import CourseWork.Server.Serializable.Auth.AuthResponse;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class AuthController {
    @FXML
    private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void onLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isBlank() || password.isBlank()) {
            showAlert("Ошибка", "Имя пользователя и пароль не могут быть пустыми.", Alert.AlertType.WARNING);
            return;
        }

        try (Socket socket = new Socket("localhost", 5555);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            AuthRequest request = new AuthRequest(username, password);
            out.writeObject(request);
            out.flush();

            AuthResponse response = (AuthResponse) in.readObject();

            if (response.success && response.user != null) {
                User user = response.user;
                Roles role = Roles.fromValue(user.role);
                Stage stage = (Stage) usernameField.getScene().getWindow();

                showAlert("Успешный вход", "Добро пожаловать!", Alert.AlertType.INFORMATION);

                switch (role) {
                    case GUEST -> MainAppManager.showRegisterTransportWindow(stage, user);
                    case DRIVER -> MainAppManager.showRequestWindow(stage, user);
                    case ADMIN -> MainAppManager.showAdminMainWindow(stage);
                    default -> showAlert("Ошибка", "Неизвестная роль пользователя.", Alert.AlertType.ERROR);
                }
            } else {
                showAlert("Ошибка входа", response.message, Alert.AlertType.ERROR);
            }

        } catch (IOException | ClassNotFoundException e) {
            showAlert("Ошибка", "❌ Ошибка подключения к серверу", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void goToRegister() {
        Stage currentStage = (Stage) usernameField.getScene().getWindow();
        MainAppManager.showRegisterWindow(currentStage);
    }
}
