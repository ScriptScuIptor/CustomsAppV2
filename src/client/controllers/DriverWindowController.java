package client.controllers;

import database.DatabaseManager;
import model.User;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DriverWindowController {

    @FXML
    private ListView<String> vehicleListView;  // Список для отображения транспортных средств

    @FXML
    private Button addVehicleButton;  // Кнопка добавления транспортного средства

    private User currentUser;  // Текущий пользователь

    // Метод инициализации: добавление обработчика на кнопку
    @FXML
    public void initialize() {
        addVehicleButton.setOnAction(e -> addVehicle());
        // Заполнение списка транспортных средств
        loadVehicles();
    }

    // Метод для добавления нового транспортного средства
    private void addVehicle() {
        // Логика добавления транспортного средства
        String vehicleNumber = "Новый ТС"; // Пример: номер автомобиля
        if (DatabaseManager.registerTransport(vehicleNumber, currentUser.getId())) {
            loadVehicles();  // Перезагружаем список автомобилей после добавления
            System.out.println("Добавлено новое транспортное средство");
        } else {
            System.out.println("Ошибка при добавлении транспортного средства");
        }
    }

    // Метод для загрузки транспортных средств в список
    private void loadVehicles() {
        // Получаем список транспортных средств для текущего водителя
        ObservableList<String> vehicles = FXCollections.observableArrayList();
        // Пример: добавляем информацию о транспортных средствах
        // Это нужно будет заменить на реальные данные из базы
        vehicles.add("A123BC - Техника");
        vehicles.add("B456XY - Фрукты");
        vehicleListView.setItems(vehicles);  // Отображаем список
    }

    // Устанавливаем текущего пользователя для контроллера
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}
