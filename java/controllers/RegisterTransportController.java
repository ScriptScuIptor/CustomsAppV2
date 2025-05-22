package controllers;

import CourseWork.Core.CourseWork.Domain.Roles;
import CourseWork.Core.CourseWork.Domain.User;
import CourseWork.Core.CourseWork.Domain.VehicleCategory;
import CourseWork.Presentation.MainAppManager;
import CourseWork.Server.Serializable.Admin.AdminRequest;
import CourseWork.Server.Serializable.Admin.AdminResponse;
import CourseWork.Server.Serializable.Transport.RegisterTransportRequest;
import CourseWork.Server.Serializable.Transport.RegisterTransportResponse;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class RegisterTransportController {
    @FXML private TextField vehicleNumberField;
    @FXML private Label statusLabel;
    @FXML private ComboBox<VehicleCategory> categoryComboBox;
    private List<VehicleCategory> categories;

    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
    }

    @FXML
    public void initialize() {
        loadCategories();
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void onRegisterTransport() {
        String vehicleNumber = vehicleNumberField.getText();
        VehicleCategory selectedCategory = categoryComboBox.getValue();

        if (vehicleNumber == null || vehicleNumber.isBlank()) {
            showAlert("Ошибка", "❌ Введите номер транспортного средства", Alert.AlertType.WARNING);
            return;
        }
        if (selectedCategory == null) {
            showAlert("Ошибка", "❌ Выберите тип транспортного средства", Alert.AlertType.WARNING);
            return;
        }

        try (Socket socket = new Socket("localhost", 5555);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            RegisterTransportRequest request = new RegisterTransportRequest(
                    currentUser.UserId,
                    vehicleNumber,
                    selectedCategory.CategoryId,
                    currentUser.username,
                    currentUser.UserPassword
            );

            out.writeObject(request);
            RegisterTransportResponse response = (RegisterTransportResponse) in.readObject();

            if (response.success) {
                showAlert("Успех", response.message, Alert.AlertType.INFORMATION);
            } else {
                showAlert("Ошибка", response.message, Alert.AlertType.ERROR);
            }

            if (response.updatedUser != null) {
                currentUser = response.updatedUser;
            }

        } catch (IOException | ClassNotFoundException e) {
            showAlert("Ошибка", "❌ Ошибка подключения к серверу", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void loadCategories() {
        AdminResponse response = null;
        try (Socket socket = new Socket("localhost", 5555);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(new AdminRequest(AdminRequest.OperationType.GET_ALL_VEHICLE_CATEGORIES));
            response = (AdminResponse) in.readObject();

            if (response.success && response.vehicleCategories != null) {
                categories = response.vehicleCategories;
                categoryComboBox.setItems(FXCollections.observableArrayList(categories));
                categoryComboBox.setConverter(new StringConverter<>() {
                    @Override
                    public String toString(VehicleCategory c) {
                        return c.CategoryName;
                    }

                    @Override
                    public VehicleCategory fromString(String s) {
                        return null;
                    }
                });
            }

        } catch (Exception e) {
            statusLabel.setText("❌ Ошибка загрузки категорий ТС");
        }
    }

    @FXML
    private void goToRequests() {
        if (currentUser.role != Roles.DRIVER.getValue()) {
            statusLabel.setText("❌ Только водители могут подавать запросы.");
            return;
        }

        Stage stage = (Stage) vehicleNumberField.getScene().getWindow();
        MainAppManager.showRequestWindow(stage, currentUser);
    }
}
