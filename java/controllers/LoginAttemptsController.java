package controllers;

import CourseWork.Core.CourseWork.Domain.LoginAttempt;
import CourseWork.Server.Serializable.Admin.AdminRequest;
import CourseWork.Server.Serializable.Admin.AdminResponse;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LoginAttemptsController {
    @FXML private TableView<LoginAttempt> attemptsTable;
    @FXML private TableColumn<LoginAttempt, String> usernameColumn;
    @FXML private TableColumn<LoginAttempt, Boolean> successColumn;
    @FXML private TableColumn<LoginAttempt, String> timestampColumn;
    @FXML private TextField usernameSearchField;
    @FXML private Label statusLabel;

    private static final String HOST = "localhost";
    private static final int PORT = 5555;

    @FXML
    public void initialize() {
        loadAll();
    }

    private void loadAll() {
        sendRequest(new AdminRequest(AdminRequest.OperationType.GET_ALL_ATTEMPTS));
    }

    @FXML
    private void onSearch() {
        String username = usernameSearchField.getText().trim();
        if (!username.isEmpty()) {
            sendRequest(new AdminRequest(AdminRequest.OperationType.GET_ATTEMPTS_BY_USERNAME, username));
        }
    }

    @FXML
    private void onReset() {
        usernameSearchField.clear();
        loadAll();
    }

    private void sendRequest(AdminRequest req) {
        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(req);
            AdminResponse response = (AdminResponse) in.readObject();

            if (response.success && response.loginAttempts != null) {
                populateTable(response.loginAttempts);
            } else {
                attemptsTable.setItems(FXCollections.emptyObservableList());
                statusLabel.setText("⚠️ Нет данных для отображения.");
            }

        } catch (Exception e) {
            statusLabel.setText("❌ Ошибка подключения к серверу.");
            e.printStackTrace();
        }
    }

    private void populateTable(List<LoginAttempt> attempts) {
        usernameColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().Username));
        successColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleBooleanProperty(c.getValue().Success));
        timestampColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().Timestamp.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
                ));
        attemptsTable.setItems(FXCollections.observableArrayList(attempts));
        statusLabel.setText("");
    }
}
