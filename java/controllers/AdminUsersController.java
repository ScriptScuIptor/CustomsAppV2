package controllers;

import CourseWork.Core.CourseWork.Domain.User;
import CourseWork.Presentation.NavigationHelper;
import CourseWork.Server.Serializable.Admin.AdminRequest;
import CourseWork.Server.Serializable.Admin.AdminResponse;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AdminUsersController {
    @FXML private TableView<User> usersTable;
    @FXML private TableColumn<User, String> userIdColumn;
    @FXML private TableColumn<User, String> usernameColumn;
    @FXML private TableColumn<User, Integer> roleColumn;

    @FXML private TextField userIdToDelete;
    @FXML private TextField searchField;
    @FXML private Label statusLabel;

    private final String HOST = "localhost";
    private final int PORT = 5555;
    private List<User> allUsers;

    @FXML
    public void initialize() {
        loadUsers();
    }

    @FXML
    public void loadUsers() {
        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(new AdminRequest(AdminRequest.OperationType.GET_ALL_USERS));
            AdminResponse response = (AdminResponse) in.readObject();

            if (response.success && response.users != null) {
                allUsers = response.users;
                userIdColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().UserId.toString()));
                usernameColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().username));
                roleColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().role).asObject());
                usersTable.setItems(FXCollections.observableArrayList(allUsers));
                statusLabel.setText("");
            }

        } catch (IOException | ClassNotFoundException e) {
            statusLabel.setText("❌ Ошибка загрузки пользователей");
        }
    }

    @FXML
    private void deleteUser() {
        try {
            UUID id = UUID.fromString(userIdToDelete.getText().trim());

            try (Socket socket = new Socket(HOST, PORT);
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                out.writeObject(new AdminRequest(AdminRequest.OperationType.DELETE_USER, id));
                AdminResponse response = (AdminResponse) in.readObject();
                statusLabel.setText(response.message);
                loadUsers();

            }

        } catch (IllegalArgumentException e) {
            statusLabel.setText("❌ Неверный UUID");
        } catch (IOException | ClassNotFoundException e) {
            statusLabel.setText("❌ Ошибка при удалении пользователя");
        }
    }

    @FXML
    private void searchUser() {
        String keyword = searchField.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            usersTable.setItems(FXCollections.observableArrayList(allUsers));
        } else {
            var filtered = allUsers.stream()
                    .filter(u -> u.username.toLowerCase().contains(keyword))
                    .collect(Collectors.toList());
            usersTable.setItems(FXCollections.observableArrayList(filtered));
        }
    }
    @FXML
    private void copySelectedUserId() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(selectedUser.UserId.toString());
            clipboard.setContent(content);
            statusLabel.setText("📋 UUID скопирован: " + selectedUser.UserId);
        } else {
            statusLabel.setText("❌ Пользователь не выбран");
        }
    }
    @FXML
    private void onBack() {
        NavigationHelper.goBack(statusLabel); // используем любую ноду сцены
    }
}
