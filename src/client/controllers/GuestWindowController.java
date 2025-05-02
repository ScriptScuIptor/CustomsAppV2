package client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GuestWindowController {

    @FXML
    private Button registerButton;

    @FXML
    private Button loginButton;

    @FXML
    public void initialize() {
        registerButton.setOnAction(e -> register());
        loginButton.setOnAction(e -> login());
    }

    private void register() {
        // Логика регистрации
        System.out.println("Регистрация нового пользователя");
    }

    private void login() {
        // Логика входа
        System.out.println("Вход в систему");
    }
}
