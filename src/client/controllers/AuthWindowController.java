package client.controllers;

import client.AuthWindowApp;
import database.DatabaseManager;
import model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import java.io.IOException;

public class AuthWindowController {

    @FXML
    private TextField userText;  // Поле ввода для логина

    @FXML
    private PasswordField passwordText;  // Поле ввода для пароля

    @FXML
    private Button loginButton;  // Кнопка для входа

    @FXML
    private Button registerButton;  // Кнопка для регистрации

    @FXML
    private Label statusLabel;  // Статусная метка

    private AuthWindowApp authWindowApp;

    // Инициализация кнопок
    @FXML
    public void initialize() {
        loginButton.setOnAction(e -> login());
        registerButton.setOnAction(e -> register());
    }

    // Метод для установки ссылки на AuthWindowApp
    public void setAuthWindowApp(AuthWindowApp authWindowApp) {
        this.authWindowApp = authWindowApp;
    }

    private void login() {
        String username = userText.getText();
        String password = passwordText.getText();

        // Проверка на пустые поля
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            statusLabel.setText("❌ Логин и пароль не могут быть пустыми.");
            return;
        }

        // Получаем пользователя из базы данных
        User user = DatabaseManager.findUserByUsername(username);

        // Если пользователь не найден или пароль не совпадает
        if (user == null || !user.getPassword().equals(password)) {
            statusLabel.setText("❌ Неверные учетные данные.");
            return;
        }

        // Если пароль верный, проверяем role_id и переходим на нужную страницу
        try {
            switch (user.getRole()) {
                case "admin":
                    authWindowApp.switchToWindow("admin"); // Переход на окно администратора
                    break;
                case "employee":
                    authWindowApp.switchToWindow("employee"); // Переход на окно водителя
                    break;
                case "guest":
                    authWindowApp.switchToWindow("guest"); // Переход на окно гостя
                    break;
                default:
                    statusLabel.setText("❌ Роль пользователя не найдена.");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace(); // Выводим трассировку стека для отладки
            statusLabel.setText("❌ Ошибка при подключении к серверу.");
        }
    }

    private void register() {
        String username = userText.getText();
        String password = passwordText.getText();

        // Проверка на пустые поля
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            statusLabel.setText("❌ Логин и пароль не могут быть пустыми.");
            return;
        }

        // Используем метод addUser из DatabaseManager для добавления пользователя
        if (DatabaseManager.addUser(username, password, "guest")) {
            statusLabel.setText("✅ Регистрация прошла успешно.");
        } else {
            statusLabel.setText("❌ Ошибка при регистрации.");
        }
    }
}
