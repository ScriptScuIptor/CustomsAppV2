package client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;

public class AdminWindowController {

    @FXML
    private ListView<String> userListView;

    @FXML
    private Button addUserButton;

    @FXML
    private Button deleteUserButton;

    @FXML
    public void initialize() {
        addUserButton.setOnAction(e -> addUser());
        deleteUserButton.setOnAction(e -> deleteUser());
    }

    private void addUser() {
        // Логика добавления пользователя
        System.out.println("Добавление пользователя");
    }

    private void deleteUser() {
        // Логика удаления пользователя
        System.out.println("Удаление пользователя");
    }
}
