package CourseWork.Presentation;

import CourseWork.Core.CourseWork.Domain.User;
import controllers.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainAppManager {

    public static void showAuthWindow(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(MainAppManager.class.getResource("/AuthWindow.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("Авторизация");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showRegisterWindow(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(MainAppManager.class.getResource("/RegisterWindow.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("Регистрация");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showRequestWindow(Stage stage, User user) {
        try {
            FXMLLoader loader = new FXMLLoader(MainAppManager.class.getResource("/RequestWindow.fxml"));
            Scene scene = new Scene(loader.load());

            RequestController controller = loader.getController();
            controller.setUser(user);

            stage.setScene(scene);
            stage.setTitle("Подача запроса");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showRegisterTransportWindow(Stage stage, User user) {
        try {
            FXMLLoader loader = new FXMLLoader(MainAppManager.class.getResource("/RegisterTransport.fxml"));
            Scene scene = new Scene(loader.load());

            RegisterTransportController controller = loader.getController();
            controller.setUser(user);

            stage.setScene(scene);
            stage.setTitle("Регистрация ТС");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showAdminMainWindow(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(MainAppManager.class.getResource("/AdminMainWindow.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("Главное меню администратора");

            NavigationHelper.setPreviousStage(stage);  // ✅ установка главного окна
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showAdminUsersWindow(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(MainAppManager.class.getResource("/AdminUsersWindow.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("Пользователи");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showAdminRequestsWindow(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(MainAppManager.class.getResource("/AdminRequestsWindow.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("Заявки");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showAdminStatsWindow(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(MainAppManager.class.getResource("/AdminStatsWindow.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("Статистика");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
