package CourseWork.Presentation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavigationHelper {
    private static Stage previousStage;

    public static void setPreviousStage(Stage stage) {
        previousStage = stage;
    }

    public static void goBack(Node currentNode) {
        if (previousStage != null && currentNode != null) {
            Stage currentStage = (Stage) currentNode.getScene().getWindow();
            currentStage.close();
            previousStage.show();
        }
    }

    public static void goToAuth(Node currentNode) {
        try {
            Stage currentStage = (Stage) currentNode.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(NavigationHelper.class.getResource("/AuthWindow.fxml"));
            Scene scene = new Scene(loader.load());
            currentStage.setScene(scene);
            currentStage.setTitle("Авторизация");
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
