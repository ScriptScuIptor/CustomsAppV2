package controllers;

import CourseWork.Server.Serializable.Admin.AdminRequest;
import CourseWork.Server.Serializable.Admin.AdminResponse;
import CourseWork.Server.Serializable.Admin.RequestWithDriverInfo;
import CourseWork.Core.CourseWork.Domain.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

public class AdminController {
    @FXML private TableView<RequestWithDriverInfo> requestsTable;
    @FXML private TableColumn<RequestWithDriverInfo, String> descriptionColumn;
    @FXML private TableColumn<RequestWithDriverInfo, Boolean> approvedColumn;
    @FXML private TableColumn<RequestWithDriverInfo, String> driverColumn;
    @FXML private TableColumn<RequestWithDriverInfo, String> vehicleColumn;

    @FXML private TableView<User> usersTable;
    @FXML private TableColumn<User, String> userIdColumn;
    @FXML private TableColumn<User, String> usernameColumn;
    @FXML private TableColumn<User, Integer> roleColumn;

    @FXML private TextField userIdToDelete;
    @FXML private Label adminStatusLabel;

    private final String HOST = "localhost";
    private final int PORT = 5555;

    @FXML
    public void initialize() {
        loadUsers();
        loadRequests();
    }

    @FXML
    public void loadRequests() {
        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(new AdminRequest(AdminRequest.OperationType.GET_ALL_REQUESTS));
            AdminResponse response = (AdminResponse) in.readObject();

            if (response.success && response.requestsWithDriver != null) {
                descriptionColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().description));
                approvedColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleBooleanProperty(c.getValue().approved));
                driverColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().driverUsername));
                vehicleColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().vehicleNumber));
                requestsTable.setItems(FXCollections.observableArrayList(response.requestsWithDriver));
            }

        } catch (IOException | ClassNotFoundException e) {
            adminStatusLabel.setText("❌ Ошибка загрузки заявок");
        }
    }

    @FXML
    public void loadUsers() {
        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(new AdminRequest(AdminRequest.OperationType.GET_ALL_USERS));
            AdminResponse response = (AdminResponse) in.readObject();

            if (response.success && response.users != null) {
                userIdColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().UserId.toString()));
                usernameColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().username));
                roleColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().role).asObject());
                usersTable.setItems(FXCollections.observableArrayList(response.users));
            }

        } catch (IOException | ClassNotFoundException e) {
            adminStatusLabel.setText("❌ Ошибка загрузки пользователей");
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

                adminStatusLabel.setText(response.message);
                loadUsers();

            }

        } catch (IllegalArgumentException e) {
            adminStatusLabel.setText("❌ Неверный UUID");
        } catch (IOException | ClassNotFoundException e) {
            adminStatusLabel.setText("❌ Ошибка при удалении пользователя");
        }
    }

    @FXML
    private void approveSelected() {
        var selected = requestsTable.getSelectionModel().getSelectedItem();
        if (selected == null || selected.approved) {
            adminStatusLabel.setText("⚠️ Заявка уже одобрена или не выбрана");
            return;
        }

        selected.approved = true;

        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(new AdminRequest(AdminRequest.OperationType.APPROVE_REQUEST, selected.toRequest()));
            AdminResponse response = (AdminResponse) in.readObject();

            adminStatusLabel.setText(response.message);
            loadRequests();

        } catch (IOException | ClassNotFoundException e) {
            adminStatusLabel.setText("❌ Ошибка при одобрении заявки");
        }
    }

    @FXML
    private void copySelectedUserId() {
        var selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(selectedUser.UserId.toString());
            clipboard.setContent(content);
            adminStatusLabel.setText("📋 UUID скопирован: " + selectedUser.UserId);
        } else {
            adminStatusLabel.setText("❌ Пользователь не выбран");
        }
    }
}
