package controllers;

import CourseWork.Core.CourseWork.Application.UseCases.UserUseCases.SubmitRequestUseCase;
import CourseWork.Core.CourseWork.Domain.Request;
import CourseWork.Core.CourseWork.Domain.Roles;
import CourseWork.Core.CourseWork.Domain.User;
import CourseWork.Infrastracture.Database.Repositories.RequestRepository;
import CourseWork.Infrastracture.Database.Repositories.DbContext;
import CourseWork.Presentation.MainAppManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class RequestController {
    @FXML private ChoiceBox<String> requestTypeBox;
    @FXML private Label statusLabel;
    @FXML private TableView<Request> requestsTable;
    @FXML private TableColumn<Request, String> descriptionColumn;
    @FXML private TableColumn<Request, Boolean> approvedColumn;

    private final SubmitRequestUseCase useCase =
            new SubmitRequestUseCase(new RequestRepository(new DbContext()));

    private User currentUser;

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setUser(User user) {
        this.currentUser = user;

        if (user.role != Roles.DRIVER.getValue()) {
            statusLabel.setText(" Только водители могут подавать запросы.");
            disableSubmissionUI();
            return;
        }

        requestTypeBox.getItems().addAll(
                "Разрешение на выезд",
                "Проверка документации",
                "Технический осмотр",
                "Согласование маршрута"
        );
        requestTypeBox.setValue("Разрешение на выезд");

        loadRequests();
    }

    @FXML
    private void onSubmit() {
        String selectedType = requestTypeBox.getValue();

        if (selectedType == null || selectedType.isEmpty()) {
            showAlert("Ошибка", "Пожалуйста, выберите тип запроса.", Alert.AlertType.WARNING);
            return;
        }

        boolean ok = useCase.submit(selectedType, currentUser.UserId);

        if (ok) {
            showAlert("Успех", "✅ Запрос успешно отправлен!", Alert.AlertType.INFORMATION);
            loadRequests();
        } else {
            showAlert("Ошибка", "❌ Вы уже подали данный запрос!", Alert.AlertType.ERROR);
        }
    }

    private void loadRequests() {
        List<Request> userRequests = useCase.getRequestsByUser(currentUser.UserId);

        descriptionColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().Description));
        approvedColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleBooleanProperty(cell.getValue().Approved));

        requestsTable.setItems(FXCollections.observableArrayList(userRequests));
    }

    private void disableSubmissionUI() {
        if (requestTypeBox != null) requestTypeBox.setDisable(true);
        if (requestsTable != null) requestsTable.setDisable(true);
    }

    @FXML
    private void onBack() {
        Stage stage = (Stage) requestTypeBox.getScene().getWindow();
        MainAppManager.showAuthWindow(stage);
    }

}
