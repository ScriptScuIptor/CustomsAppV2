package controllers;

import CourseWork.Core.CourseWork.Domain.AdminLog;
import CourseWork.Server.Serializable.Admin.AdminRequest;
import CourseWork.Server.Serializable.Admin.AdminResponse;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.format.DateTimeFormatter;

public class AdminLogsController {
    @FXML private TableView<AdminLog> logsTable;
    @FXML private TableColumn<AdminLog, String> timestampColumn;
    @FXML private TableColumn<AdminLog, String> actionColumn;
    @FXML private TableColumn<AdminLog, String> performedByColumn;
    @FXML private Label statusLabel;

    private static final String HOST = "localhost";
    private static final int PORT = 5555;

    @FXML
    public void initialize() {
        loadLogs();
    }

    private void loadLogs() {
        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(new AdminRequest(AdminRequest.OperationType.GET_ADMIN_LOGS));
            AdminResponse response = (AdminResponse) in.readObject();

            if (response.success && response.adminLogs != null) {
                timestampColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                        c.getValue().Timestamp.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))));
                actionColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().Action));
                performedByColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().PerformedBy));

                logsTable.setItems(FXCollections.observableArrayList(response.adminLogs));
                statusLabel.setText("");
            } else {
                statusLabel.setText("❌ Ошибка загрузки логов.");
            }

        } catch (Exception e) {
            statusLabel.setText("❌ Ошибка соединения с сервером.");
            e.printStackTrace();
        }
    }
}
