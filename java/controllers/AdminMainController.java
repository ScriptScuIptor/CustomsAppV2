package controllers;

import CourseWork.Presentation.MainAppManager;
import CourseWork.Presentation.NavigationHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminMainController {
    @FXML
    private Button usersButton;
    @FXML
    private Button requestsButton;
    @FXML
    private Button statsButton;
    @FXML
    private Button logoutButton;

    @FXML
    private void onUsers() {
        Stage current = (Stage) usersButton.getScene().getWindow();
        NavigationHelper.setPreviousStage(current);
        MainAppManager.showAdminUsersWindow(new Stage());
        current.hide();
    }

    @FXML
    private void onRequests() {
        Stage current = (Stage) requestsButton.getScene().getWindow();
        NavigationHelper.setPreviousStage(current);
        MainAppManager.showAdminRequestsWindow(new Stage());
        current.hide();
    }

    @FXML
    private void onStats() {
        Stage current = (Stage) statsButton.getScene().getWindow();
        NavigationHelper.setPreviousStage(current);
        MainAppManager.showAdminStatsWindow(new Stage());
        current.hide();
    }

    @FXML
    private void onLogout() {
        NavigationHelper.goToAuth(logoutButton); // 🔁 переход к окну авторизации
    }
}
