package controllers;

import CourseWork.Presentation.NavigationHelper;
import CourseWork.Server.Serializable.Admin.AdminRequest;
import CourseWork.Server.Serializable.Admin.AdminResponse;
import CourseWork.Server.Serializable.Admin.RequestWithDriverInfo;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class AdminRequestsController {
    @FXML private TableView<RequestWithDriverInfo> requestsTable;
    @FXML private TableColumn<RequestWithDriverInfo, String> descriptionColumn;
    @FXML private TableColumn<RequestWithDriverInfo, Boolean> approvedColumn;
    @FXML private TableColumn<RequestWithDriverInfo, String> driverColumn;
    @FXML private TableColumn<RequestWithDriverInfo, String> vehicleColumn;
    @FXML private TableColumn<RequestWithDriverInfo, String> categoryColumn;
    @FXML private Label statusLabel;

    private List<RequestWithDriverInfo> allRequests;
    private final String HOST = "localhost";
    private final int PORT = 5555;

    @FXML
    public void initialize() {
        loadRequests();
    }

    public void loadRequests() {
        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(new AdminRequest(AdminRequest.OperationType.GET_ALL_REQUESTS));
            AdminResponse response = (AdminResponse) in.readObject();

            if (response.success && response.requestsWithDriver != null) {
                allRequests = response.requestsWithDriver;
                descriptionColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().description));
                approvedColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleBooleanProperty(c.getValue().approved));
                driverColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().driverUsername));
                vehicleColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().vehicleNumber));
                categoryColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().vehicleCategoryName));
                requestsTable.setItems(FXCollections.observableArrayList(allRequests));
                statusLabel.setText("");
            }

        } catch (IOException | ClassNotFoundException e) {
            statusLabel.setText("❌ Ошибка загрузки заявок");
        }
    }

    @FXML
    public void showAllRequests() {
        requestsTable.setItems(FXCollections.observableArrayList(allRequests));
    }

    @FXML
    public void showApprovedRequests() {
        requestsTable.setItems(FXCollections.observableArrayList(
                allRequests.stream().filter(r -> r.approved).toList()));
    }

    @FXML
    public void showUnapprovedRequests() {
        requestsTable.setItems(FXCollections.observableArrayList(
                allRequests.stream().filter(r -> !r.approved).toList()));
    }

    @FXML
    public void approveSelected() {
        var selected = requestsTable.getSelectionModel().getSelectedItem();
        if (selected == null || selected.approved) {
            statusLabel.setText("⚠️ Заявка уже одобрена или не выбрана");
            return;
        }

        selected.approved = true;

        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(new AdminRequest(AdminRequest.OperationType.APPROVE_REQUEST, selected.toRequest()));
            AdminResponse response = (AdminResponse) in.readObject();

            statusLabel.setText(response.message);
            loadRequests();

        } catch (IOException | ClassNotFoundException e) {
            statusLabel.setText("❌ Ошибка при одобрении заявки");
        }
    }

    @FXML
    private void onBack() {
        NavigationHelper.goBack(statusLabel);
    }
}
